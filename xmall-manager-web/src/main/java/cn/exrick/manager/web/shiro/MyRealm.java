package cn.exrick.manager.web.shiro;

import cn.exrick.manager.dto.manager.ManagerUserDto;
import cn.exrick.manager.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author Exrickx
 */
@Slf4j
public class MyRealm extends AuthorizingRealm {

    @Reference(version = "1.0.1")
    private UserService userService;

    /**
     * 返回权限信息
     *
     * @param principal
     *
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {

        //获取用户名
        String username = principal.getPrimaryPrincipal().toString();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //获得授权角色
        authorizationInfo.setRoles(this.userService.getRoles(username));
        //获得授权权限
        authorizationInfo.setStringPermissions(userService.getPermissions(username));
        return authorizationInfo;
    }

    /**
     * 先执行登录验证
     *
     * @param token
     *
     * @return
     *
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //获取用户名密码
        String username = token.getPrincipal().toString();
        ManagerUserDto managerUserDto = userService.getUserByUsername(username);
        if (managerUserDto != null) {
            //得到用户账号和密码存放到authenticationInfo中用于Controller层的权限判断 第三个参数随意不能为null
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(managerUserDto.getUsername(), managerUserDto.getPassword(),
                managerUserDto.getUsername());
            return authenticationInfo;
        } else {
            return null;
        }
    }

}
