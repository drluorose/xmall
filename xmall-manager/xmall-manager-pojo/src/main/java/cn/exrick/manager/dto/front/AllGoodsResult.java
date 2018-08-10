package cn.exrick.manager.dto.front;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Exrickx
 */
@Data
public class AllGoodsResult implements Serializable {

    private int total;

    private List<?> data;
}
