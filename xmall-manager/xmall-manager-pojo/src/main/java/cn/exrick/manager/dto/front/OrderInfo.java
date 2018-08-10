package cn.exrick.manager.dto.front;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Exrickx
 */
@Data
public class OrderInfo implements Serializable {

    private String userId;

    private Long addressId;

    private String tel;

    private String userName;

    private String streetName;

    private BigDecimal orderTotal;
}
