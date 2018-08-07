package cn.exrick.manager.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbRoleExtMapper extends TbRoleMapper {

    List<String> getUsedRoles(@Param("id") int id);
}