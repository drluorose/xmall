package cn.exrick.manager.pojo;

import java.io.Serializable;
import lombok.Data;

@Data
public class TbPermission implements Serializable {
    private Integer id;

    private String name;

    private String permission;

    private static final long serialVersionUID = 1L;
}