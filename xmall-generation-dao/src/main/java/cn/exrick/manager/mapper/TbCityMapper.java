package cn.exrick.manager.mapper;

import cn.exrick.manager.pojo.TbCity;
import cn.exrick.manager.pojo.TbCityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbCityMapper {
    long countByExample(TbCityExample example);

    int deleteByExample(TbCityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbCity record);

    int insertSelective(TbCity record);

    List<TbCity> selectByExample(TbCityExample example);

    TbCity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbCity record, @Param("example") TbCityExample example);

    int updateByExample(@Param("record") TbCity record, @Param("example") TbCityExample example);

    int updateByPrimaryKeySelective(TbCity record);

    int updateByPrimaryKey(TbCity record);
}