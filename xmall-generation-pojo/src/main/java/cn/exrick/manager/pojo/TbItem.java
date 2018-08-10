package cn.exrick.manager.pojo;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class TbItem {
    private Long id;

    private String title;

    private String sellPoint;

    private BigDecimal price;

    private Integer num;

    private Integer limitNum;

    private String image;

    private Long cid;

    private Integer status;

    private Date created;

    private Date updated;
}