package cn.exrick.manager.dto.front;

import cn.exrick.manager.pojo.TbCountry;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class CountryDto implements Serializable, Comparable<CountryDto> {

    private int id;

    private String name;

    public CountryDto(TbCountry tbCountry) {
        this.id = tbCountry.getId();
        this.name = tbCountry.getName();
    }

    @Override
    public int compareTo(CountryDto o) {
        return name.compareTo(o.name);
    }
}
