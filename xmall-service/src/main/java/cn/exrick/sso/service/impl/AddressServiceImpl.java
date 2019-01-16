package cn.exrick.sso.service.impl;

import cn.exrick.common.enums.ValidStatusEnum;
import cn.exrick.common.exception.XmallException;
import cn.exrick.common.utils.ObjectId;
import cn.exrick.manager.dto.front.AddressDto;
import cn.exrick.manager.mapper.TbAddressItemMapper;
import cn.exrick.manager.mapper.TbAddressMapper;
import cn.exrick.manager.pojo.TbAddress;
import cn.exrick.manager.pojo.TbAddressExample;
import cn.exrick.manager.pojo.TbAddressItem;
import cn.exrick.manager.pojo.TbAddressItemExample;
import cn.exrick.sso.service.AddressService;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    private TbAddressItemMapper tbAddressItemMapper;

    @Override
    public List<AddressDto> getAddressList(String mid) {

        TbAddressExample example = new TbAddressExample();
        example.createCriteria().andMidEqualTo(mid);

        List<TbAddress> addresses = this.tbAddressMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(addresses)) {
            return Lists.newArrayList();
        }
        List<AddressDto> addressDtos = Lists.newArrayListWithCapacity(addresses.size());
        for (TbAddress tbAddress : addresses) {
            TbAddressItemExample itemExample = new TbAddressItemExample();
            itemExample.createCriteria().andAddressIdEqualTo(tbAddress.getId());
            itemExample.setOrderByClause("level ASC");
            List<TbAddressItem> tbAddressItems = tbAddressItemMapper.selectByExample(itemExample);
            addressDtos.add(new AddressDto(tbAddress, tbAddressItems));
        }
        return addressDtos;
    }

    @Override
    public TbAddress getAddress(Long addressId) {

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addAddress(TbAddress tbAddress, List<TbAddressItem> items) {
        if (Objects.isNull(tbAddress) || CollectionUtils.isEmpty(items)) {
            throw new IllegalArgumentException("参数错误");
        }
        TbAddressExample example = new TbAddressExample();
        example.createCriteria().andMidEqualTo(tbAddress.getMid())
            .andValidEqualTo(ValidStatusEnum.VALID);
        List<TbAddress> tbAddressValidList = this.tbAddressMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(tbAddressValidList) && tbAddressValidList.size() > 5) {
            throw new XmallException("当前已经达到最大邮件地址数");
        }
        if (tbAddress.getIsDefault()) {
            TbAddress tbAddressUpdateRecord = new TbAddress();
            tbAddress.setIsDefault(false);
            tbAddressMapper.updateByExample(tbAddressUpdateRecord, example);
        }
        tbAddressMapper.insert(tbAddress);
        for (TbAddressItem tbAddressItem : items) {
            tbAddressItemMapper.insertSelective(tbAddressItem);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAddress(TbAddress tbAddress, List<TbAddressItem> items) {
        if (Objects.isNull(tbAddress) || CollectionUtils.isEmpty(items) || items.size() != 3) {
            throw new IllegalArgumentException("参数错误");
        }
        TbAddressExample example = new TbAddressExample();
        example.createCriteria().andIdEqualTo(tbAddress.getId())
            .andMidEqualTo(tbAddress.getMid()).andValidEqualTo(ValidStatusEnum.VALID);
        TbAddress tbAddressInDb = this.tbAddressMapper.selectOneByExample(example);
        if (Objects.isNull(tbAddressInDb)) {
            throw new IllegalArgumentException("提交的参数不匹配");
        }
        if (tbAddress.getIsDefault()) {
            example = new TbAddressExample();
            example.createCriteria().andMidEqualTo(tbAddress.getMid());
            TbAddress tbAddressUpdateRecord = new TbAddress();
            tbAddressUpdateRecord.setIsDefault(false);
            tbAddressMapper.updateByExampleSelective(tbAddressUpdateRecord, example);
        }

        tbAddressMapper.updateByPrimaryKey(tbAddress);
        TbAddressItemExample itemExample = new TbAddressItemExample();
        itemExample.createCriteria().andAddressIdEqualTo(tbAddress.getId());
        List<TbAddressItem> tbAddressItems = tbAddressItemMapper.selectByExample(itemExample);
        for (int i = 0; i < items.size(); i++) {
            TbAddressItem req = items.get(i);
            TbAddressItem db = null;
            if (Objects.nonNull(tbAddressItems) && tbAddressItems.size() > i) {
                db = tbAddressItems.get(i);
            }
            fill(req, db);
        }
    }

    private void fill(TbAddressItem req, TbAddressItem db) {
        if (Objects.isNull(db)) {
            req.setId(ObjectId.getId());
            tbAddressItemMapper.insertSelective(req);
            return;
        }
        req.setId(db.getId());
        tbAddressItemMapper.updateByPrimaryKeySelective(req);
        return;
    }

    @Override
    public int delAddress(TbAddress tbAddress) {

        return 1;
    }

    public void setOneDefault(TbAddress tbAddress) {
        //设置唯一默认
        if (tbAddress.getIsDefault()) {
            TbAddressExample example = new TbAddressExample();
            TbAddressExample.Criteria criteria = example.createCriteria();
            List<TbAddress> list = tbAddressMapper.selectByExample(example);
            for (TbAddress tbAddress1 : list) {
                tbAddress1.setIsDefault(false);
                tbAddressMapper.updateByPrimaryKey(tbAddress1);
            }
        }
    }
}
