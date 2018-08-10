package cn.exrick.manager.mapper;

import cn.exrick.manager.pojo.TbOrderItem;

import java.util.List;

public interface TbOrderItemExtMapper extends TbOrderItemMapper {

    List<TbOrderItem> getWeekHot();
}