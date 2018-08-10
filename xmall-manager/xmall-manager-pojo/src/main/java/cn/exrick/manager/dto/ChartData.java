package cn.exrick.manager.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Exrickx
 */
@Data
public class ChartData implements Serializable {

    private List<Object> xDatas;

    private List<Object> yDatas;

    private Object countAll;

}
