package cn.exrick.manager.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface TbUserExtMapper extends TbUserMapper {

    Set<String> getRoles(@Param("username") String username);

    Set<String> getPermissions(@Param("username") String username);

    List<String> getPermsByRoleId(@Param("id") int id);
}