package @packageName@.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ListReq implements Serializable {

    /**
    * 开始的记录数
    */
    private Integer offset = 0;

    /**
    * 取的条数
    */
    private Integer limit = 20;

    /**
    * 排序规则
    */
    private String orderBy = "id desc";

}
