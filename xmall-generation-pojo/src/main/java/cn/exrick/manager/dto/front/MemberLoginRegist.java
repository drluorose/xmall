package cn.exrick.manager.dto.front;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Exrickx
 */
@Data
public class MemberLoginRegist implements Serializable {

    private String userName;

    private String userPwd;

    private String challenge;

    private String validate;

    private String seccode;

    private String statusKey;

}
