package cn.exrick.manager.pojo;

import cn.exrick.common.enums.EnableStatusEnum;
import cn.exrick.common.enums.ValidStatusEnum;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class TbPanel implements Serializable {
    private Integer id;

    private String name;

    private Integer type;

    private Integer sortOrder;

    private Integer position;

    private Integer limitNum;

    private EnableStatusEnum status;

    private String remark;

    private ValidStatusEnum valid;

    private Date created;

    private Date updated;

    private static final long serialVersionUID = 1L;
}