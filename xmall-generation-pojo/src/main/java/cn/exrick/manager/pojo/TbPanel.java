package cn.exrick.manager.pojo;

import java.util.Date;
import lombok.Data;

@Data
public class TbPanel {
    private Integer id;

    private String name;

    private Integer type;

    private Integer sortOrder;

    private Integer position;

    private Integer limitNum;

    private Integer status;

    private String remark;

    private Date created;

    private Date updated;
}