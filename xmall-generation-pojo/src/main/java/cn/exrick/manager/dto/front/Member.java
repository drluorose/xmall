package cn.exrick.manager.dto.front;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Exrickx
 */
@Data
public class Member implements Serializable{

    private Long id;

    private String username;

    private String phone;

    private String email;

    private String sex;

    private String address;

    private String file;

    private String description;

    private Integer points;

    private BigDecimal balance;

    private int state;

    private String token;

    private String message;


}
