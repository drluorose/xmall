package cn.exrick.manager.dto.front;

import cn.exrick.manager.pojo.TbAddressItem;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class AddressItemDto implements Serializable {

    private String id;

    private int level;

    private int countryCode;

    private int code;

    private String name;

    private String nameCn;

    public AddressItemDto() {
    }

    public AddressItemDto(TbAddressItem item) {
        this.id = item.getId();
        this.level = item.getLevel();
        this.countryCode = item.getCountryCode();
        this.code = item.getCode();
        this.name = item.getName();
        this.nameCn = item.getNameCn();
    }
}
