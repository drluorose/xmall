package cn.exrick.manager.pojo;

import java.util.Date;
import lombok.Data;

@Data
public class TbUser {
    private Long id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private String sex;

    private String address;

    private Integer state;

    private String description;

    private Integer roleId;

    private String file;

    private Date created;

    private Date updated;
}