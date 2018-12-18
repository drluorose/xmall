package cn.exrick.manager.mapper;

import cn.exrick.manager.pojo.TbMemberJwtKey;
import cn.exrick.manager.pojo.TbMemberJwtKeyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbMemberJwtKeyMapper {
    long countByExample(TbMemberJwtKeyExample example);

    int deleteByExample(TbMemberJwtKeyExample example);

    int deleteByPrimaryKey(String id);

    int insert(TbMemberJwtKey record);

    int insertSelective(TbMemberJwtKey record);

    TbMemberJwtKey selectOneByExample(TbMemberJwtKeyExample example);

    List<TbMemberJwtKey> selectByExample(TbMemberJwtKeyExample example);

    TbMemberJwtKey selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TbMemberJwtKey record, @Param("example") TbMemberJwtKeyExample example);

    int updateByExample(@Param("record") TbMemberJwtKey record, @Param("example") TbMemberJwtKeyExample example);

    int updateByPrimaryKeySelective(TbMemberJwtKey record);

    int updateByPrimaryKey(TbMemberJwtKey record);

    int insertBatch(List<TbMemberJwtKey> records);
}