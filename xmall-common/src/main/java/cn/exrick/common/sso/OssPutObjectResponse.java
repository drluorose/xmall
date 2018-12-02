package cn.exrick.common.sso;

import lombok.Data;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class OssPutObjectResponse {

    private String eTag;

    private String requestId;

    private Long clientCRC;

    private Long serverCRC;

    private String responseStr;
}
