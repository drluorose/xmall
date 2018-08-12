package cn.exrick.manager.dto.front;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Exrickx
 */
@Data
public class Cart implements Serializable {

    private Long userId;

    private Long productId;

    private String checked;

    private int productNum;
}
