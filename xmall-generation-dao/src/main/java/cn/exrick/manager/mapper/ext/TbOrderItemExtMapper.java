package cn.exrick.manager.mapper.ext;

import cn.exrick.manager.mapper.TbOrderItemMapper;
import cn.exrick.manager.pojo.TbOrderItem;

import java.util.List;

public interface TbOrderItemExtMapper  extends TbOrderItemMapper {

    List<TbOrderItem> getWeekHot();
}