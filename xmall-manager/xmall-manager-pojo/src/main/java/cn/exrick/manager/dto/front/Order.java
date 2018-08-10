package cn.exrick.manager.dto.front;

import cn.exrick.manager.pojo.TbAddress;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class Order implements Serializable {

    private Long orderId;

    private BigDecimal orderTotal;

    private TbAddress addressInfo;

    private List<CartProduct> goodsList;

    private String orderStatus;

    private String createDate;

    private String closeDate;

    private String finishDate;

    private String payDate;
}
