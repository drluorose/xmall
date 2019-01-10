package cn.exrick.sso.service.impl;

import cn.exrick.common.enums.MemberStateEnum;
import cn.exrick.manager.mapper.TbMemberMapper;
import cn.exrick.manager.pojo.TbMember;
import cn.exrick.manager.pojo.TbMemberExample;
import cn.exrick.sso.biz.RegisterBiz;
import cn.exrick.sso.service.RegisterService;
import com.alibaba.dubbo.config.annotation.Service;
import com.aliyuncs.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * @author Exrickx
 */
@Slf4j
@Component
@Service(interfaceClass = RegisterService.class)
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private TbMemberMapper tbMemberMapper;

    @Autowired
    private RegisterBiz registerBiz;

    @Override
    public boolean verifyCallback(String id, String content) {
        return registerBiz.emailVerify(id, content);
    }

    @Override
    public void register(String email, String userPwd) {
        if (StringUtils.isBlank(email) || StringUtils.isBlank(userPwd)) {
            throw new IllegalArgumentException("Arg(email:" + email + ",userPwd:******) is bad");
        }

        TbMember memberInDb = getMemberStateByEmail(email);
        if (Objects.isNull(memberInDb)) {
            try {
                registerBiz.registerNew(email, userPwd);
            } catch (ClientException e) {
                log.error("ClientException", e);
            } catch (UnsupportedEncodingException | BadPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException e) {
                log.error("Other", e);
            }
        }

        if (memberInDb.getState() == MemberStateEnum.NEW) {
            try {
                registerBiz.sendVerifyEmailAgain(memberInDb.getMid(), email);
            } catch (ClientException e) {
                log.error("ClientException", e);
            } catch (UnsupportedEncodingException | BadPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException e) {
                log.error("Other", e);
            }
        }
    }

    private TbMember getMemberStateByEmail(String email) {
        TbMemberExample example = new TbMemberExample();
        example.createCriteria().andEmailEqualTo(email);
        TbMember tbMember = tbMemberMapper.selectOneByExample(example);
        if (Objects.isNull(tbMember)) {
            return null;
        }
        return tbMember;
    }

}
