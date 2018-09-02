package cn.exrick.search.service.impl;

import cn.exrick.common.exception.XmallException;
import cn.exrick.common.utils.HttpUtil;
import cn.exrick.manager.dto.EsCount;
import cn.exrick.manager.dto.EsInfo;
import cn.exrick.manager.dto.front.SearchItem;
import cn.exrick.manager.mapper.ext.TbItemExtMapper;
import cn.exrick.search.service.SearchItemService;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author Exrickx
 */
@Slf4j
@Component
@Service(interfaceClass = SearchItemService.class)
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private TbItemExtMapper tbItemExtMapper;

    @Value("${es.connectIp}")
    private String esConnectIp;

    @Value("${es.nodeClientPort}")
    private String esNodeClientPort;

    @Value("${es.clusterName}")
    private String esClusterName;

    @Value("${es.itemIndex}")
    private String esItemIndex;

    @Value("${es.itemType}")
    private String esItemType;

    @PostConstruct
    private void initIndex() {
        int itemImportSize = importAllItems();
        log.info("Import {} Items into index", itemImportSize);
    }

    private void clearIndex(TransportClient client) {
        client.admin().indices().prepareDelete(esItemIndex).execute().actionGet();
    }

    private boolean existsIndex(TransportClient client) {
        IndicesExistsRequest indicesExistsRequest = client.admin().indices().prepareExists(esItemIndex).request();
        IndicesExistsResponse response = client.admin().indices().exists(indicesExistsRequest).actionGet();
        return response.isExists();
    }

    private void buildIndex(TransportClient client) throws IOException {
        CreateIndexRequestBuilder createBuilder = client.admin().indices().prepareCreate(esItemIndex);
        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder().startObject()
            .startObject("properties");

        xContentBuilder = builderField(xContentBuilder, "productId", "long");
        xContentBuilder = builderField(xContentBuilder, "salePrice", "double");
        xContentBuilder = builderField(xContentBuilder, "productName", "string");
        xContentBuilder = builderField(xContentBuilder, "subTitle", "string");
        xContentBuilder = builderField(xContentBuilder, "productImageBig", "string");
        xContentBuilder = builderField(xContentBuilder, "categoryName", "string");
        xContentBuilder = builderField(xContentBuilder, "cid", "integer");
        xContentBuilder.endObject().endObject();
        client.admin().indices().create(createBuilder.request()).actionGet();
    }

    private XContentBuilder builderField(XContentBuilder xContentBuilder, String fieldName, String type) throws IOException {
        xContentBuilder.startObject(fieldName)
            .field("type", type)
            .endObject();
        return xContentBuilder;
    }

    @Override
    public int importAllItems() {

        try {
            Settings settings = Settings.builder()
                .put("cluster.name", esClusterName).build();
            TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(esConnectIp), 9300));
            if (this.existsIndex(client)) {
                this.clearIndex(client);
            }
            this.buildIndex(client);
            //批量添加
            BulkRequestBuilder bulkRequest = client.prepareBulk();

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
                bulkRequest.add(client.prepareIndex(esItemIndex, esItemType, String.valueOf(searchItem.getProductId()))
                    .setSource(jsonBuilder()
                        .startObject()
                        .field("productId", searchItem.getProductId())
                        .field("salePrice", searchItem.getSalePrice().doubleValue())
                        .field("productName", searchItem.getProductName())
                        .field("subTitle", searchItem.getSubTitle())
                        .field("productImageBig", searchItem.getProductImageBig())
                        .field("categoryName", searchItem.getCategoryName())
                        .field("cid", searchItem.getCid())
                        .endObject()
                    )
                );
            }

            BulkResponse bulkResponse = bulkRequest.get();

            log.info("更新索引成功");

            client.close();
        } catch (Exception e) {
            log.error("ex", e);
            throw new XmallException("导入ES索引库出错，请再次尝试", e);
        }

        return 1;
    }

    @Override
    public EsInfo getEsInfo() {

        String healthUrl = "http://" + esConnectIp + ":" + esNodeClientPort + "/_cluster/health";
        String countUrl = "http://" + esConnectIp + ":" + esNodeClientPort + "/_count";
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
