package cn.exrick.manager.pojo;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class TbThanks {
    private Integer id;

    private String nickName;

    private String username;

    private BigDecimal money;

    private String info;

    private String email;

    private Integer state;

    private String payType;

    private String orderId;

    private Date date;
}