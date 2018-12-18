package cn.exrick.manager.web.controller;

import cn.exrick.common.annotation.SystemControllerLog;
import cn.exrick.common.pojo.DataTablesResult;
import cn.exrick.common.pojo.Result;
import cn.exrick.common.utils.GeetestLib;
import cn.exrick.common.utils.ResultUtil;
import cn.exrick.manager.dto.TbRoleDto;
import cn.exrick.manager.dto.manager.ManagerUserDto;
import cn.exrick.manager.dto.manager.MenuDto;
import cn.exrick.manager.pojo.TbPermission;
import cn.exrick.manager.pojo.TbRole;
import cn.exrick.manager.pojo.TbUser;
import cn.exrick.manager.service.UserService;
import cn.exrick.manager.web.controller.req.LoginReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author Exrickx
 */
@RestController
@Api(description = "管理员管理")
public class UserController {

    final static Logger log = LoggerFactory.getLogger(UserController.class);

    @Reference
    private UserService userService;

    @RequestMapping(value = "/user/testAuthorization", method = RequestMethod.GET)
    @ApiOperation(value = "测试用户登录状态，当为login状态是直接跳转到工作页")
    public Result<Object> testAuthorization() {
        Subject subject = SecurityUtils.getSubject();
        boolean authenticated = subject.isAuthenticated();
        if (!authenticated) {
            return new ResultUtil<>().setErrorMsg("用户没有登录");
        } else {
            return new ResultUtil<>().setData(null);
        }
    }

    @RequestMapping(value = "/geetestInit", method = RequestMethod.GET)
    @ApiOperation(value = "极验初始化")
    public String geetesrInit(HttpServletRequest request) {

        GeetestLib gtSdk = new GeetestLib(GeetestLib.id, GeetestLib.key, GeetestLib.newfailback);

        String resStr = "{}";

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();

        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);

        //将服务器状态设置到session中
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);

        resStr = gtSdk.getResponseStr();

        return resStr;
    }

    @RequestMapping(value = "/user/login", method = {RequestMethod.POST, RequestMethod.GET}, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "用户登录")
    @SystemControllerLog(description = "登录系统")
    public Result<Object> login(@RequestBody LoginReq loginReq) {
        String secret = userService.getAuthSecret();
        //验证验证码
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        boolean authorize = gAuth.authorize(secret, loginReq.getAuth());
        if (!authorize) {
            return new ResultUtil<>().setErrorMsg("验证码错误");
        }

        // 验证成功
        Subject subject = SecurityUtils.getSubject();
        //MD5加密
        String md5Pass = DigestUtils.md5DigestAsHex(loginReq.getPassword().getBytes());
        UsernamePasswordToken token = new UsernamePasswordToken(loginReq.getUsername(), md5Pass);
        try {
            subject.login(token);
            return new ResultUtil<Object>().setData(null);
        } catch (Exception e) {
            return new ResultUtil<Object>().setErrorMsg("用户名或密码错误");
        }
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    @ApiOperation(value = "退出登录")
    public Result<Object> logout() {

        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/info", method = RequestMethod.GET)
    @ApiOperation(value = "获取登录用户信息")
    public Result<ManagerUserDto> getUserInfo() {

        String username = SecurityUtils.getSubject().getPrincipal().toString();
        ManagerUserDto managerUserDto = userService.getUserByUsername(username);
        managerUserDto.setPassword("");
        return new ResultUtil<ManagerUserDto>().setData(managerUserDto);
    }

    @RequestMapping(value = "/user/roleList", method = RequestMethod.GET)
    @ApiOperation(value = "获取角色列表")
    public DataTablesResult getRoleList() {

        DataTablesResult result = userService.getRoleList();
        return result;
    }

    @RequestMapping(value = "/user/getAllRoles", method = RequestMethod.GET)
    @ApiOperation(value = "获取所有角色")
    public Result<List<TbRole>> getAllRoles() {

        List<TbRole> list = userService.getAllRoles();
        return new ResultUtil<List<TbRole>>().setData(list);
    }

    @RequestMapping(value = "/user/roleName", method = RequestMethod.GET)
    @ApiOperation(value = "判断角色是否已存在")
    public boolean roleName(String name) {

        if (userService.getRoleByRoleName(name) != null) {
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/user/edit/roleName/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "判断编辑角色是否已存在")
    public boolean roleName(@PathVariable int id, String name) {

        return userService.getRoleByEditName(id, name);
    }

    @RequestMapping(value = "/user/addRole", method = RequestMethod.POST)
    @ApiOperation(value = "添加角色")
    public Result<Object> addRole(@ModelAttribute TbRoleDto tbRole) {

        userService.addRole(tbRole);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/updateRole", method = RequestMethod.POST)
    @ApiOperation(value = "更新角色")
    public Result<Object> updateRole(@ModelAttribute TbRoleDto tbRole) {

        userService.updateRole(tbRole);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/delRole/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除角色")
    public Result<Object> delRole(@PathVariable int[] ids) {

        for (int id : ids) {
            int result = userService.deleteRole(id);
            if (result == 0) {
                return new ResultUtil<Object>().setErrorMsg("id为" + id + "的角色被使用中，不能删除！");
            }
        }
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/roleCount", method = RequestMethod.GET)
    @ApiOperation(value = "统计角色数")
    public Result<Object> getRoleCount() {

        Long result = userService.countRole();
        return new ResultUtil<Object>().setData(result);
    }

    @RequestMapping(value = "/user/permissionList", method = RequestMethod.GET)
    @ApiOperation(value = "获取权限列表")
    public DataTablesResult getPermissionList() {

        DataTablesResult result = userService.getPermissionList();
        return result;
    }

    @RequestMapping(value = "/user/addPermission", method = RequestMethod.POST)
    @ApiOperation(value = "添加权限")
    public Result<Object> addPermission(@ModelAttribute TbPermission tbPermission) {

        userService.addPermission(tbPermission);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/updatePermission", method = RequestMethod.POST)
    @ApiOperation(value = "更新权限")
    public Result<Object> updatePermission(@ModelAttribute TbPermission tbPermission) {

        userService.updatePermission(tbPermission);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/delPermission/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除权限")
    public Result<Object> delPermission(@PathVariable int[] ids) {

        for (int id : ids) {
            userService.deletePermission(id);
        }
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/permissionCount", method = RequestMethod.GET)
    @ApiOperation(value = "统计权限数")
    public Result<Object> getPermissionCount() {

        Long result = userService.countPermission();
        return new ResultUtil<Object>().setData(result);
    }

    @RequestMapping(value = "/user/userList", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户列表")
    public DataTablesResult getUserList() {

        DataTablesResult result = userService.getUserList();
        return result;
    }

    @RequestMapping(value = "/user/username", method = RequestMethod.GET)
    @ApiOperation(value = "判断用户名是否存在")
    public boolean getUserByName(String username) {

        return userService.getUserByName(username);
    }

    @RequestMapping(value = "/user/phone", method = RequestMethod.GET)
    @ApiOperation(value = "判断手机是否存在")
    public boolean getUserByPhone(String phone) {

        return userService.getUserByPhone(phone);
    }

    @RequestMapping(value = "/user/email", method = RequestMethod.GET)
    @ApiOperation(value = "判断邮箱是否存在")
    public boolean getUserByEmail(String email) {

        return userService.getUserByEmail(email);
    }

    @RequestMapping(value = "/user/addUser", method = RequestMethod.POST)
    @ApiOperation(value = "添加用户")
    public Result<Object> addUser(@ModelAttribute TbUser tbUser) {

        userService.addUser(tbUser);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/updateUser", method = RequestMethod.POST)
    @ApiOperation(value = "更新用户")
    public Result<Object> updateUser(@ModelAttribute TbUser tbUser) {

        userService.updateUser(tbUser);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/edit/username/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "判断编辑用户名是否存在")
    public boolean getUserByEditName(@PathVariable Long id, String username) {

        return userService.getUserByEditName(id, username);
    }

    @RequestMapping(value = "/user/edit/phone/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "判断编辑手机是否存在")
    public boolean getUserByEditPhone(@PathVariable Long id, String phone) {

        return userService.getUserByEditPhone(id, phone);
    }

    @RequestMapping(value = "/user/edit/email/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "判断编辑用户名是否存在")
    public boolean getUserByEditEmail(@PathVariable Long id, String email) {

        return userService.getUserByEditEmail(id, email);
    }

    @RequestMapping(value = "/user/stop/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "停用用户")
    public Result<Object> stopUser(@PathVariable Long id) {

        userService.changeUserState(id, 0);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/start/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "启用用户")
    public Result<Object> startUser(@PathVariable Long id) {

        userService.changeUserState(id, 1);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/changePass", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户密码")
    public Result<Object> changePass(@ModelAttribute TbUser tbUser) {

        userService.changePassword(tbUser);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/delUser/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除用户")
    public Result<Object> delUser(@PathVariable Long[] ids) {

        for (Long id : ids) {
            userService.deleteUser(id);
        }
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/userCount", method = RequestMethod.GET)
    @ApiOperation(value = "统计用户数")
    public Result<Object> getUserCount() {

        Long result = userService.countUser();
        return new ResultUtil<Object>().setData(result);
    }

    @RequestMapping(value = "/user/menu/get/{id}")
    @ApiOperation(value = "获取用户的菜单信息")
    public Result<Object> getUserMenu(@PathVariable long id) {
        List<MenuDto> menuDtos = userService.getUserMenu(id);
        return new ResultUtil<>().setData(menuDtos);
    }
}
