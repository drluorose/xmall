package cn.exrick.manager.mapper;

import cn.exrick.manager.pojo.TransactionPaypalShippingAddress;
import cn.exrick.manager.pojo.TransactionPaypalShippingAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TransactionPaypalShippingAddressMapper {
    long countByExample(TransactionPaypalShippingAddressExample example);

    int deleteByExample(TransactionPaypalShippingAddressExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TransactionPaypalShippingAddress record);

    int insertSelective(TransactionPaypalShippingAddress record);

    TransactionPaypalShippingAddress selectOneByExample(TransactionPaypalShippingAddressExample example);

    List<TransactionPaypalShippingAddress> selectByExample(TransactionPaypalShippingAddressExample example);

    TransactionPaypalShippingAddress selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TransactionPaypalShippingAddress record, @Param("example") TransactionPaypalShippingAddressExample example);

    int updateByExample(@Param("record") TransactionPaypalShippingAddress record, @Param("example") TransactionPaypalShippingAddressExample example);

    int updateByPrimaryKeySelective(TransactionPaypalShippingAddress record);

    int updateByPrimaryKey(TransactionPaypalShippingAddress record);

    int insertBatch(List<TransactionPaypalShippingAddress> records);
}