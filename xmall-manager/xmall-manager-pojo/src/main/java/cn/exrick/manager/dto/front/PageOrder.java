package cn.exrick.manager.dto.front;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Exrickx
 */
@Data
public class PageOrder implements Serializable {

    private int total;

    private List<Order> data;
}
