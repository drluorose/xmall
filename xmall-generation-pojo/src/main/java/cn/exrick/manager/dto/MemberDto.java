package cn.exrick.manager.dto;

import cn.exrick.manager.pojo.TbMember;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Exrick
 * @date 2017/8/25
 */
@Data
public class MemberDto implements Serializable {


    private String username;

    private String password;

    private String phone;

    private String email;

    private String sex;

    private String province;

    private String city;

    private String district;

    private Integer state;

    private String memberfile;

    private String description;

    public MemberDto() {
    }

    public MemberDto(TbMember tbMember) {
        this();
        this.username = tbMember.getUsername();
        this.email = tbMember.getEmail();
        this.state = tbMember.getState().getNumber();
        this.memberfile = tbMember.getFile();
    }

}
