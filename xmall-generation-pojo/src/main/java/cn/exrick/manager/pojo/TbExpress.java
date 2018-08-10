package cn.exrick.manager.pojo;

import java.util.Date;
import lombok.Data;

@Data
public class TbExpress {
    private Integer id;

    private String expressName;

    private Integer sortOrder;

    private Date created;

    private Date updated;
}