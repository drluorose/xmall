package cn.exrick.manager.web.controller.req;

import lombok.Data;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class LoginReq {
    private String username;

    private String password;

    private Integer auth;
}
