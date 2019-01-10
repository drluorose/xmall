package cn.exrick.common.query;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class PageQuery implements Serializable {

    private Integer pageNo;

    private Integer pageSize;
}
