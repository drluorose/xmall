package cn.exrick.search.service.impl;

import cn.exrick.config.EsConfig;
import cn.exrick.manager.dto.front.SearchItem;
import cn.exrick.manager.dto.front.SearchResult;
import cn.exrick.search.service.SearchService;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Exrickx
 */
@Slf4j
@Component
@Service(interfaceClass = SearchService.class)
public class SearchServiceImpl implements SearchService {

    @Autowired
    private EsConfig esConfig;

    @Autowired
    private RestClient restClient;

    @Autowired
    private ElasticSearchVelocityHelper elasticSearchVelocityHelper;

    private Gson gson = new GsonBuilder().create();

    private static final String ASE_SORT = "1";

    private static final String DESC_SORT = "-1";

    private static final String ASC = "asc";

    private static final String DESC = "desc";

    private String buildEndpoint() {
        return "/" + esConfig.getSearchItemIndexName() + "/" + esConfig.getSearchItemIndexType() + "/_search";
    }

    private NStringEntity buildSearchEntity(String key, String sort, int priceGt, int priceLte, int page, int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("查询参数异常");
        }
        if (page <= 0) {
            page = 1;
        }
        int start = (page - 1) * size;
        String sortType = ASC;
        if (Objects.equals(sort, DESC_SORT)) {
            sortType = DESC;
        }
        Map<String, Object> searchParam = Maps.newHashMap();
        searchParam.put("from", start);
        searchParam.put("size", size);
        searchParam.put("sortDirection", sortType);
        searchParam.put("searchProductName", key);
        searchParam.put("filterGtValue", priceGt);
        searchParam.put("filterLtValue", priceLte);
        String searchStr = elasticSearchVelocityHelper.getElasticSearchString(ElasticSearchVelocityHelper.SEARCH_ITEM_INDEX, searchParam);
        log.info("searchStr;{}", searchStr);
        return new NStringEntity(searchStr, ContentType.APPLICATION_JSON);
    }

    /**
     * 使用QueryBuilder
     * termQuery("key", obj) 完全匹配
     * termsQuery("key", obj1, obj2..)   一次匹配多个值
     * matchQuery("key", Obj) 单个匹配, field不支持通配符, 前缀具高级特性
     * multiMatchQuery("text", "field1", "field2"..);  匹配多个字段, field有通配符忒行
     */
    @Override
    public SearchResult search(String key, int page, int size, String sort, int priceGt, int priceLte) throws IOException {
        try {
            NStringEntity searchStr = buildSearchEntity(key, sort, priceGt, priceLte, page, size);
            Response response = restClient.performRequest("GET", buildEndpoint(), Collections.<String, String>emptyMap(), searchStr);
            String searchResultStr = EntityUtils.toString(response.getEntity());
            return parseSearchResult(searchResultStr, size);
        } catch (Exception e) {
            log.error("e", e);
        }
        return null;
    }

    public SearchResult parseSearchResult(String searchResultJson, int size) {
        SearchResult searchResult = new SearchResult();
        JsonObject searchResultJsonObject = gson.fromJson(searchResultJson, JsonObject.class);
        searchResultJsonObject = (JsonObject) searchResultJsonObject.get("hits");
        searchResult.setRecordCount(searchResultJsonObject.get("total").getAsLong());
        searchResult.setTotalPages(searchResult.getRecordCount().intValue() / size);
        searchResult.setItemList(parseSearchResult((JsonArray) searchResultJsonObject.get("hits")));
        return searchResult;
    }

    public List<SearchItem> parseSearchResult(JsonArray hits) {
        List<SearchItem> searchItems = Lists.newArrayList();
        for (JsonElement element : hits) {
            JsonObject hit = (JsonObject) element;
            SearchItem searchItem = gson.fromJson(hit.get("_source"), SearchItem.class);
            if (Objects.nonNull(hit.get("hightlight"))) {
                JsonElement highlightProductName = ((JsonObject) hit.get("highlight")).get("productName");
                searchItem.setProductName(highlightProductName.getAsString());
            }
            searchItems.add(searchItem);
        }
        return searchItems;
    }
}
