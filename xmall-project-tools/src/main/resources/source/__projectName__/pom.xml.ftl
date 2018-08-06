<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>${groupId}</groupId>
    <artifactId>${artifactId}</artifactId>
    <version>${version}</version>
    <packaging>pom</packaging>
    <description>${description}</description>
    <url></url>

    <organization>
        <name>Douyu, Inc.</name>
        <url>http://www.douyu.com</url>
    </organization>

    <properties>
        <java_source_version>1.8</java_source_version>
        <java_target_version>1.8</java_target_version>
        <file_encoding>UTF-8</file_encoding>
        <spring_boot_version>1.5.10.RELEASE</spring_boot_version>
        <skip_maven_deploy>false</skip_maven_deploy>
        <maven_deploy_version>2.8.2</maven_deploy_version>
    </properties>

    <modules>
        <#if commonModule??>
            <module>${commonModule.artifactId}</module>
        </#if>
        <#list exeModules as exeModule>
            <module>${exeModule.artifactId}</module>
        </#list>
    </modules>

<#noparse>
    <!-- 继承spring boot的依赖管理 START -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.10.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <!-- 继承spring boot的依赖管理 END -->

    <dependencies>
        <!-- 内部基础类 START -->
        <dependency>
            <groupId>com.douyu.wsd-framework</groupId>
            <artifactId>framework-common</artifactId>
            <version>1.0.1-SNAPSHOT</version>
        </dependency>
        <!-- 内部基础类 END -->

        <!-- api 描述文档 START -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.7.0</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.7.0</version>
        </dependency>
        <!-- api 描述文档 END -->

        <!-- wsd-基础监控服务 START -->
        <dependency>
            <groupId>com.douyu.wsd</groupId>
            <artifactId>wsd-starter-actuator</artifactId>
            <version>1.0.0-SNAPSHOT</version>
        </dependency>
        <!-- wsd-基础监控服务 END -->

        <!-- 代码生成器 START -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.16.18</version>
            <scope>provided</scope>
        </dependency>
        <!-- 代码生成器 END -->
    </dependencies>

    <build>
        <plugins>
            <!-- maven 编译插件 START -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>${java_source_version}</source>
                    <target>${java_target_version}</target>
                    <encoding>${file_encoding}</encoding>
                </configuration>
            </plugin>
            <!-- maven 编译插件 END -->
        </plugins>
    </build>
</#noparse>

</project>