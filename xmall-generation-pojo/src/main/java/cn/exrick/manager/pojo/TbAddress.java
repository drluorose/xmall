package cn.exrick.manager.pojo;

import java.io.Serializable;
import lombok.Data;

@Data
public class TbAddress implements Serializable {
    private Long addressId;

    private Long userId;

    private String mid;

    private Integer cityId;

    private String userName;

    private String tel;

    private String streetName;

    private Boolean isDefault;

    private static final long serialVersionUID = 1L;
}