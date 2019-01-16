package cn.exrick.manager.dto.front;

import cn.exrick.manager.pojo.TbAddress;
import cn.exrick.manager.pojo.TbAddressItem;
import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class AddressDto implements Serializable {

    private String id;

    private String mid;

    private String userName;

    private String tel;

    private String address;

    private Boolean isDefault;

    private List<AddressItemDto> addressCode;

    public AddressDto() {

    }

    public AddressDto(TbAddress address, List<TbAddressItem> items) {
        this.id = address.getId();
        this.mid = address.getMid();
        this.userName = address.getUserName();
        this.tel = address.getTel();
        this.address = address.getAddress();
        this.isDefault = address.getIsDefault();
        this.addressCode = Lists.newArrayListWithCapacity(items.size());
        for (TbAddressItem item : items) {
            AddressItemDto addressItemDto = new AddressItemDto(item);
            addressCode.add(addressItemDto);
        }
    }
}
