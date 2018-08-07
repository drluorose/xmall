package cn.exrick.manager.mapper;

import java.util.List;

public interface TbOrderItemExtMapper extends TbOrderItemMapper {

    List<TbOrderItem> getWeekHot();
}