package cn.exrick.manager.mapper.ext;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbRoleExtMapper {

    List<String> getUsedRoles(@Param("id") int id);
}