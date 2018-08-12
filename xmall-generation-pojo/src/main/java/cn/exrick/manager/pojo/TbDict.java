package cn.exrick.manager.pojo;

import java.io.Serializable;
import lombok.Data;

@Data
public class TbDict implements Serializable {
    private Integer id;

    private String dict;

    private Integer type;

    private static final long serialVersionUID = 1L;
}