package cn.exrick.manager.pojo;

import java.io.Serializable;
import lombok.Data;

@Data
public class TbShiroFilter implements Serializable {
    private Integer id;

    private String name;

    private String perms;

    private Integer sortOrder;

    private static final long serialVersionUID = 1L;
}