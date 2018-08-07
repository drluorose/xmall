package cn.exrick.manager.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbLogExtMapper extends TbLogMapper {

    List<TbLog> selectByMulti(@Param("search") String search, @Param("orderCol") String orderCol, @Param("orderDir") String orderDir);
}