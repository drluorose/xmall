<?xml version="1.0" encoding="UTF-8"?>
<project name="generate" default="generateProject">

    <target name="global.copy">
        <copy todir="${outputBaseDir}">
            <!-- 拷贝外层文件 -->
            <fileset dir="${inputBaseDir}/__projectName__">
                <exclude name="__commonModule__/**"/>
                <exclude name="__exeModule__/**"/>
            </fileset>
        </copy>
        <delete file="${outputBaseDir}/build.xml"/>
    </target>

<#if commonModule??>
    <target name="commonModule.copy">
        <copy todir="${outputBaseDir}/${commonModule.name}">
            <!-- 拷贝外层文件 -->
            <fileset dir="${inputBaseDir}/__projectName__/__commonModule__">
                <exclude name="src/main/java/**"/>
                <#if commonModule.useMybatis == false>
                    <exclude name="src/main/resources/mybatis/**"/>
                </#if>
            </fileset>
        </copy>

        <!-- 拷贝java代码 -->
        <copy todir="${outputBaseDir}/${commonModule.name}/src/main/java/${commonModule.packageName?replace('.', '/')}">
            <fileset
                    dir="${inputBaseDir}/__projectName__/__commonModule__/src/main/java/__packageName__">
                <#if commonModule.useRestTemplate == false>
                    <exclude name="config/RestTemplateConfig.java.ftl"/>
                </#if>
                <#if commonModule.useMybatis == false>
                    <exclude name="config/MybatisDaoAutoConfiguration.java.ftl"/>
                </#if>
            </fileset>
        </copy>
    </target>


    <target name="commonModule.replace" depends="commonModule.copy">
        <!-- 替换代码中的包名 -->
        <replace dir="${outputBaseDir}/${commonModule.name}/src/main/java">
            <replacefilter token="${packageNameTag}" value="${commonModule.packageName}"/>
            <replacefilter token="${useMybatisTag}" value="${commonModule.useMybatis?c}"/>
            <replacefilter token="${useRestTemplateTag}" value="${commonModule.useRestTemplate?c}"/>
            <replacefilter token="${useDubboTag}" value="${commonModule.useDubbo?c}"/>
        </replace>

    <#--<sleep seconds="200"/>-->

        <!-- 替换pom中的artifactId -->
        <replace file="${outputBaseDir}/${commonModule.name}/pom.xml.ftl">
            <replacefilter token="${artifactIdTag}" value="${commonModule.artifactId}"/>
            <replacefilter token="${useMybatisTag}" value="${commonModule.useMybatis?c}"/>
            <replacefilter token="${useRestTemplateTag}" value="${commonModule.useRestTemplate?c}"/>
            <replacefilter token="${useDubboTag}" value="${commonModule.useDubbo?c}"/>
        </replace>

    <#if commonModule.useMybatis == true>
        <!-- 替换mybatis generator中的变量 -->
        <replace dir="${outputBaseDir}/${commonModule.name}/src/main/resources/mybatis">
            <replacefilter token="${artifactIdTag}" value="${commonModule.artifactId}"/>
            <replacefilter token="${packageNameTag}" value="${commonModule.packageName}"/>
            <replacefilter token="${databaseIpTag}" value="${commonModule.databaseIp}"/>
            <replacefilter token="${databasePortTag}" value="${commonModule.databasePort?c}"/>
            <replacefilter token="${databaseNameTag}" value="${commonModule.databaseName}"/>
            <replacefilter token="${databaseUsernameTag}" value="${commonModule.databaseUsername}"/>
            <replacefilter token="${databasePasswordTag}" value="${commonModule.databasePassword}"/>
            <replacefilter token="${tableMappingTag}" value='${commonModule.tableMapping}'/>
        </replace>

        <!-- 替换yml配置中的变量 -->
        <replace dir="${outputBaseDir}/${exeModule.name}/src/main/resources">
            <replacefilter token="${databaseIpTag}" value="${commonModule.databaseIp}"/>
            <replacefilter token="${databasePortTag}" value="${commonModule.databasePort?c}"/>
            <replacefilter token="${databaseNameTag}" value="${commonModule.databaseName}"/>
            <replacefilter token="${databaseUsernameTag}" value="${commonModule.databaseUsername}"/>
            <replacefilter token="${databasePasswordTag}" value="${commonModule.databasePassword}"/>
        </replace>

        <mkdir dir="${outputBaseDir}/${commonModule.name}/src/main/generated"/>
        <mkdir dir="${outputBaseDir}/${exeModule.name}/src/main/resources/mybatis/mappers/generated"/>
        <mkdir dir="${outputBaseDir}/${exeModule.name}/src/main/resources/mybatis/mappers/custom"/>
    </#if>
    </target>


    <target name="commonModule" depends="commonModule.replace">
    </target>
</#if>


<#list exeModules as exeModule>
<#--文件拷贝-->
    <target name="exeModule.${exeModule.name}.copy">
        <copy todir="${outputBaseDir}/${exeModule.name}">
            <fileset dir="${inputBaseDir}/__projectName__/__exeModule__">
                <exclude name="src/main/java/**"/>
            <#if exeModule.useMybatis == false>
                <exclude name="src/main/resources/mybatis/**"/>
            </#if>
            </fileset>
        </copy>
        <copy todir="${outputBaseDir}/${exeModule.name}/src/main/java/${exeModule.packageName?replace('.', '/')}">
            <fileset
                    dir="${inputBaseDir}/__projectName__/__exeModule__/src/main/java/__packageName__">
                <#if exeModule.useRestTemplate == false>
                    <exclude name="config/RestTemplateConfig.java.ftl"/>
                </#if>
                <#if exeModule.useMybatis == false>
                    <exclude name="config/MybatisDaoAutoConfiguration.java.ftl"/>
                </#if>
                <#if exeModule.useOa == false>
                    <exclude name="config/DouyuOaConfig.java.ftl"/>
                    <exclude name="facade/controller/LoginOAController.java.ftl"/>
                    <exclude name="facade/controller/LogoutController.java.ftl"/>
                    <exclude name="service/impl/OaServiceImpl.java.ftl"/>
                </#if>
            </fileset>
        </copy>
    </target>

    <#--文件内容替换-->
    <target name="exeModule.${exeModule.name}.replace" depends="exeModule.${exeModule.name}.copy">
        <!-- 替换代码中的包名 -->
        <replace dir="${outputBaseDir}/${exeModule.name}/src/main/java">
            <replacefilter token="${packageNameTag}" value="${exeModule.packageName}"/>
            <replacefilter token="${useMybatisTag}" value="${exeModule.useMybatis?c}"/>
            <replacefilter token="${useRestTemplateTag}" value="${exeModule.useRestTemplate?c}"/>
            <replacefilter token="${useDubboTag}" value="${exeModule.useDubbo?c}"/>
            <replacefilter token="${useOaTag}" value="${exeModule.useOa?c}"/>
        </replace>

        <!-- 替换resources中的包名 -->
        <replace dir="${outputBaseDir}/${exeModule.name}/src/main/resources">
            <replacefilter token="${useMybatisTag}" value="${exeModule.useMybatis?c}"/>
            <replacefilter token="${useRestTemplateTag}" value="${exeModule.useRestTemplate?c}"/>
            <replacefilter token="${useDubboTag}" value="${exeModule.useDubbo?c}"/>
            <replacefilter token="${useOaTag}" value="${exeModule.useOa?c}"/>
        </replace>

        <!-- 替换pom中的artifactId -->
        <replace file="${outputBaseDir}/${exeModule.name}/pom.xml.ftl">
            <replacefilter token="${artifactIdTag}" value="${exeModule.artifactId}"/>
            <replacefilter token="${useMybatisTag}" value="${exeModule.useMybatis?c}"/>
            <replacefilter token="${useRestTemplateTag}" value="${exeModule.useRestTemplate?c}"/>
            <replacefilter token="${useDubboTag}" value="${exeModule.useDubbo?c}"/>
            <replacefilter token="${useOaTag}" value="${exeModule.useOa?c}"/>
        </replace>

        <#if exeModule.useMybatis == true>
            <!-- 替换mybatis generator中的变量 -->
            <replace dir="${outputBaseDir}/${exeModule.name}/src/main/resources/mybatis">
                <replacefilter token="${artifactIdTag}" value="${exeModule.artifactId}"/>
                <replacefilter token="${packageNameTag}" value="${exeModule.packageName}"/>
                <replacefilter token="${databaseIpTag}" value="${exeModule.databaseIp}"/>
                <replacefilter token="${databasePortTag}" value="${exeModule.databasePort?c}"/>
                <replacefilter token="${databaseNameTag}" value="${exeModule.databaseName}"/>
                <replacefilter token="${databaseUsernameTag}" value="${exeModule.databaseUsername}"/>
                <replacefilter token="${databasePasswordTag}" value="${exeModule.databasePassword}"/>
                <replacefilter token="${tableMappingTag}" value='${exeModule.tableMapping}'/>
            </replace>

            <!-- 替换yml配置中的变量 -->
            <replace dir="${outputBaseDir}/${exeModule.name}/src/main/resources">
                <replacefilter token="${databaseIpTag}" value="${exeModule.databaseIp}"/>
                <replacefilter token="${databasePortTag}" value="${exeModule.databasePort?c}"/>
                <replacefilter token="${databaseNameTag}" value="${exeModule.databaseName}"/>
                <replacefilter token="${databaseUsernameTag}" value="${exeModule.databaseUsername}"/>
                <replacefilter token="${databasePasswordTag}" value="${exeModule.databasePassword}"/>
            </replace>

            <mkdir dir="${outputBaseDir}/${exeModule.name}/src/main/generated"/>
            <mkdir dir="${outputBaseDir}/${exeModule.name}/src/main/resources/mybatis/mappers/generated"/>
            <mkdir dir="${outputBaseDir}/${exeModule.name}/src/main/resources/mybatis/mappers/custom"/>
        </#if>
    </target>
</#list>

    <target name="exeModule"
            depends="<#list exeModules as exeModule>exeModule.${exeModule.name}.replace<#if (exeModule_has_next)>,</#if></#list>">

    </target>

    <target name="generateProject" depends="global.copy<#if commonModule??>,commonModule</#if>,exeModule">
        <#--<sleep seconds="400"/>-->
    </target>

</project>