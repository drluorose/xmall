package cn.exrick.common.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Exrick
 * @date 2017/8/2
 */
@Data
public class ZTreeNode implements Serializable {

    private int id;

    private int pId;

    private String name;

    private Boolean isParent;

    private Boolean open;

    private String icon;

    private int status;

    private int sortOrder;

    private String remark;

    /**
     * 板块限制商品数量
     */
    private int limitNum;
}
