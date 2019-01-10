package cn.exrick.sso.biz.impl;

import cn.exrick.common.enums.DoStatusEnum;
import cn.exrick.common.enums.MemberStateEnum;
import cn.exrick.common.mail.MailTemplate;
import cn.exrick.common.mail.MailUtils;
import cn.exrick.common.mail.Region;
import cn.exrick.common.utils.AesUtil;
import cn.exrick.common.utils.ObjectId;
import cn.exrick.manager.mapper.MemberVerifyMapper;
import cn.exrick.manager.mapper.TbMemberMapper;
import cn.exrick.manager.pojo.MemberVerify;
import cn.exrick.manager.pojo.MemberVerifyExample;
import cn.exrick.manager.pojo.TbMember;
import cn.exrick.manager.pojo.TbMemberExample;
import cn.exrick.sso.biz.RegisterBiz;
import com.aliyuncs.exceptions.ClientException;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Slf4j
@Component
public class RegisterBizImpl implements RegisterBiz {

    @Autowired
    private TbMemberMapper tbMemberMapper;

    @Autowired
    private MemberVerifyMapper memberVerifyMapper;

    @Autowired
    private Region region;

    @Value("${aliyun.accessId}")
    private String accessId;

    @Value("${aliyun.accessKey}")
    private String accessKey;

    @Value("${register.callbackUrl}")
    private String url;

    @Override
    public boolean emailVerify(String id, String secretContent) {
        MemberVerify memberVerify = memberVerifyMapper.selectByPrimaryKey(id);
        if (Objects.isNull(memberVerify)) {
            return false;
        }
        try {
            String key = memberVerify.getSecret();
            String initVector = memberVerify.getInitVector();
            String content = memberVerify.getContent();
            String secretContentWithDb = AesUtil.encrypt(key, initVector, content);

            if (!StringUtils.equals(secretContent, secretContentWithDb)) {
                return false;
            }
            TbMember tbMember = new TbMember();
            tbMember.setState(MemberStateEnum.VERIFIED);
            tbMember.setUpdated(new Date());

            TbMemberExample tbMemberExample = new TbMemberExample();
            tbMemberExample.createCriteria().andMidEqualTo(memberVerify.getMid());

            tbMemberMapper.updateByExampleSelective(tbMember, tbMemberExample);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean sendVerifyEmail(MemberVerify memberVerify) {
        String secretContent = null;
        try {
            String key = memberVerify.getSecret();
            String initVector = memberVerify.getInitVector();
            String content = memberVerify.getContent();
            secretContent = AesUtil.encrypt(key, initVector, content);
        } catch (Exception e) {
            log.error("AesError", e);
            return false;
        }

        TbMemberExample example = new TbMemberExample();
        example.createCriteria().andMidEqualTo(memberVerify.getMid())
            .andStateEqualTo(MemberStateEnum.NEW);
        TbMember tbMember = tbMemberMapper.selectOneByExample(example);
        if (Objects.isNull(tbMember)) {
            return true;
        }
        try {
            MailTemplate mailTemplate = new MailTemplate();
            mailTemplate.setAccountName("account@qqlesson.com");
            mailTemplate.setFromAlias("xmall");
            mailTemplate.setToAddress(tbMember.getEmail());
            mailTemplate.setTagName("account");
            mailTemplate.setSubject("account verify");
            mailTemplate.setHtmlBody(getMemberVerifyUrl(url, memberVerify.getId(), secretContent));
            MailUtils.sendEmail(region, mailTemplate, accessId, accessKey);
            memberVerify.setStatus(DoStatusEnum.DO);
            memberVerifyMapper.updateByPrimaryKeySelective(memberVerify);
            return true;
        } catch (ClientException | UnsupportedEncodingException e) {
            log.error("sendEmail error ", e);
            return false;
        }
    }

    @Override
    public List<MemberVerify> getMemberVerify(DoStatusEnum doStatusEnum) {
        MemberVerifyExample example = new MemberVerifyExample();
        example.createCriteria().andStatusEqualTo(doStatusEnum);
        example.setLimit(100);
        List<MemberVerify> memberVerifies = memberVerifyMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(memberVerifies)) {
            return Lists.newArrayList();
        }
        return memberVerifies;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendVerifyEmailAgain(String mid, String email) {

        MemberVerifyExample example = new MemberVerifyExample();
        example.createCriteria().andMidEqualTo(mid);
        MemberVerify memberVerify = this.memberVerifyMapper.selectOneByExample(example);
        if (Objects.nonNull(memberVerify)) {
            memberVerify.setStatus(DoStatusEnum.UNDO);
            memberVerifyMapper.updateByPrimaryKeySelective(memberVerify);
        } else {
            makeWaitSendEmail(mid);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerNew(String email, String userPwd) {
        TbMember tbMember = saveDb(email, userPwd);
        makeWaitSendEmail(tbMember.getMid());
    }

    private void makeWaitSendEmail(String mid) {
        String content = RandomStringUtils.randomAscii(256);
        String key = RandomStringUtils.randomNumeric(16);
        String initVector = RandomStringUtils.randomNumeric(16);
        MemberVerify memberVerify = new MemberVerify();
        memberVerify.setId(ObjectId.getId());
        memberVerify.setContent(content);
        memberVerify.setCreateTime(new Date());
        memberVerify.setSecret(key);
        memberVerify.setInitVector(initVector);
        memberVerify.setMid(mid);
        memberVerify.setUpdateTime(new Date());
        memberVerifyMapper.insert(memberVerify);
    }

    private TbMember saveDb(String email, String pwd) {
        TbMember tbMember = new TbMember();
        tbMember.setUsername(email);
        tbMember.setEmail(email);
        //MD5加密
        String md5Pass = DigestUtils.md5DigestAsHex(pwd.getBytes());
        tbMember.setPassword(md5Pass);
        tbMember.setState(MemberStateEnum.NEW);
        tbMember.setCreated(new Date());
        tbMember.setUpdated(new Date());
        tbMember.setMid(ObjectId.getId());
        tbMemberMapper.insert(tbMember);
        return tbMember;
    }

    private String getMemberVerifyUrl(String url, String id, String content) throws UnsupportedEncodingException {
        String clickUrl = MessageFormat.format("{0}?id={1}&content={2}", url, id, URLEncoder.encode(content, Charsets.UTF_8.name()));
        return MessageFormat.format("<a href='{0}'>{1}</a>", clickUrl, clickUrl);
    }
}
