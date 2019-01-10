package cn.exrick.common.mail;

import com.aliyuncs.exceptions.ClientException;
import org.junit.Test;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
public class MailUtilsTests {

    @Test
    public void testMailUtils() throws ClientException {
        Region region = new Region("cn-hangzhou", "", "", "");
        MailTemplate mailTemplate = new MailTemplate();
        mailTemplate.setAccountName("account@qqlesson.com");
        mailTemplate.setFromAlias("羽绒商城");
        mailTemplate.setToAddress("dongjiejie@qq.com");
        mailTemplate.setTagName("account");
        mailTemplate.setSubject("account verify");
        mailTemplate.setHtmlBody("hello world");
        MailUtils.sendEmail(region, mailTemplate, "LTAIcklXsm0lpGak", "XsFwUNziSWH1tqnER5j7fAXK3WZbkf");
    }
}
