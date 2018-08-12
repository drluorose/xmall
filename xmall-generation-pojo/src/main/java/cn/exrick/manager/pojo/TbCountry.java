package cn.exrick.manager.pojo;

import java.io.Serializable;
import lombok.Data;

@Data
public class TbCountry implements Serializable {
    private Integer id;

    private Integer continentId;

    private String name;

    private String lowerName;

    private String countryCode;

    private String fullName;

    private String cname;

    private String fullCname;

    private String remark;

    private static final long serialVersionUID = 1L;
}