package cn.exrick.manager.pojo;

import lombok.Data;

@Data
public class TbRolePerm {
    private Integer id;

    private Integer roleId;

    private Integer permissionId;
}