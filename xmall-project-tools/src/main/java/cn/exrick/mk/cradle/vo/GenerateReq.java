package cn.exrick.mk.cradle.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class GenerateReq {

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * pom里的项目groupId
     */
    @NotBlank(message = "groupId不能为空")
    @Length(min = 1, max = 32, message = "groupId长度不符合要求")
    private String groupId;

    /**
     * pom里的项目artifactId
     */
    @NotBlank(message = "artifactId不能为空")
    @Length(min = 1, max = 32, message = "artifactId长度不符合要求")
    private String artifactId;

    /**
     * pom里的项目version
     */
    private String version = "1.0.0-SNAPSHOT";


    private boolean useCommon;


    private String commonArtifactId;


    private String commonPackageName;


    private boolean useExe1;


    private String exeArtifactId1;


    private String exePackageName1;


    private boolean useExe2;


    private String exeArtifactId2;


    private String exePackageName2;


    private boolean useExe3;


    private String exeArtifactId3;


    private String exePackageName3;


    private boolean commonUseMybatis;


    private boolean commonUseDubbo;


    private boolean commonUseRestTemplate;


    private boolean exeUseMybatis1;


    private boolean exeUseDubbo1;


    private boolean exeUseRestTemplate1;


    private boolean exeUseMybatis2;


    private boolean exeUseDubbo2;


    private boolean exeUseRestTemplate2;


    private boolean exeUseMybatis3;


    private boolean exeUseDubbo3;


    private boolean exeUseRestTemplate3;

    private boolean useLocalModel;

    private String localPath;

    private String commonDatabaseIp;

    private Integer commonDatabasePort;

    private String commonDatabaseName;

    private String commonDatabaseUsername;

    private String commonDatabasePassword;

    private String exeDatabaseIp1;

    private Integer exeDatabasePort1;

    private String exeDatabaseName1;

    private String exeDatabaseUsername1;

    private String exeDatabasePassword1;

    private String exeDatabaseIp2;

    private Integer exeDatabasePort2;

    private String exeDatabaseName2;

    private String exeDatabaseUsername2;

    private String exeDatabasePassword2;

    private String exeDatabaseIp3;

    private Integer exeDatabasePort3;

    private String exeDatabaseName3;

    private String exeDatabaseUsername3;

    private String exeDatabasePassword3;

    private boolean exeUseOa1;

    private boolean exeUseOa2;

    private boolean exeUseOa3;

}
