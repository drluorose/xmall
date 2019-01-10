package cn.exrick.common.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class PageResult<T> implements Serializable {

    private int pageNo;

    private int pageSize;

    private long total;

    private List<T> data;
}
