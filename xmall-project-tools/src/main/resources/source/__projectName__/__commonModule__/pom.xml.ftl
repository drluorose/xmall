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
    </dependencies>


    <build>
        <plugins>
<#noparse>
            <plugin>
                <artifactId>maven-source-plugin</artifactId>
                <executions>
                    <execution>
                        <id>attach-sources</id>
                        <goals>
                            <goal>jar</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <artifactId>maven-jar-plugin</artifactId>
                <configuration>
                    <archive>
                        <addMavenDescriptor>true</addMavenDescriptor>
                        <index>true</index>
                        <manifest>
                            <addDefaultSpecificationEntries>true</addDefaultSpecificationEntries>
                            <addDefaultImplementationEntries>true</addDefaultImplementationEntries>
                        </manifest>
                    </archive>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-deploy-plugin</artifactId>
                <version>${maven_deploy_version}</version>
                <configuration>
                    <skip>${skip_maven_deploy}</skip>
                </configuration>
            </plugin>
</#noparse>

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


    <distributionManagement>
        <repository>
            <id>nexus-dy</id>
            <name>lib-releases</name>
            <url>http://10.1.100.15:8081/nexus/content/repositories/releases/</url>
        </repository>
        <snapshotRepository>
            <id>nexus-dy</id>
            <name>lib-snapshots</name>
            <url>http://10.1.100.15:8081/nexus/content/repositories/snapshots/</url>
        </snapshotRepository>
    </distributionManagement>

</project>