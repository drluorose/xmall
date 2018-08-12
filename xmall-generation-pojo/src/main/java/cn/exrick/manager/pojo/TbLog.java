package cn.exrick.manager.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class TbLog implements Serializable {
    private Integer id;

    private String name;

    private Integer type;

    private String url;

    private String requestType;

    private String requestParam;

    private String user;

    private String ip;

    private String ipInfo;

    private Integer time;

    private Date createDate;

    private static final long serialVersionUID = 1L;
}