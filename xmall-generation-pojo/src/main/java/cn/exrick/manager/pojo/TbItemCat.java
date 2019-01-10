package cn.exrick.manager.pojo;

import cn.exrick.common.enums.EnableStatusEnum;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class TbItemCat implements Serializable {
    private Long id;

    private Long parentId;

    private String name;

    private EnableStatusEnum status;

    private Integer sortOrder;

    private Boolean isParent;

    private String icon;

    private String remark;

    private Date created;

    private Date updated;

    private static final long serialVersionUID = 1L;
}