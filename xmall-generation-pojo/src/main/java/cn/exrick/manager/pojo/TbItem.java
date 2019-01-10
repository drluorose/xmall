package cn.exrick.manager.pojo;

import cn.exrick.common.enums.EnableStatusEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class TbItem implements Serializable {
    private Long id;

    private String sku;

    private String title;

    private String sellPoint;

    private BigDecimal price;

    private Integer num;

    private Integer limitNum;

    private String image;

    private Long cid;

    private EnableStatusEnum status;

    private Date created;

    private Date updated;

    private static final long serialVersionUID = 1L;
}