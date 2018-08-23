package cn.exrick.manager.pojo;

import cn.exrick.common.enums.EnableStatusEnum;
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

    private EnableStatusEnum status;

    private String remark;

    private static final long serialVersionUID = 1L;
}