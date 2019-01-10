package cn.exrick.sso.service;

import cn.exrick.common.jwt.JwtKeyInfo;
import cn.exrick.manager.dto.front.Member;

import java.security.NoSuchAlgorithmException;

/**
 * @author Exrickx
 */
public interface LoginService {

    /**
     * 登录
     *
     * @param email
     * @param password
     *
     * @return
     */
    Member userLogin(String email, String password) throws NoSuchAlgorithmException;

    /**
     * 通过token获取
     *
     * @param token
     *
     * @return
     */
    Member getUserByToken(String token);

    /**
     * 注销
     *
     * @param token
     *
     * @return
     */
    int logout(String token);

    /**
     * @param mid
     *
     * @return
     */
    JwtKeyInfo getJwtKeyInfo(String mid);
}
