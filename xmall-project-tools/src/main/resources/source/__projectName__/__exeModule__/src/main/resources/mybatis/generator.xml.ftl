<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>

    <context id="test" targetRuntime="MyBatis3">
        <!--serialize plugin-->
        <plugin type="org.mybatis.generator.plugins.SerializablePlugin"/>
        <!--page plugin-->
        <plugin type="org.mybatis.generator.plugins.MyLimitPlugin"/>
        <!--lombok plugin-->
        <plugin type="org.mybatis.generator.plugins.LombokPlugin"/>
        <!--fix xml overwrite bug plugin-->
        <plugin type="org.mybatis.generator.plugins.UnmergeableXmlMappersPlugin"/>

        <plugin type="org.mybatis.generator.plugins.MyBatchPlugin"/>

        <plugin type="org.mybatis.generator.plugins.JsonIgnorePlugin"/>

        <plugin type="com.douyu.wsd.mybatis.generator.plugins.GenerateMvcCodePlugin">
            <property name="basePackage" value="@packageName@"/>
            <property name="daoXmlPackage" value="@artifactId@"/>
        </plugin>

        <!-- 这里的type里写的是你的实现类的类全路径 -->
        <commentGenerator type="org.mybatis.generator.MyCommentGenerator">
            <!-- 这个元素用来去除指定生成的注释中是否包含生成的日期 false:表示保护 -->
            <!-- 如果生成日期，会造成即使修改一个字段，整个实体类所有属性都会发生变化，不利于版本控制，所以设置为true -->
            <property name="suppressDate" value="true"/>
            <!-- 是否去除自动生成的注释 true：是 ： false:否 -->
            <property name="suppressAllComments" value="false"/>
        </commentGenerator>

        <!--数据库链接URL，用户名、密码 -->
        <jdbcConnection driverClass="com.mysql.jdbc.Driver" connectionURL="jdbc:mysql://@databaseIp@:@databasePort@/@databaseName@"
                        userId="@databaseUsername@" password="@databasePassword@">
        </jdbcConnection>

        <javaTypeResolver>
            <!-- This property is used to specify whether MyBatis Generator should
                force the use of java.math.BigDecimal for DECIMAL and NUMERIC fields, -->
            <property name="forceBigDecimals" value="false"/>
        </javaTypeResolver>

        <!-- 生成模型的包名和位置 -->
        <javaModelGenerator targetPackage="@packageName@.domain.entity"
                            targetProject="src/main/generated">
            <property name="enableSubPackages" value="true"/>
            <property name="trimStrings" value="true"/>
        </javaModelGenerator>

        <!-- 生成映射文件的包名和位置 -->
        <sqlMapGenerator targetPackage="@artifactId@"
                         targetProject="src/main/resources/mybatis/mappers/generated">
            <property name="enableSubPackages" value="true"/>
        </sqlMapGenerator>

        <!-- 生成DAO的包名和位置 -->
        <javaClientGenerator type="XMLMAPPER"
                             targetPackage="@packageName@.mapper" targetProject="src/main/generated">
            <property name="enableSubPackages" value="true"/>
        </javaClientGenerator>

        <!-- 要生成哪些表 -->
    <#assign tableMapping = @tableMapping@>
    <#list tableMapping as k, v>
        <table tableName="${k}" domainObjectName="${v}"
               enableCountByExample="true" enableUpdateByExample="true"
               enableDeleteByExample="false" enableSelectByExample="true"
               selectByExampleQueryId="true">
            <generatedKey column="id" sqlStatement="MySql" identity="true"/>
        </table>

    </#list>

    </context>
</generatorConfiguration>