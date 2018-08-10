package cn.exrick.manager.mapper;

import cn.exrick.manager.pojo.TbCountry;
import cn.exrick.manager.pojo.TbCountryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbCountryMapper {
    long countByExample(TbCountryExample example);

    int deleteByExample(TbCountryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbCountry record);

    int insertSelective(TbCountry record);

    List<TbCountry> selectByExampleWithBLOBs(TbCountryExample example);

    List<TbCountry> selectByExample(TbCountryExample example);

    TbCountry selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbCountry record, @Param("example") TbCountryExample example);

    int updateByExampleWithBLOBs(@Param("record") TbCountry record, @Param("example") TbCountryExample example);

    int updateByExample(@Param("record") TbCountry record, @Param("example") TbCountryExample example);

    int updateByPrimaryKeySelective(TbCountry record);

    int updateByPrimaryKeyWithBLOBs(TbCountry record);

    int updateByPrimaryKey(TbCountry record);
}