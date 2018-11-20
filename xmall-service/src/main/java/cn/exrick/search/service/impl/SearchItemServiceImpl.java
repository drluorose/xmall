package cn.exrick.search.service.impl;

import cn.exrick.common.exception.XmallException;
import cn.exrick.common.utils.HttpUtil;
import cn.exrick.config.EsConfig;
import cn.exrick.manager.dto.EsCount;
import cn.exrick.manager.dto.EsInfo;
import cn.exrick.manager.dto.front.SearchItem;
import cn.exrick.manager.mapper.ext.TbItemExtMapper;
import cn.exrick.search.service.SearchItemService;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Exrickx
 */
@Slf4j
@Component
@Service(interfaceClass = SearchItemService.class)
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private TbItemExtMapper tbItemExtMapper;

    @Autowired
    private EsConfig esConfig;

    private Gson gson = new GsonBuilder().create();

    private RestClient restClient;

    @PostConstruct
    private void initIndex() throws IOException {
        int itemImportSize = importAllItems();
        log.info("Import {} Items into index", itemImportSize);
    }

    public String buildEndpoint(Long productId) {
        return esConfig.getSearchItemIndexName() + "/" + esConfig.getSearchItemIndexType() + "/" + productId;
    }

    private void putSearchItemNStringEntity(SearchItem searchItem) throws IOException {
        NStringEntity searchItemNStringEntity = buildItemNStringEntity(searchItem);
        restClient.performRequest("PUT",
            buildEndpoint(searchItem.getProductId()),
            Collections.<String, String>emptyMap(),
            searchItemNStringEntity);
    }

    private NStringEntity buildItemNStringEntity(SearchItem searchItem) {
        String searchItemStr = gson.toJson(searchItem);
        NStringEntity searchItemStrNStringEntity = new NStringEntity(searchItemStr, ContentType.APPLICATION_JSON);
        return searchItemStrNStringEntity;
    }

    private void createIndex() throws IOException {
        if (Objects.isNull(restClient)) {
            return;
        }
        restClient.performRequest("PUT", esConfig.getSearchItemIndexName());
    }

    private boolean existsIndex() throws IOException {
        if (Objects.isNull(restClient)) {
            return false;
        }

        Response response = restClient.performRequest("HEAD", esConfig.getSearchItemIndexName());
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.OK.value()) {
            return true;
        }
        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            return false;
        }
        return false;
    }

    @Override
    public int importAllItems() throws IOException {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
            new UsernamePasswordCredentials(esConfig.getUsername(), esConfig.getPassword()));

        restClient = RestClient.builder(new HttpHost(esConfig.getHost(), esConfig.getPort()))
            .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                @Override
                public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                    return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                }
            }).build();

        if (!existsIndex()) {
            createIndex();
        }

        try {

            //查询商品列表
            List<SearchItem> itemList = tbItemExtMapper.getItemList();

            //遍历商品列表
            for (SearchItem searchItem : itemList) {
                String image = searchItem.getProductImageBig();
                if (image != null && !"".equals(image)) {
                    String[] strings = image.split(",");
                    image = strings[0];
                } else {
                    image = "";
                }
                searchItem.setProductImageBig(image);
                putSearchItemNStringEntity(searchItem);
            }

            log.info("更新索引成功");
        } catch (Exception e) {
            log.error("ex", e);
            throw new XmallException("导入ES索引库出错，请再次尝试", e);
        }

        return 1;
    }

    @Override
    public EsInfo getEsInfo() {

        String healthUrl = "http://" + "x" + ":" + "x" + "/_cluster/health";
        String countUrl = "http://" + "x" + ":" + "x" + "/_count";
        String healthResult = HttpUtil.sendGet(healthUrl);
        String countResult = HttpUtil.sendGet(countUrl);
        if (StringUtils.isBlank(healthResult) || StringUtils.isBlank(countResult)) {
            throw new XmallException("连接集群失败，请检查ES运行状态");
        }
        EsInfo esInfo = new EsInfo();
        EsCount esCount = new EsCount();
        try {
            esInfo = new Gson().fromJson(healthResult, EsInfo.class);
            esCount = new Gson().fromJson(countResult, EsCount.class);
            esInfo.setCount(esCount.getCount());
        } catch (Exception e) {
            e.printStackTrace();
            throw new XmallException("获取ES信息出错");
        }

        return esInfo;
    }
}
