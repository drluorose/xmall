package cn.exrick.manager.pojo;

import cn.exrick.common.enums.DoStatusEnum;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class MemberVerify implements Serializable {
    private String id;

    private String mid;

    private String initVector;

    private String content;

    private String secret;

    private DoStatusEnum status;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}