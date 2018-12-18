package cn.exrick.manager.pojo;

import cn.exrick.common.enums.EnableStatusEnum;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class RoleMenu implements Serializable {
    private Integer id;

    private Integer roleId;

    private Integer menuId;

    private EnableStatusEnum valid;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}