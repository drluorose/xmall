package cn.exrick.manager.pojo;

import java.io.Serializable;
import lombok.Data;

@Data
public class TbBase implements Serializable {
    private Integer id;

    private String webName;

    private String keyWord;

    private String description;

    private String sourcePath;

    private String uploadPath;

    private String copyright;

    private String countCode;

    private Integer hasLogNotice;

    private String logNotice;

    private Integer hasAllNotice;

    private String allNotice;

    private String notice;

    private String updateLog;

    private String frontUrl;

    private static final long serialVersionUID = 1L;
}