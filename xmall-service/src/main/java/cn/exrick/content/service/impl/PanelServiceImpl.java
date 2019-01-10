package cn.exrick.content.service.impl;

import cn.exrick.common.enums.IndexTypeEnum;
import cn.exrick.common.enums.ValidStatusEnum;
import cn.exrick.common.exception.XmallException;
import cn.exrick.common.jedis.JedisClient;
import cn.exrick.content.service.PanelService;
import cn.exrick.manager.mapper.TbPanelMapper;
import cn.exrick.manager.pojo.TbPanel;
import cn.exrick.manager.pojo.TbPanelExample;
import cn.exrick.manager.pojo.TbPanelExample.Criteria;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Exrickx
 */
@Slf4j
@Component
@Service
public class PanelServiceImpl implements PanelService {

    @Autowired
    private TbPanelMapper tbPanelMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${PRODUCT_HOME}")
    private String PRODUCT_HOME;

    @Override
    public TbPanel getTbPanelById(int id) {

        TbPanel tbPanel = tbPanelMapper.selectByPrimaryKey(id);
        if (tbPanel == null) {
            throw new XmallException("通过id获得板块失败");
        }
        return tbPanel;
    }

    @Override
    public List<TbPanel> getPanelList(IndexTypeEnum indexTypeEnum) {

        TbPanelExample example = new TbPanelExample();
        Criteria criteria = example.createCriteria().andValidEqualTo(ValidStatusEnum.VALID);
        if (indexTypeEnum == IndexTypeEnum.BANNER) {
            criteria.andTypeEqualTo(0);
        } else if (indexTypeEnum == IndexTypeEnum.OTHER) {
            criteria.andTypeNotEqualTo(0);
        }
        example.setOrderByClause("sort_order");
        List<TbPanel> panelList = tbPanelMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(panelList)) {
            panelList = Lists.newArrayList();
        }
        return panelList;
    }

    @Override
    public int addPanel(TbPanel tbPanel) {

        if (tbPanel.getType() == 0) {
            TbPanelExample example = new TbPanelExample();
            TbPanelExample.Criteria criteria = example.createCriteria();
            criteria.andTypeEqualTo(0);
            List<TbPanel> list = tbPanelMapper.selectByExample(example);
            if (list != null && list.size() > 0) {
                throw new XmallException("已有轮播图板块,轮播图仅能添加1个!");
            }
        }

        tbPanel.setCreated(new Date());
        tbPanel.setUpdated(new Date());

        if (tbPanelMapper.insert(tbPanel) != 1) {
            throw new XmallException("添加板块失败");
        }
        //同步缓存
        deleteHomeRedis();
        return 1;
    }

    @Override
    public int updatePanel(TbPanel tbPanel) {

        TbPanel old = getTbPanelById(tbPanel.getId());
        if (Objects.isNull(old)) {
            return 0;
        }
        tbPanel.setUpdated(new Date());

        if (tbPanelMapper.updateByPrimaryKeySelective(tbPanel) != 1) {
            throw new XmallException("更新板块失败");
        }
        //同步缓存
        deleteHomeRedis();
        return 1;
    }

    @Override
    public int deletePanel(int id) {
        TbPanel tbPanel = new TbPanel();
        tbPanel.setId(id);
        tbPanel.setValid(ValidStatusEnum.INVALID);
        tbPanel.setUpdated(new Date());

        int result = tbPanelMapper.updateByPrimaryKeySelective(tbPanel);
        if (result != 1) {
            throw new XmallException("删除内容分类失败");
        }
        //同步缓存
        deleteHomeRedis();
        return 1;
    }

    /**
     * 同步首页缓存
     */
    public void deleteHomeRedis() {
        try {
            jedisClient.del(PRODUCT_HOME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
