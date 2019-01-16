package cn.exrick.front.controller.address.req;

import lombok.Data;

import java.util.List;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class AddAddressReq {

    private String userName;

    private String tel;

    private String address;

    private Boolean isDefault;

    private List<AddressItem> addressCode;
}
