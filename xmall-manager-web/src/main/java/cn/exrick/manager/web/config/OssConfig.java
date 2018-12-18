package cn.exrick.manager.web.config;

import lombok.Data;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class OssConfig {

    private String accessKeyId;

    private String accessKeySecret;

    private String imgBuckName;

    private String imgBuckOrigin;

    private String endPoint;
}
