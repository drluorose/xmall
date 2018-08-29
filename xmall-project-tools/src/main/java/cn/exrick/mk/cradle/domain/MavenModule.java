package com.douyu.wsd.cradle.domain;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MavenModule {

    private String name;

    private String packageName;

    private String artifactId;

    private boolean useMybatis;

    private boolean useDubbo;

    private boolean useRestTemplate;

    private String databaseIp = "localhost";

    private Integer databasePort = 3306;

    private String databaseName;

    private String databaseUsername;

    private String databasePassword;

    private String tableMapping;

    private boolean useOa;

}
