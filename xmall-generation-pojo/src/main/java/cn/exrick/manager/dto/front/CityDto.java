package cn.exrick.manager.dto.front;

import cn.exrick.manager.pojo.TbCity;
import com.google.common.base.MoreObjects;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class CityDto implements Serializable {

    private int id;

    private String state;

    private String city;

    private String stateOfCity;

    public CityDto(TbCity tbCity) {
        this.id = tbCity.getId();
        this.state = tbCity.getState();
        this.city = tbCity.getName();
        if (Objects.isNull(this.state)) {
            this.stateOfCity = this.city;
        } else {
            this.stateOfCity = this.state + "  " + this.city;
        }
    }
}
