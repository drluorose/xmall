package cn.exrick.content.service;

import cn.exrick.common.enums.IndexTypeEnum;
import cn.exrick.manager.pojo.TbPanel;

import java.util.List;

/**
 * @author Exrickx
 */
public interface PanelService {

    /**
     * 通过id获取板块
     * @param id
     * @return
     */
    TbPanel getTbPanelById(int id);

    /**
     * 获取板块类目
     * @return
     */
    List<TbPanel> getPanelList(IndexTypeEnum indexTypeEnum);

    /**
     * 添加板块
     * @param tbPanel
     * @return
     */
    int addPanel(TbPanel tbPanel);

    /**
     * 更新板块
     * @param tbPanel
     * @return
     */
    int updatePanel(TbPanel tbPanel);

    /**
     * 删除板块
     * @param id
     * @return
     */
    int deletePanel(int id);
}
