package cn.exrick.manager.pojo;

import lombok.Data;

@Data
public class TbCountry {
    private Integer id;

    private Integer continentId;

    private String name;

    private String lowerName;

    private String countryCode;

    private String fullName;

    private String cname;

    private String fullCname;

    private String remark;
}