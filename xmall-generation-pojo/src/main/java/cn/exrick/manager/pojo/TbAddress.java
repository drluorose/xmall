package cn.exrick.manager.pojo;

import lombok.Data;

@Data
public class TbAddress {
    private Long addressId;

    private Long userId;

    private String userName;

    private String tel;

    private String streetName;

    private Boolean isDefault;
}