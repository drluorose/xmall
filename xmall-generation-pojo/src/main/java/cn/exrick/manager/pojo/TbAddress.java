package cn.exrick.manager.pojo;

import cn.exrick.common.enums.ValidStatusEnum;
import java.io.Serializable;
import lombok.Data;

@Data
public class TbAddress implements Serializable {
    private String id;

    private String mid;

    private String userName;

    private String tel;

    private String address;

    private Boolean isDefault;

    private ValidStatusEnum valid;

    private static final long serialVersionUID = 1L;
}