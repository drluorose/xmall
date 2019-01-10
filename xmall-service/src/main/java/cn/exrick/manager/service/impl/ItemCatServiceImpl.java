package cn.exrick.manager.service.impl;

import cn.exrick.common.enums.EnableStatusEnum;
import cn.exrick.common.exception.XmallException;
import cn.exrick.manager.mapper.TbItemCatMapper;
import cn.exrick.manager.pojo.TbItemCat;
import cn.exrick.manager.pojo.TbItemCatExample;
import cn.exrick.manager.service.ItemCatService;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Exrick
 * @date 2017/8/2
 */
@Slf4j
@Component
@Service(interfaceClass = ItemCatService.class)
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Override
    public TbItemCat getItemCatById(Long id) {

        TbItemCat tbItemCat = tbItemCatMapper.selectByPrimaryKey(id);
        if (tbItemCat == null) {
            throw new XmallException("通过id获取商品分类失败");
        }
        return tbItemCat;
    }

    @Override
    public ArrayList<TbItemCat> getItemCatList(long parentId, EnableStatusEnum enableStatusEnum) {

        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(enableStatusEnum)) {
            criteria.andStatusEqualTo(enableStatusEnum);
        }
        //排序
        example.setOrderByClause("sort_order");
        //条件查询
        criteria.andParentIdEqualTo(new Long(parentId));
        List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        return Lists.newArrayList(list);
    }

    @Override
    public int addItemCat(TbItemCat tbItemCat) {

        if (tbItemCat.getParentId() == 0) {
            //根节点
            tbItemCat.setSortOrder(0);
            tbItemCat.setStatus(EnableStatusEnum.ENABLED);
        } else {
            TbItemCat parent = tbItemCatMapper.selectByPrimaryKey(tbItemCat.getParentId());
            tbItemCat.setSortOrder(0);
            tbItemCat.setStatus(EnableStatusEnum.ENABLED);
            tbItemCat.setCreated(new Date());
            tbItemCat.setUpdated(new Date());
        }

        if (tbItemCatMapper.insert(tbItemCat) != 1) {
            throw new XmallException("添加商品分类失败");
        }
        return 1;
    }

    @Override
    public int updateItemCat(TbItemCat tbItemCat) {

        TbItemCat old = getItemCatById(tbItemCat.getId());
        tbItemCat.setCreated(old.getCreated());
        tbItemCat.setUpdated(new Date());

        if (tbItemCatMapper.updateByPrimaryKey(tbItemCat) != 1) {
            throw new XmallException("添加商品分类失败");
        }
        return 1;
    }

    @Override
    public void deleteItemCat(Long id) {

        deleteZTree(id);
    }

    @Override
    public void deleteZTree(Long id) {

//        //查询该节点所有子节点
//        List<ZTreeNode> node = getItemCatList(Math.toIntExact(id));
//        if (node.size() > 0) {
//            //如果有子节点，遍历子节点
//            for (int i = 0; i < node.size(); i++) {
//                deleteItemCat((long) node.get(i).getId());
//            }
//        }
//        //没有子节点直接删除
//        if (tbItemCatMapper.deleteByPrimaryKey(id) != 1) {
//            throw new XmallException("删除商品分类失败");
//        }
    }

}
