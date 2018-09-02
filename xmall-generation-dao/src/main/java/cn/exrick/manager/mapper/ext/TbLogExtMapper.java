package cn.exrick.manager.mapper.ext;

import cn.exrick.manager.pojo.TbLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbLogExtMapper {

    List<TbLog> selectByMulti(@Param("search") String search, @Param("orderCol") String orderCol, @Param("orderDir") String orderDir);
}