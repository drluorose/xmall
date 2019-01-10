package cn.exrick.common.mail;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Slf4j
public class MailUtils {

    /**
     * 发送注册营销邮件
     *
     * @param region
     * @param mailTemplate
     * @param accessKey
     * @param accessSecret
     *
     * @return
     *
     * @throws com.aliyuncs.exceptions.ClientException
     */
    public static MailResponse sendEmail(Region region, MailTemplate mailTemplate, String accessKey, String accessSecret) throws com.aliyuncs.exceptions.ClientException {
        // 如果是除杭州region外的其它region（如新加坡、澳洲Region），需要将下面的"cn-hangzhou"替换为"ap-southeast-1"、或"ap-southeast-2"。
        IClientProfile profile = DefaultProfile.getProfile(region.getRegionId(), accessKey, accessSecret);
        if (!region.isHanzhouRegin()) {
            DefaultProfile.addEndpoint(region.getEndpointName(),
                region.getRegionId(),
                region.getProduct(),
                region.getDomain());
        }
        // 如果是除杭州region外的其它region（如新加坡region）， 需要做如下处理

        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        if (!region.isHanzhouRegin()) {
            request.setVersion(region.getRequestVersion());
        }
        request.setAccountName(mailTemplate.getAccountName());
        request.setFromAlias(mailTemplate.getFromAlias());
        request.setAddressType(mailTemplate.getAddressType());
        request.setTagName(mailTemplate.getTagName());
        request.setReplyToAddress(mailTemplate.getReplayToAddress());
        request.setToAddress(mailTemplate.getToAddress());
        request.setSubject(mailTemplate.getSubject());
        request.setHtmlBody(mailTemplate.getHtmlBody());
        SingleSendMailResponse httpResponse = client.getAcsResponse(request);
        MailResponse mailResponse = new MailResponse();
        mailResponse.setEnvId(httpResponse.getEnvId());
        mailResponse.setRequestId(httpResponse.getRequestId());
        return mailResponse;
    }
}
