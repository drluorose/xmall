package cn.exrick.common.mail;

import lombok.Data;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class MailTemplate {

    private String accountName;

    private String fromAlias;

    private Integer addressType = 1;

    private String tagName;

    private Boolean replayToAddress = true;

    private String toAddress;

    private String subject;

    private String htmlBody;

}
