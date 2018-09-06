package cn.exrick.sso.service.impl;

import cn.exrick.common.enums.EnableStatusEnum;
import cn.exrick.common.exception.XmallException;
import cn.exrick.manager.dto.front.AddressDto;
import cn.exrick.manager.mapper.TbAddressMapper;
import cn.exrick.manager.mapper.TbCityMapper;
import cn.exrick.manager.mapper.TbCountryMapper;
import cn.exrick.manager.pojo.TbAddress;
import cn.exrick.manager.pojo.TbAddressExample;
import cn.exrick.manager.pojo.TbCity;
import cn.exrick.manager.pojo.TbCityExample;
import cn.exrick.manager.pojo.TbCountry;
import cn.exrick.manager.pojo.TbCountryExample;
import cn.exrick.sso.service.AddressService;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * ALTER USER 'root'@'localhost' IDENTIFIED BY 'EusbI7pr91%h$3BC';
 *
 * @author Exrickx
 */
@Slf4j
@Component
@Service(interfaceClass = AddressService.class)
public class AddressServiceImpl implements AddressService {

    @Autowired
    private TbAddressMapper tbAddressMapper;

    @Autowired
    private TbCountryMapper tbCountryMapper;

    @Autowired
    private TbCityMapper tbCityMapper;

    @Override
    public List<TbCountry> getCountryList() {
        TbCountryExample tbCountryExample = new TbCountryExample();
        tbCountryExample.createCriteria().andStatusEqualTo(EnableStatusEnum.ENABLED);
        List<TbCountry> tbCountryList = this.tbCountryMapper.selectByExample(tbCountryExample);
        if (CollectionUtils.isEmpty(tbCountryList)) {
            tbCountryList = Lists.newArrayList();
        }
        return tbCountryList;
    }

    @Override
    public List<TbCity> getCountryCites(Integer countryId) {
        if (Objects.isNull(countryId)) {
            throw new IllegalArgumentException("Country Id is null");
        }
        TbCityExample tbCityExample = new TbCityExample();
        tbCityExample.createCriteria().andCountryIdEqualTo(countryId);
        List<TbCity> cities = tbCityMapper.selectByExample(tbCityExample);
        if (CollectionUtils.isEmpty(cities)) {
            cities = Lists.newArrayList();
        }
        return cities;
    }

    @Override
    public List<AddressDto> getAddressList(String mid) {

        List<TbAddress> list = null;
        TbAddressExample example = new TbAddressExample();
        TbAddressExample.Criteria criteria = example.createCriteria();
        criteria.andMidEqualTo(mid);
        list = tbAddressMapper.selectByExample(example);
        if (Objects.isNull(list)) {
            list = Lists.newArrayList();
        }
        List<AddressDto> addressDtos = Lists.newArrayListWithCapacity(list.size());
        list.forEach(tbAddress -> {
            AddressDto addressDto = new AddressDto();
            addressDto.setAddressId(tbAddress.getAddressId());
            addressDto.setIsDefault(tbAddress.getIsDefault());
            addressDto.setMid(tbAddress.getMid());
            addressDto.setStreetName(tbAddress.getStreetName());
            TbCity tbCity = tbCityMapper.selectByPrimaryKey(tbAddress.getCityId());
            TbCountry tbCountry = null;
            if (Objects.nonNull(tbCity)) {
                tbCountry = tbCountryMapper.selectByPrimaryKey(tbCity.getCountryId());
            }
            addressDto.setTbCity(tbCity);
            addressDto.setTbCountry(tbCountry);
            addressDto.setTel(tbAddress.getTel());
            addressDto.setUserId(tbAddress.getUserId());
            addressDto.setUserName(tbAddress.getUserName());
            addressDtos.add(addressDto);
        });
        for (int i = 0; i < addressDtos.size(); i++) {
            if (list.get(i).getIsDefault()) {
                Collections.swap(addressDtos, 0, i);
                break;
            }
        }

        return addressDtos;
    }

    @Override
    public TbAddress getAddress(Long addressId) {

        TbAddress tbAddress = tbAddressMapper.selectByPrimaryKey(addressId);
        if (tbAddress == null) {
            throw new XmallException("通过id获取地址失败");
        }
        return tbAddress;
    }

    @Override
    public int addAddress(TbAddress tbAddress) {

        //设置唯一默认
        setOneDefault(tbAddress);
        if (tbAddressMapper.insert(tbAddress) != 1) {
            throw new XmallException("添加地址失败");
        }
        return 1;
    }

    @Override
    public int updateAddress(TbAddress tbAddress) {

        //设置唯一默认
        setOneDefault(tbAddress);
        if (tbAddressMapper.updateByPrimaryKey(tbAddress) != 1) {
            throw new XmallException("更新地址失败");
        }
        return 1;
    }

    @Override
    public int delAddress(TbAddress tbAddress) {

        if (tbAddressMapper.deleteByPrimaryKey(tbAddress.getAddressId()) != 1) {
            throw new XmallException("删除地址失败");
        }
        return 1;
    }

    public void setOneDefault(TbAddress tbAddress) {
        //设置唯一默认
        if (tbAddress.getIsDefault()) {
            TbAddressExample example = new TbAddressExample();
            TbAddressExample.Criteria criteria = example.createCriteria();
            criteria.andUserIdEqualTo(tbAddress.getUserId());
            List<TbAddress> list = tbAddressMapper.selectByExample(example);
            for (TbAddress tbAddress1 : list) {
                tbAddress1.setIsDefault(false);
                tbAddressMapper.updateByPrimaryKey(tbAddress1);
            }
        }
    }
}
