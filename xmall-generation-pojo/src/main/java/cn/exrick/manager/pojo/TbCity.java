package cn.exrick.manager.pojo;

import java.io.Serializable;
import lombok.Data;

@Data
public class TbCity implements Serializable {
    private Integer id;

    private Integer countryId;

    private String state;

    private String name;

    private String lowerName;

    private String cnState;

    private String cnName;

    private String cityCode;

    private String stateCode;

    private static final long serialVersionUID = 1L;
}