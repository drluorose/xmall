package cn.exrick.manager.pojo;

import java.io.Serializable;
import lombok.Data;

@Data
public class TbRolePerm implements Serializable {
    private Integer id;

    private Integer roleId;

    private Integer permissionId;

    private static final long serialVersionUID = 1L;
}