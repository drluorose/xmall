package cn.exrick.manager.mapper;

import cn.exrick.manager.pojo.TransactionPaypalItem;
import cn.exrick.manager.pojo.TransactionPaypalItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TransactionPaypalItemMapper {
    long countByExample(TransactionPaypalItemExample example);

    int deleteByExample(TransactionPaypalItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TransactionPaypalItem record);

    int insertSelective(TransactionPaypalItem record);

    TransactionPaypalItem selectOneByExample(TransactionPaypalItemExample example);

    List<TransactionPaypalItem> selectByExample(TransactionPaypalItemExample example);

    TransactionPaypalItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TransactionPaypalItem record, @Param("example") TransactionPaypalItemExample example);

    int updateByExample(@Param("record") TransactionPaypalItem record, @Param("example") TransactionPaypalItemExample example);

    int updateByPrimaryKeySelective(TransactionPaypalItem record);

    int updateByPrimaryKey(TransactionPaypalItem record);

    int insertBatch(List<TransactionPaypalItem> records);
}