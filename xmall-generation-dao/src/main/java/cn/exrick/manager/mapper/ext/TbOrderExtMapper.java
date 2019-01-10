package cn.exrick.manager.mapper.ext;

import cn.exrick.common.query.OrderSearchParam;
import cn.exrick.manager.dto.OrderChartData;
import cn.exrick.manager.pojo.TbOrder;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author dongjiejie
 */
public interface TbOrderExtMapper {

    List<TbOrder> selectByMulti(OrderSearchParam orderSearchParam);

    List<OrderChartData> selectOrderChart(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<OrderChartData> selectOrderChartByYear(@Param("year") int year);
}