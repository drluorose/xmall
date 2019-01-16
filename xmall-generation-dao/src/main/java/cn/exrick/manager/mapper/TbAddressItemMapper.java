package cn.exrick.manager.mapper;

import cn.exrick.manager.pojo.TbAddressItem;
import cn.exrick.manager.pojo.TbAddressItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbAddressItemMapper {
    long countByExample(TbAddressItemExample example);

    int deleteByExample(TbAddressItemExample example);

    int deleteByPrimaryKey(String id);

    int insert(TbAddressItem record);

    int insertSelective(TbAddressItem record);

    TbAddressItem selectOneByExample(TbAddressItemExample example);

    List<TbAddressItem> selectByExample(TbAddressItemExample example);

    TbAddressItem selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TbAddressItem record, @Param("example") TbAddressItemExample example);

    int updateByExample(@Param("record") TbAddressItem record, @Param("example") TbAddressItemExample example);

    int updateByPrimaryKeySelective(TbAddressItem record);

    int updateByPrimaryKey(TbAddressItem record);

    int insertBatch(List<TbAddressItem> records);
}