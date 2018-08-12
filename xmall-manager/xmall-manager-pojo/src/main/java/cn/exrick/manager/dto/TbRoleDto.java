package cn.exrick.manager.dto;

import cn.exrick.manager.pojo.TbRole;
import lombok.Data;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class TbRoleDto extends TbRole {


    private Integer[] roles;
}
