package cn.exrick.manager.pojo;

import cn.exrick.common.enums.MemberStateEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class TbMember implements Serializable {
    private Long id;

    private String mid;

    private String username;

    private String password;

    private String phone;

    private String email;

    private Date created;

    private Date updated;

    private String sex;

    private String address;

    private MemberStateEnum state;

    private String file;

    private String description;

    private Integer points;

    private BigDecimal balance;

    private Integer valid;

    private static final long serialVersionUID = 1L;
}