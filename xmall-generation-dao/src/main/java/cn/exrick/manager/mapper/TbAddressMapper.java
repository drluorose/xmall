package cn.exrick.manager.mapper;

import cn.exrick.manager.pojo.TbAddress;
import cn.exrick.manager.pojo.TbAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbAddressMapper {
    long countByExample(TbAddressExample example);

    int deleteByExample(TbAddressExample example);

    int deleteByPrimaryKey(String id);

    int insert(TbAddress record);

    int insertSelective(TbAddress record);

    TbAddress selectOneByExample(TbAddressExample example);

    List<TbAddress> selectByExample(TbAddressExample example);

    TbAddress selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TbAddress record, @Param("example") TbAddressExample example);

    int updateByExample(@Param("record") TbAddress record, @Param("example") TbAddressExample example);

    int updateByPrimaryKeySelective(TbAddress record);

    int updateByPrimaryKey(TbAddress record);

    int insertBatch(List<TbAddress> records);
}