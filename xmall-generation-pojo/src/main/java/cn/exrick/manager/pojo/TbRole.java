package cn.exrick.manager.pojo;

import java.io.Serializable;
import lombok.Data;

@Data
public class TbRole implements Serializable {
    private Integer id;

    private String name;

    private String description;

    private static final long serialVersionUID = 1L;
}