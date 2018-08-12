package cn.exrick.manager.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class TbItemDesc implements Serializable {
    private Long itemId;

    private Date created;

    private Date updated;

    private String itemDesc;

    private static final long serialVersionUID = 1L;
}