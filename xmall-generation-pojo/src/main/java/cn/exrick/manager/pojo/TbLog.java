package cn.exrick.manager.pojo;

import java.util.Date;
import lombok.Data;

@Data
public class TbLog {
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
}