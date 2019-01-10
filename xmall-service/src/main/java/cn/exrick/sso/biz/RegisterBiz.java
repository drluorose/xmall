package cn.exrick.sso.biz;

import cn.exrick.common.enums.DoStatusEnum;
import cn.exrick.manager.pojo.MemberVerify;
import com.aliyuncs.exceptions.ClientException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
public interface RegisterBiz {
    /**
     * email verify
     *
     * @param id
     * @param content
     *
     * @return
     */
    boolean emailVerify(String id, String content);

    /**
     * sendVerifyEmail
     *
     * @param memberVerify
     *
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException
     * @throws ClientException
     */
    boolean sendVerifyEmail(MemberVerify memberVerify);

    /**
     * 获取未发送邮件的记录
     *
     * @param doStatusEnum
     *
     * @return
     */
    List<MemberVerify> getMemberVerify(DoStatusEnum doStatusEnum);

    /**
     * 重新发送验证email
     *
     * @param mid   mid
     * @param email email
     *
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException
     * @throws ClientException
     */
    void sendVerifyEmailAgain(String mid, String email) throws NoSuchPaddingException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidAlgorithmParameterException, ClientException;

    /**
     * 新注册一个用户
     *
     * @param email
     * @param userPwd
     *
     * @throws NoSuchPaddingException
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws ClientException
     * @throws InvalidKeyException
     */
    void registerNew(String email, String userPwd) throws NoSuchPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, ClientException, InvalidKeyException;
}
