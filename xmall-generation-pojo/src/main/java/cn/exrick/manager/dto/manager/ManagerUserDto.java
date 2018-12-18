package cn.exrick.manager.dto.manager;

import cn.exrick.manager.pojo.TbUser;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class ManagerUserDto implements Serializable {
    private String username;

    private Long userId;

    private List<String> access;

    private String token;

    private String password;

    private String avator;

    public ManagerUserDto() {

    }

    public ManagerUserDto(TbUser tbUser, Set<String> access) {
        this.userId = tbUser.getId();
        this.username = tbUser.getUsername();
        this.token = "";
        this.password = tbUser.getPassword();
        this.access = Lists.newArrayList(Sets.newTreeSet(access));
        this.avator = tbUser.getFile();
    }
}
