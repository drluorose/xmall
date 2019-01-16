package cn.exrick.manager.pojo;

import java.io.Serializable;
import lombok.Data;

@Data
public class TbAddressItem implements Serializable {
    private String id;

    private String addressId;

    private Integer level;

    private Integer countryCode;

    private Integer code;

    private String name;

    private String nameCn;

    private static final long serialVersionUID = 1L;
}