package cn.exrick.sso.service;

import cn.exrick.manager.pojo.TbAddress;
import cn.exrick.manager.pojo.TbCity;
import cn.exrick.manager.pojo.TbCountry;

import java.util.List;

/**
 * @author Exrickx
 */
public interface AddressService {

    /**
     * 获取国家列表
     *
     * @return
     */
    List<TbCountry> getCountryList();

    /**
     * 通过国家id号获取国家城市
     *
     * @param countryId
     *
     * @return
     */
    List<TbCity> getCountryCites(Integer countryId);

    /**
     * 获取地址
     *
     * @param userId
     *
     * @return
     */
    List<TbAddress> getAddressList(Long userId);

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
    int addAddress(TbAddress tbAddress);

    /**
     * 更新
     *
     * @param tbAddress
     *
     * @return
     */
    int updateAddress(TbAddress tbAddress);

    /**
     * 删除
     *
     * @param tbAddress
     *
     * @return
     */
    int delAddress(TbAddress tbAddress);
}
