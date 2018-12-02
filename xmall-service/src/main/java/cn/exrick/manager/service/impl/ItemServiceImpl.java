package cn.exrick.manager.service.impl;

import cn.exrick.common.exception.XmallException;
import cn.exrick.common.jedis.JedisClient;
import cn.exrick.common.pojo.DataTablesResult;
import cn.exrick.common.utils.IDUtil;
import cn.exrick.manager.dto.DtoUtil;
import cn.exrick.manager.dto.ItemDto;
import cn.exrick.manager.dto.front.SearchItem;
import cn.exrick.manager.mapper.TbItemCatMapper;
import cn.exrick.manager.mapper.TbItemDescMapper;
import cn.exrick.manager.mapper.TbItemMapper;
import cn.exrick.manager.mapper.ext.TbItemExtMapper;
import cn.exrick.manager.pojo.TbItem;
import cn.exrick.manager.pojo.TbItemCat;
import cn.exrick.manager.pojo.TbItemDesc;
import cn.exrick.manager.pojo.TbItemExample;
import cn.exrick.manager.service.ItemService;
import cn.exrick.search.biz.ElasticSearchBiz;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Exrick on 2017/7/29.
 */
@Slf4j
@Component
@Service(interfaceClass = ItemService.class)
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemExtMapper tbItemExtMapper;

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${PRODUCT_ITEM}")
    private String PRODUCT_ITEM;

    @Autowired
    private ElasticSearchBiz elasticSearchBiz;

    @Override
    public ItemDto getItemById(Long id) {
        ItemDto itemDto;
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
        itemDto = DtoUtil.TbItem2ItemDto(tbItem);
        TbItemCat tbItemCat = tbItemCatMapper.selectByPrimaryKey(itemDto.getCid());
        itemDto.setCname(tbItemCat.getName());
        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(id);
        itemDto.setDetail(tbItemDesc.getItemDesc());
        return itemDto;
    }

    @Override
    public TbItem getNormalItemById(Long id) {
        return tbItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public DataTablesResult getItemList(int draw, int start, int length, int cid, String search,
                                        String orderCol, String orderDir) {
        DataTablesResult result = new DataTablesResult();
        //分页执行查询返回结果
        PageHelper.startPage(start / length + 1, length);
        List<TbItem> list = tbItemExtMapper.selectItemByCondition(cid, "%" + search + "%", orderCol, orderDir);
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        result.setRecordsFiltered((int) pageInfo.getTotal());
        result.setRecordsTotal(getAllItemCount().getRecordsTotal());
        result.setDraw(draw);
        result.setData(list);
        return result;
    }

    @Override
    public DataTablesResult getItemSearchList(int draw, int start, int length, int cid, String search,
                                              String minDate, String maxDate, String orderCol, String orderDir) {

        DataTablesResult result = new DataTablesResult();
        //分页执行查询返回结果
        PageHelper.startPage(start / length + 1, length);
        List<TbItem> list = tbItemExtMapper.selectItemByMultiCondition(cid, "%" + search + "%", minDate, maxDate, orderCol, orderDir);
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        result.setRecordsFiltered((int) pageInfo.getTotal());
        result.setRecordsTotal(getAllItemCount().getRecordsTotal());

        result.setDraw(draw);
        result.setData(list);
        return result;
    }

    @Override
    public DataTablesResult getAllItemCount() {
        TbItemExample example = new TbItemExample();
        Long count = tbItemMapper.countByExample(example);
        DataTablesResult result = new DataTablesResult();
        result.setRecordsTotal(Math.toIntExact(count));
        return result;
    }

    @Override
    public TbItem alertItemState(Long id, Integer state) {
        TbItem tbMember = getNormalItemById(id);
        tbMember.setStatus(state);
        tbMember.setUpdated(new Date());
        if (tbItemMapper.updateByPrimaryKey(tbMember) != 1) {
            throw new XmallException("修改商品状态失败");
        }
        return getNormalItemById(id);
    }

    @Override
    public int deleteItem(Long id) {
        if (tbItemMapper.deleteByPrimaryKey(id) != 1) {
            throw new XmallException("删除商品失败");
        }
        if (tbItemDescMapper.deleteByPrimaryKey(id) != 1) {
            throw new XmallException("删除商品详情失败");
        }
        deleteIndex(id);
        return 0;
    }

    @Override
    public TbItem addItem(ItemDto itemDto) throws IOException {
        long id = IDUtil.getRandomId();
        TbItem tbItem = DtoUtil.ItemDto2TbItem(itemDto);
        tbItem.setId(id);
        tbItem.setStatus(1);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        if (tbItem.getImage().isEmpty()) {
            tbItem.setImage("http://ow2h3ee9w.bkt.clouddn.com/nopic.jpg");
        }
        if (tbItemMapper.insert(tbItem) != 1) {
            throw new XmallException("添加商品失败");
        }

        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(id);
        tbItemDesc.setItemDesc(itemDto.getDetail());
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());

        if (tbItemDescMapper.insert(tbItemDesc) != 1) {
            throw new XmallException("添加商品详情失败");
        }
        updateIndex(id);
        return getNormalItemById(id);
    }

    @Override
    public TbItem updateItem(Long id, ItemDto itemDto) {

        TbItem oldTbItem = getNormalItemById(id);

        TbItem tbItem = DtoUtil.ItemDto2TbItem(itemDto);

        if (tbItem.getImage().isEmpty()) {
            tbItem.setImage(oldTbItem.getImage());
        }
        tbItem.setId(id);
        tbItem.setStatus(oldTbItem.getStatus());
        tbItem.setCreated(oldTbItem.getCreated());
        tbItem.setUpdated(new Date());
        if (tbItemMapper.updateByPrimaryKey(tbItem) != 1) {
            throw new XmallException("更新商品失败");
        }

        TbItemDesc tbItemDesc = new TbItemDesc();

        tbItemDesc.setItemId(id);
        tbItemDesc.setItemDesc(itemDto.getDetail());
        tbItemDesc.setUpdated(new Date());
        tbItemDesc.setCreated(oldTbItem.getCreated());

        if (tbItemDescMapper.updateByPrimaryKey(tbItemDesc) != 1) {
            throw new XmallException("更新商品详情失败");
        }
        //同步缓存
        deleteProductDetRedis(id);
        deleteIndex(id);
        return getNormalItemById(id);
    }

    /**
     * 同步商品详情缓存
     *
     * @param id
     */
    public void deleteProductDetRedis(Long id) {
        try {
            jedisClient.del(PRODUCT_ITEM + ":" + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateIndex(Long id) throws IOException {
        SearchItem searchItem = tbItemExtMapper.getItemById(id);
        if (Objects.nonNull(searchItem)) {
            elasticSearchBiz.updateSearchIndexDoc(searchItem);
        }
    }

    private void deleteIndex(Long id) {
        SearchItem searchItem = tbItemExtMapper.getItemById(id);
        if (Objects.nonNull(searchItem)) {
            elasticSearchBiz.removeSearchIndexDoc(searchItem);
        }
    }
}
