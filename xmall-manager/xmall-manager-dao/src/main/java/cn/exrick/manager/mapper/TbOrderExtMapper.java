package cn.exrick.manager.mapper;

import cn.exrick.manager.dto.OrderChartData;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * @author dongjiejie
 */
public interface TbOrderExtMapper extends TbOrderMapper {

    List<TbOrder> selectByMulti(@Param("search") String search, @Param("orderCol") String orderCol, @Param("orderDir") String orderDir);

    List<OrderChartData> selectOrderChart(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<OrderChartData> selectOrderChartByYear(@Param("year") int year);
}