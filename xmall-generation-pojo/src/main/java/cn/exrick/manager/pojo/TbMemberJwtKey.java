package cn.exrick.manager.pojo;

import cn.exrick.common.enums.EnableStatusEnum;
import java.io.Serializable;
import lombok.Data;

@Data
public class TbMemberJwtKey implements Serializable {
    private String id;

    private String mid;

    private String jwtAlgorithmName;

    private String privateKey;

    private String publicKey;

    private EnableStatusEnum status;

    private static final long serialVersionUID = 1L;
}