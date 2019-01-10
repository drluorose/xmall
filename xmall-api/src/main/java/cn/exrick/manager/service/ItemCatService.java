package cn.exrick.manager.service;

import cn.exrick.common.enums.EnableStatusEnum;
import cn.exrick.manager.pojo.TbItemCat;

import java.util.ArrayList;

/**
 * @author Exrick
 * @date 2017/8/2
 */
public interface ItemCatService {

    /**
     * 通过id获取
     *
     * @param id
     *
     * @return
     */
    TbItemCat getItemCatById(Long id);

    /**
     * 获得分类树
     *
     * @param parentId
     *
     * @return
     */
    ArrayList<TbItemCat> getItemCatList(long parentId, EnableStatusEnum enableStatusEnum);

    /**
     * 添加分类
     *
     * @param tbItemCat
     *
     * @return
     */
    int addItemCat(TbItemCat tbItemCat);

    /**
     * 编辑分类
     *
     * @param tbItemCat
     *
     * @return
     */
    int updateItemCat(TbItemCat tbItemCat);

    /**
     * 删除单个分类
     *
     * @param id
     */
    void deleteItemCat(Long id);

    /**
     * 递归删除
     *
     * @param id
     */
    void deleteZTree(Long id);
}
