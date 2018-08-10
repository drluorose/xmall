package cn.exrick.manager.pojo;

import java.util.Date;
import lombok.Data;

@Data
public class TbItemDesc {
    private Long itemId;

    private Date created;

    private Date updated;

    private String itemDesc;
}