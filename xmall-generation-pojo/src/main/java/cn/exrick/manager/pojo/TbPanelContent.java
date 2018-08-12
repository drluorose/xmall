package cn.exrick.manager.pojo;

import java.util.Date;
import lombok.Data;

@Data
public class TbPanelContent {
    private Integer id;

    private Integer panelId;

    private Integer type;

    private Long productId;

    private Integer sortOrder;

    private String fullUrl;

    private String picUrl;

    private String picUrl2;

    private String picUrl3;

    private Date created;

    private Date updated;
}