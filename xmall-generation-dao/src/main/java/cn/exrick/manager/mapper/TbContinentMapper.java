package cn.exrick.manager.mapper;

import cn.exrick.manager.pojo.TbContinent;
import cn.exrick.manager.pojo.TbContinentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbContinentMapper {
    long countByExample(TbContinentExample example);

    int deleteByExample(TbContinentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbContinent record);

    int insertSelective(TbContinent record);

    TbContinent selectOneByExample(TbContinentExample example);

    List<TbContinent> selectByExample(TbContinentExample example);

    TbContinent selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbContinent record, @Param("example") TbContinentExample example);

    int updateByExample(@Param("record") TbContinent record, @Param("example") TbContinentExample example);

    int updateByPrimaryKeySelective(TbContinent record);

    int updateByPrimaryKey(TbContinent record);

    int insertBatch(List<TbContinent> records);
}