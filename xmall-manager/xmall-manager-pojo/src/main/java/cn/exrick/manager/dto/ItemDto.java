package cn.exrick.manager.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Exrickx
 */
@Data
public class ItemDto implements Serializable {

    private Long id;

    private String title;

    private String sellPoint;

    private BigDecimal price;

    private Integer num;

    private Integer limitNum;

    private String detail;

    private String image;

    private Long cid;

    private String cname;

    private Byte status;

    private Date created;

    private Date updated;

}
