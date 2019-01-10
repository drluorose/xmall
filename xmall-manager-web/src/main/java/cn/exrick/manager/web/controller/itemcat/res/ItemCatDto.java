package cn.exrick.manager.web.controller.itemcat.res;

import cn.exrick.common.enums.EnableStatusEnum;
import cn.exrick.manager.pojo.TbItemCat;
import lombok.Data;

import java.util.List;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class ItemCatDto {

    private long id;

    private String name;

    private long pid;

    EnableStatusEnum status;

    private List<ItemCatDto> catDtos;

    public ItemCatDto() {

    }

    public ItemCatDto(TbItemCat tbItemCat) {
        this.id = tbItemCat.getId();
        this.name = tbItemCat.getName();
        this.pid = tbItemCat.getParentId();
        this.status = tbItemCat.getStatus();
    }
}
