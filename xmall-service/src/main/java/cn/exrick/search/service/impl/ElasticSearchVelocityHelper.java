package cn.exrick.search.service.impl;

import com.google.common.collect.Maps;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Component
public class ElasticSearchVelocityHelper {

    public static final String CREATE_ITEM_INDEX = "create_item_index";

    public static final String SEARCH_ITEM_INDEX = "search_item_index";

    public static final String UPDATE_ITEM_INDEX = "update_item_index";

    private VelocityEngine velocityEngine;

    private ConcurrentMap<String, Template> allTemplateIndex = Maps.newConcurrentMap();

    public ElasticSearchVelocityHelper() {

    }

    @PostConstruct
    private void init(){
        this.velocityEngine = new VelocityEngine();
        this.velocityEngine.setProperty(Velocity.RESOURCE_LOADER, "class");
        this.velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        this.velocityEngine.init();
        allTemplateIndex.put(CREATE_ITEM_INDEX, createItemIndex());
        allTemplateIndex.put(SEARCH_ITEM_INDEX, searchItemIndex());
        allTemplateIndex.put(UPDATE_ITEM_INDEX, updateItemIndex());
    }

    private Template updateItemIndex() {
        return this.velocityEngine.getTemplate("elasticsearch-template/update_item_index.vm");
    }

    private Template createItemIndex() {
        return this.velocityEngine.getTemplate("elasticsearch-template/create_item_index.vm");
    }

    private Template searchItemIndex() {
        return this.velocityEngine.getTemplate("elasticsearch-template/search_item_index.vm");
    }

    public String getElasticSearchString(String indexName, Map<String, Object> values) {
        Template template = allTemplateIndex.get(indexName);
        VelocityContext velocityContext = new VelocityContext(values);
        StringWriter stringWriter = new StringWriter();
        template.merge(velocityContext, stringWriter);
        return stringWriter.toString();
    }

}
