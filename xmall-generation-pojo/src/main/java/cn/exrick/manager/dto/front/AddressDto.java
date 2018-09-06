package cn.exrick.manager.dto.front;

import cn.exrick.manager.pojo.TbCity;
import cn.exrick.manager.pojo.TbCountry;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class AddressDto implements Serializable {

    private Long addressId;

    private Long userId;

    private String mid;

    private String userName;

    private String tel;

    private String streetName;

    private Boolean isDefault;

    private TbCity tbCity;

    private TbCountry tbCountry;
}
