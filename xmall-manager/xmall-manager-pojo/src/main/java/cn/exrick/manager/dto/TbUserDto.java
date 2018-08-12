package cn.exrick.manager.dto;

import cn.exrick.manager.pojo.TbUser;
import lombok.Data;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class TbUserDto extends TbUser {

    private String roleNames;

    public TbUserDto() {
    }

    public TbUserDto(TbUser tbUser) {
        this.setId(tbUser.getId());
        this.setAddress(tbUser.getAddress());
        this.setCreated(tbUser.getCreated());
        this.setDescription(tbUser.getDescription());
        this.setEmail(tbUser.getEmail());
        this.setFile(tbUser.getFile());
        this.setPassword(tbUser.getPassword());
        this.setPhone(tbUser.getPhone());
        this.setRoleId(tbUser.getRoleId());
        this.setSex(tbUser.getSex());
        this.setState(tbUser.getState());
        this.setUpdated(tbUser.getUpdated());
        this.setUsername(tbUser.getUsername());
    }
}
