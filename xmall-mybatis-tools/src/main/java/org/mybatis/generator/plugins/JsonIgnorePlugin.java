package org.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonIgnorePlugin extends PluginAdapter {

    private static final Logger log = LoggerFactory.getLogger(JsonIgnorePlugin.class);

    private FullyQualifiedJavaType jsonIgnoreAnnotation = new FullyQualifiedJavaType("com.fasterxml.jackson.annotation.JsonIgnore");

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (hasJsonIgnoreProperties(introspectedTable)) {
            this.addDataAnnotation(topLevelClass);
        }
        return true;
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (hasJsonIgnoreProperties(introspectedTable)) {
            this.addDataAnnotation(topLevelClass);
        }
        return true;
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass,
                                                      IntrospectedTable introspectedTable) {
        if (hasJsonIgnoreProperties(introspectedTable)) {
            this.addDataAnnotation(topLevelClass);
        }
        return true;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass,
                                              IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass,
                                              IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {

        if (!hasJsonIgnoreProperties(introspectedTable)) {
            return true;
        }

        String property = introspectedTable.getTableConfigurationProperty("jsonIgnore");
        List<String> ignoreColumns = new ArrayList<>(Arrays.asList(property.split(",")));
        if (ignoreColumns.contains(introspectedColumn.getActualColumnName())) {
            field.addAnnotation("@JsonIgnore");
        }
        return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);

    }

    protected void addDataAnnotation(TopLevelClass topLevelClass) {
        topLevelClass.addImportedType(this.jsonIgnoreAnnotation);
    }


    private boolean hasJsonIgnoreProperties(IntrospectedTable introspectedTable) {
        String property = introspectedTable.getTableConfigurationProperty("jsonIgnore");

        log.info("property:{}", property);
        if (null == property || property.length() == 0) {
            return false;
        }
        return true;
    }
}