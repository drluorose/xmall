package cn.exrick.sso.service;

import cn.exrick.manager.dto.front.AddressDto;
import cn.exrick.manager.pojo.TbAddress;
import cn.exrick.manager.pojo.TbAddressItem;

import java.util.List;

/**
 * @author Exrickx
 */
public interface AddressService {

    /**
     * 获取地址
     *
     * @param mid
     *
     * @return
     */
    List<AddressDto> getAddressList(String mid);

    /**
     * 获取单个
     *
     * @param addressId
     *
     * @return
     */
    TbAddress getAddress(Long addressId);

    /**
     * 添加
     *
     * @param tbAddress
     *
     * @return
     */
    void addAddress(TbAddress tbAddress, List<TbAddressItem> items);

    /**
     * 更新地址信息
     *
     * @param tbAddress
     * @param items
     *
     * @return
     */
    void updateAddress(TbAddress tbAddress, List<TbAddressItem> items);

    /**
     * 删除
     *
     * @param tbAddress
     *
     * @return
     */
    int delAddress(TbAddress tbAddress);
}
