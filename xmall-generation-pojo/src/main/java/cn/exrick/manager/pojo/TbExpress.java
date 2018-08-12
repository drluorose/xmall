package cn.exrick.manager.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class TbExpress implements Serializable {
    private Integer id;

    private String expressName;

    private Integer sortOrder;

    private Date created;

    private Date updated;

    private static final long serialVersionUID = 1L;
}