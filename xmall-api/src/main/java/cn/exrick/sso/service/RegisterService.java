package cn.exrick.sso.service;

/**
 * @author Exrickx
 */
public interface RegisterService {

    /**
     * 勾选
     *
     * @param id
     * @param content
     *
     * @return
     */
    boolean verifyCallback(String id, String content);

    /**
     * 注册
     *
     * @param email
     * @param userPwd
     *
     * @return
     */
    void register(String email, String userPwd);
}
