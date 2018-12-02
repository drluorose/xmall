package com.xmall.es;

import cn.exrick.search.service.impl.ElasticSearchVelocityHelper;
import org.apache.commons.collections.MapUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
public class util {

    @Test
    public void test() {
        ElasticSearchVelocityHelper elasticSearchVelocityHelper = new ElasticSearchVelocityHelper();
        String ns = elasticSearchVelocityHelper.getElasticSearchString(ElasticSearchVelocityHelper.CREATE_ITEM_INDEX, MapUtils.EMPTY_MAP);
        System.out.println(ns);

        Map<String, Object> values = new HashMap<>();
        values.put("from", 0);
        values.put("size", 10);
        values.put("sortFieldDirection", "ASC");
        values.put("searchFieldValue", "手机");
        values.put("filterFieldGtValue", 7.90);
        values.put("filterFieldLtValue", 2000.00);
        ns = elasticSearchVelocityHelper.getElasticSearchString(ElasticSearchVelocityHelper.SEARCH_ITEM_INDEX, values);
        System.out.println(ns);
    }
}
