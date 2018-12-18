

package org.mybatis.generator.plugins;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * @author dongjiejie
 */
@Slf4j
public class SelectOneByExamplePlugin extends PluginAdapter {

    private Config config;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(List<String> warnings) {
        if (this.config == null) {
            this.config = new Config(getProperties());
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze,
                                                                 IntrospectedTable introspectedTable) {

        if (!config.shouldExclude(interfaze.getType())) {
            interfaze.addMethod(generateSelectOneByExample(method, introspectedTable));
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze,
                                                                    IntrospectedTable introspectedTable) {

        if (!config.shouldExclude(interfaze.getType())) {
            interfaze.addMethod(generateSelectOneByExample(method, introspectedTable));
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method,
                                                                 TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (!config.shouldExclude(topLevelClass.getType())) {
            topLevelClass.addMethod(generateSelectOneByExample(method, introspectedTable));
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method,
                                                                    TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (!config.shouldExclude(topLevelClass.getType())) {
            topLevelClass.addMethod(generateSelectOneByExample(method, introspectedTable));
        }
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        sqlMapSelectOneByExample(document, introspectedTable);
        return true;
    }

    private Method generateSelectOneByExample(Method method, IntrospectedTable introspectedTable) {
        Method m = new Method(config.methodToGenerate);
        m.setVisibility(method.getVisibility());
        FullyQualifiedJavaType returnType = introspectedTable.getRules().calculateAllFieldsClass();
        m.setReturnType(returnType);

        List<String> annotations = method.getAnnotations();
        for (String a : annotations) {
            m.addAnnotation(a);
        }

        List<Parameter> params = method.getParameters();
        for (Parameter p : params) {
            m.addParameter(p);
        }

        context.getCommentGenerator().addGeneralMethodComment(m, introspectedTable);
        return m;
    }

    private void sqlMapSelectOneByExample(Document document, IntrospectedTable introspectedTable) {

        // 生成查询语句
        XmlElement selectOneElement = new XmlElement("select");
        // 添加注释(!!!必须添加注释，overwrite覆盖生成时，@see XmlFileMergerJaxp.isGeneratedNode会去判断注释中是否存在OLD_ELEMENT_TAGS中的一点，例子：@mbg.generated)
        CommentTools.addComment(selectOneElement);

        // 添加ID
        selectOneElement.addAttribute(new Attribute("id", config.methodToGenerate));

        // ----------------------------------------- 表中是否有blob类型字段 ---------------------------------------
        if (introspectedTable.hasBLOBColumns()) {
            // 添加返回类型
            selectOneElement.addAttribute(new Attribute("resultMap", introspectedTable.getResultMapWithBLOBsId()));
            // 添加参数类型
            selectOneElement.addAttribute(new Attribute("parameterType", introspectedTable.getExampleType()));
            // 添加查询SQL
            selectOneElement.addElement(new TextElement("select")); //$NON-NLS-1$

            StringBuilder sb = new StringBuilder();
            if (stringHasValue(introspectedTable.getSelectByExampleQueryId())) {
                sb.append('\'');
                sb.append(introspectedTable.getSelectByExampleQueryId());
                sb.append("' as QUERYID,"); //$NON-NLS-1$
                selectOneElement.addElement(new TextElement(sb.toString()));
            }

            selectOneElement.addElement(XmlElementGeneratorTools.getBaseColumnListElement(introspectedTable));
            selectOneElement.addElement(new TextElement(",")); //$NON-NLS-1$
            selectOneElement.addElement(XmlElementGeneratorTools.getBlobColumnListElement(introspectedTable));

            sb.setLength(0);
            sb.append("from "); //$NON-NLS-1$
            sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
            selectOneElement.addElement(new TextElement(sb.toString()));
            selectOneElement.addElement(XmlElementGeneratorTools.getExampleIncludeElement(introspectedTable));

            XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
            ifElement.addAttribute(new Attribute("test", "orderByClause != null")); //$NON-NLS-1$ //$NON-NLS-2$
            ifElement.addElement(new TextElement("order by ${orderByClause}")); //$NON-NLS-1$
            selectOneElement.addElement(ifElement);

            // 只查询一条
            selectOneElement.addElement(new TextElement("limit 1"));
        } else {
            // 添加返回类型
            selectOneElement.addAttribute(new Attribute("resultMap", introspectedTable.getBaseResultMapId()));
            // 添加参数类型
            selectOneElement.addAttribute(new Attribute("parameterType", introspectedTable.getExampleType()));
            selectOneElement.addElement(new TextElement("select")); //$NON-NLS-1$

            StringBuilder sb = new StringBuilder();
            if (stringHasValue(introspectedTable.getSelectByExampleQueryId())) {
                sb.append('\'');
                sb.append(introspectedTable.getSelectByExampleQueryId());
                sb.append("' as QUERYID,"); //$NON-NLS-1$
                selectOneElement.addElement(new TextElement(sb.toString()));
            }
            selectOneElement.addElement(XmlElementGeneratorTools.getBaseColumnListElement(introspectedTable));

            sb.setLength(0);
            sb.append("from "); //$NON-NLS-1$
            sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
            selectOneElement.addElement(new TextElement(sb.toString()));
            selectOneElement.addElement(XmlElementGeneratorTools.getExampleIncludeElement(introspectedTable));

            XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
            ifElement.addAttribute(new Attribute("test", "orderByClause != null")); //$NON-NLS-1$ //$NON-NLS-2$
            ifElement.addElement(new TextElement("order by ${orderByClause}")); //$NON-NLS-1$
            selectOneElement.addElement(ifElement);

            // 只查询一条
            selectOneElement.addElement(new TextElement("limit 1"));
        }

        // 添加到根节点
        document.getRootElement().addElement(selectOneElement);
        log.debug("itfsw(查询单条数据插件):" + introspectedTable.getMyBatis3XmlMapperFileName() + "增加SelectOneByExample方法(" + (introspectedTable.hasBLOBColumns() ? "有" : "无") + "Blob类型))。");

    }

    private static final class Config extends BasePluginConfig {

        private static final String defaultMethodToGenerate = "selectOneByExample";
        private static final String methodToGenerateKey = "methodToGenerate";

        private String methodToGenerate;

        protected Config(Properties props) {
            super(props);
            this.methodToGenerate = props.getProperty(methodToGenerateKey, defaultMethodToGenerate);
        }
    }
}
