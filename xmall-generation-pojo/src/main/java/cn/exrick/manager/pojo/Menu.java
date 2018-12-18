package cn.exrick.manager.pojo;

import cn.exrick.common.enums.EnableStatusEnum;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class Menu implements Serializable {
    private Integer id;

    private Integer pid;

    private String title;

    private String name;

    private String component;

    private String path;

    private String icon;

    private String permType;

    private EnableStatusEnum valid;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}