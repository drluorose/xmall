package cn.exrick.manager.pojo;

import java.io.Serializable;
import lombok.Data;

@Data
public class TbContinent implements Serializable {
    private Integer id;

    private String cnName;

    private String enName;

    private static final long serialVersionUID = 1L;
}