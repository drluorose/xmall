package cn.exrick.manager.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Exrickx
 */
@Data
public class OrderChartData implements Serializable {

    private Date time;

    private BigDecimal money;
}
