package cn.exrick.manager.pojo;

import lombok.Data;

@Data
public class TbShiroFilter {
    private Integer id;

    private String name;

    private String perms;

    private Integer sortOrder;
}