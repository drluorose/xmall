package cn.exrick.manager.pojo;

import lombok.Data;

@Data
public class TbCity {
    private Integer id;

    private Integer countryId;

    private String state;

    private String name;

    private String lowerName;

    private String cnState;

    private String cnName;

    private String cityCode;

    private String stateCode;
}