package cn.exrick.manager.dto.manager;

import cn.exrick.manager.pojo.Menu;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class MenuDto implements Serializable {

    private int id;

    private String title;

    private String name;

    private String component;

    private String permType;

    private String path;

    private List<MenuDto> children;

    public MenuDto() {
    }

    public MenuDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.title = menu.getTitle();
        this.component = menu.getComponent();
        this.permType = menu.getPermType();
        this.path = menu.getPath();
    }
}
