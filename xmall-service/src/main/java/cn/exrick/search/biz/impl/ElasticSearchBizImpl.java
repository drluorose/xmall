package cn.exrick.search.biz.impl;

import cn.exrick.config.EsConfig;
import cn.exrick.manager.dto.front.SearchItem;
import cn.exrick.search.biz.ElasticSearchBiz;
import cn.exrick.search.service.impl.ElasticSearchVelocityHelper;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Slf4j
@Component
public class ElasticSearchBizImpl implements ElasticSearchBiz {

    @Autowired
    private EsConfig esConfig;

    @Autowired
    private RestClient restClient;

    private Gson gson = new GsonBuilder().create();

    @Autowired
    private ElasticSearchVelocityHelper elasticSearchVelocityHelper;

    @Override
    public boolean existProductIndex() throws IOException {
        if (Objects.isNull(restClient)) {
            return false;
        }
        Response response = restClient.performRequest("HEAD", esConfig.getSearchItemIndexName());
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            return true;
        }
        if (statusCode == HttpStatus.SC_NOT_FOUND) {
            return false;
        }
        return false;
    }

    @Override
    public boolean createProductIndex() throws IOException {
        String ns = elasticSearchVelocityHelper.getElasticSearchString(ElasticSearchVelocityHelper.CREATE_ITEM_INDEX, Collections.emptyMap());
        NStringEntity nStringEntity = new NStringEntity(ns, ContentType.APPLICATION_JSON);
        Response response = restClient.performRequest("PUT", esConfig.getSearchItemIndexName(), Collections.emptyMap(), nStringEntity);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return true;
        }
        return false;
    }

    @Override
    public SearchItem getSearchIndexDoc(Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("id is null");
        }
        try {
            Response response = restClient.performRequest("GET", buildSearchItemEndpoint(id));
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String objStr = EntityUtils.toString(response.getEntity());
                JsonObject jsonObject = gson.fromJson(objStr, JsonObject.class);
                return gson.fromJson(jsonObject.get("_source"), SearchItem.class);
            } else {
                return null;
            }
        } catch (IOException e) {
            return null;
        }

    }

    @Override
    public boolean createSearchIndexDoc(SearchItem searchItem) throws IOException {
        if (Objects.isNull(searchItem)) {
            throw new IllegalArgumentException("searchItem is null");
        }

        NStringEntity searchItemNStringEntity = buildSearchItemNStringEntity(searchItem);
        Response response = restClient.performRequest("PUT",
            buildSearchItemEndpoint(searchItem.getProductId()),
            Collections.<String, String>emptyMap(),
            searchItemNStringEntity);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateSearchIndexDoc(SearchItem searchItem) throws IOException {
        if (Objects.isNull(searchItem)) {
            throw new IllegalArgumentException("searchItem is null");
        }
        Map<String, Object> values = ImmutableMap.of("doc", gson.toJson(searchItem));
        String ns = elasticSearchVelocityHelper.getElasticSearchString(
            ElasticSearchVelocityHelper.UPDATE_ITEM_INDEX, values);

        NStringEntity searchItemNStringEntity = new NStringEntity(ns, ContentType.APPLICATION_JSON);

        Response response = restClient.performRequest("POST",
            buildUpdateSearchItemEndpoint(searchItem.getProductId()),
            Collections.<String, String>emptyMap(),
            searchItemNStringEntity);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeSearchIndexDoc(SearchItem searchItem) {
        if (Objects.isNull(searchItem)) {
            throw new IllegalArgumentException("searchItem is null");
        }
        try {
            Response response = restClient.performRequest("DELETE",
                buildSearchItemEndpoint(searchItem.getProductId()));
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            log.error("es", e);
        }
        return false;
    }

    private NStringEntity buildSearchItemNStringEntity(SearchItem searchItem) {
        String searchItemStr = gson.toJson(searchItem);
        return new NStringEntity(searchItemStr, ContentType.APPLICATION_JSON);
    }

    private String buildSearchItemEndpoint(Long productId) {
        return "/" + esConfig.getSearchItemIndexName() + "/" + esConfig.getSearchItemIndexType() + "/" + productId;
    }

    private String buildUpdateSearchItemEndpoint(Long productId) {
        return "/" + esConfig.getSearchItemIndexName() + "/" + esConfig.getSearchItemIndexType() + "/" + productId + "/_update";
    }
}
