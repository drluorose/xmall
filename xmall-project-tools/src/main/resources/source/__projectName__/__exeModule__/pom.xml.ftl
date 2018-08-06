<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>${groupId}</groupId>
        <artifactId>${artifactId}</artifactId>
        <version>${version}</version>
    </parent>

    <artifactId>@artifactId@</artifactId>
    <version>${version}</version>
    <packaging>jar</packaging>
<#noparse>
    <name>${project.artifactId}</name>
</#noparse>
    <description>${description}</description>


    <dependencies>
        <!-- start boot START -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!-- start boot END -->

<#if @useMybatis@ == true>
        <!-- mybatis START -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.9</version>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.9</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.3.2</version>
            <optional>true</optional>
        </dependency>
        <!-- mybatis END -->
</#if>


<#if @useOa@ == true>
        <!-- 斗鱼OA START -->
        <dependency>
            <groupId>com.douyu.wsd-framework</groupId>
            <artifactId>framework-oa</artifactId>
            <version>1.0.0-SNAPSHOT</version>
        </dependency>
        <!-- 斗鱼OA END -->

        <!-- 斗鱼鉴权 START -->
        <dependency>
            <groupId>com.douyu.wsd-framework</groupId>
            <artifactId>framework-simple-pms</artifactId>
            <version>1.0.2.RELEASE</version>
        </dependency>
        <!-- 斗鱼鉴权 END -->
</#if>

<#if @useDubbo@ == true>
    <!-- dubbo START -->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>spring-boot-starter-dubbo-wsd</artifactId>
        <version>2.0.0-SNAPSHOT</version>
    </dependency>
    <!-- dubbo END -->
</#if>

<#if @useRestTemplate@ == true>
        <!-- http client START -->
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient</artifactId>
            <version>4.5.5</version>
        </dependency>
        <dependency>
            <groupId>io.netty</groupId>
            <artifactId>netty-all</artifactId>
            <version>4.1.5.Final</version>
        </dependency>
        <!-- http client END -->
</#if>

        <!-- elk日志收集依赖包 START -->
        <dependency>
            <groupId>net.logstash.logback</groupId>
            <artifactId>logstash-logback-encoder</artifactId>
            <version>4.4</version>
        </dependency>
        <!-- elk日志收集依赖包 END -->
    </dependencies>

    <build>
        <plugins>
            <!-- spring boot 打包插件 START -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <executable>true</executable>
                </configuration>
            </plugin>
            <!-- spring boot 打包插件 END -->

<#if @useMybatis@ == true>
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.6</version>
                <executions>
                    <execution>
                        <id>Generate MyBatis Artifacts</id>
                        <goals>
                            <goal>generate</goal>
                        </goals>
                        <!-- 绑定到generate周期中，防止在其他生命周期中再次生成 -->
                        <phase>generate</phase>
                    </execution>
                </executions>
                <configuration>
                    <configurationFile>src/main/resources/mybatis/generator.xml</configurationFile>
                    <verbose>true</verbose>
                    <overwrite>true</overwrite>
                </configuration>
                <dependencies>
                    <dependency>
                        <groupId>mysql</groupId>
                        <artifactId>mysql-connector-java</artifactId>
                        <version>5.1.45</version>
                    </dependency>
                    <dependency>
                        <groupId>org.mybatis.generator</groupId>
                        <artifactId>mybatis-generator-core</artifactId>
                        <version>1.3.6</version>
                    </dependency>
                    <dependency>
                        <groupId>org.mybatis</groupId>
                        <artifactId>mybatis</artifactId>
                        <version>3.4.6</version>
                    </dependency>
                    <dependency>
                        <groupId>com.douyu.wsd-framework</groupId>
                        <artifactId>mybatis-generator-plus</artifactId>
                        <version>1.0.0-SNAPSHOT</version>
                    </dependency>
                </dependencies>
            </plugin>
            <!--配置多源代码目录，增加generated这个自定义目录-->
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>build-helper-maven-plugin</artifactId>
                <version>3.0.0</version>
                <executions>
                    <execution>
                        <phase>generate-sources</phase>
                        <goals>
                            <goal>add-source</goal>
                        </goals>
                        <configuration>
                            <sources>
                                <source>src/main/generated</source>
                            </sources>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
</#if>
        </plugins>
    </build>
</project>