package cn.exrick.manager.mapper;

import cn.exrick.manager.pojo.TransactionPaypal;
import cn.exrick.manager.pojo.TransactionPaypalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TransactionPaypalMapper {
    long countByExample(TransactionPaypalExample example);

    int deleteByExample(TransactionPaypalExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TransactionPaypal record);

    int insertSelective(TransactionPaypal record);

    TransactionPaypal selectOneByExample(TransactionPaypalExample example);

    List<TransactionPaypal> selectByExample(TransactionPaypalExample example);

    TransactionPaypal selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TransactionPaypal record, @Param("example") TransactionPaypalExample example);

    int updateByExample(@Param("record") TransactionPaypal record, @Param("example") TransactionPaypalExample example);

    int updateByPrimaryKeySelective(TransactionPaypal record);

    int updateByPrimaryKey(TransactionPaypal record);

    int insertBatch(List<TransactionPaypal> records);
}