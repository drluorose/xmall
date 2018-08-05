package cn.exrick.common.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author Exrick
 * @date 2017/8/1
 */
@Data
@Getter
@Setter
public class DataTablesResult implements Serializable {

    private Boolean success;

    private int draw;

    private int recordsTotal;

    private int recordsFiltered;

    private String error;

    private List<?> data;
}
