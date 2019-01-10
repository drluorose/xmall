package cn.exrick.manager.service.impl;

import cn.exrick.common.enums.EnableStatusEnum;
import cn.exrick.common.exception.XmallException;
import cn.exrick.common.pojo.DataTablesResult;
import cn.exrick.manager.dto.RoleDto;
import cn.exrick.manager.dto.TbRoleDto;
import cn.exrick.manager.dto.TbUserDto;
import cn.exrick.manager.dto.manager.ManagerUserDto;
import cn.exrick.manager.dto.manager.MenuDto;
import cn.exrick.manager.mapper.MenuMapper;
import cn.exrick.manager.mapper.RoleMenuMapper;
import cn.exrick.manager.mapper.TbPermissionMapper;
import cn.exrick.manager.mapper.TbRoleMapper;
import cn.exrick.manager.mapper.TbRolePermMapper;
import cn.exrick.manager.mapper.TbUserMapper;
import cn.exrick.manager.mapper.ext.TbRoleExtMapper;
import cn.exrick.manager.mapper.ext.TbUserExtMapper;
import cn.exrick.manager.pojo.*;
import cn.exrick.manager.service.UserService;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Exrickx
 */
@Slf4j
@Component
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserExtMapper tbUserExtMapper;

    @Autowired
    private TbUserMapper tbUserMapper;

    @Autowired
    private TbRoleExtMapper tbRoleExtMapper;

    @Autowired
    private TbRoleMapper tbRoleMapper;

    @Autowired
    private TbPermissionMapper tbPermissionMapper;

    @Autowired
    private TbRolePermMapper tbRolePermMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public String getAuthSecret() {
        return "LU2D7U74KG52GKDN";
    }

    @Override
    public List<MenuDto> getUserMenu(long id) {
        //这里默认id有效
        TbUser tbUser = this.tbUserMapper.selectByPrimaryKey(id);
        if (Objects.isNull(tbUser)) {
            return Lists.newArrayList();
        }
        TbRole tbRole = this.tbRoleMapper.selectByPrimaryKey(tbUser.getRoleId());
        if (Objects.isNull(tbRole)) {
            return Lists.newArrayList();
        }
        return getMenuDtoByRoles(Lists.newArrayList(tbRole));
    }

    private List<MenuDto> getMenuDtoByRoles(List<TbRole> tbRoles) {
        if (CollectionUtils.isEmpty(tbRoles)) {
            return Lists.newArrayList();
        }
        List<Integer> roleIds = tbRoles.stream().map(TbRole::getId).collect(Collectors.toList());
        RoleMenuExample roleMenuExample = new RoleMenuExample();
        roleMenuExample.createCriteria()
            .andRoleIdIn(roleIds)
            .andValidEqualTo(EnableStatusEnum.ENABLED);
        List<RoleMenu> roleMenus = roleMenuMapper.selectByExample(roleMenuExample);
        if (CollectionUtils.isEmpty(roleMenus)) {
            return Lists.newArrayList();
        }
        return getMenuDtoByRoleMenuCollection(roleMenus);
    }

    private List<MenuDto> getMenuDtoByRoleMenuCollection(List<RoleMenu> roleMenus) {
        if (CollectionUtils.isEmpty(roleMenus)) {
            return Lists.newArrayList();
        }
        List<Integer> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());

        MenuExample menuExample = new MenuExample();
        menuExample.createCriteria()
            .andIdIn(menuIds)
            .andPidIsNull()
            .andValidEqualTo(EnableStatusEnum.ENABLED);
        List<Menu> menus = menuMapper.selectByExample(menuExample);
        if (CollectionUtils.isEmpty(menus)) {
            return Lists.newArrayList();
        }
        List<MenuDto> menuDtos = Lists.newArrayListWithCapacity(menus.size());
        for (Menu menu : menus) {
            menuDtos.add(makeTopLevelMenu(menu, menuIds));
        }
        return menuDtos;
    }

    private MenuDto makeTopLevelMenu(Menu menu, List<Integer> menuIds) {
        MenuDto menuDto = new MenuDto(menu);
        List<Menu> children = getChildrenByParentMenu(menu, menuIds);
        List<MenuDto> childrenMenuDto = children.stream().map(MenuDto::new).collect(Collectors.toList());
        menuDto.setChildren(childrenMenuDto);
        return menuDto;
    }

    private List<Menu> getChildrenByParentMenu(Menu menu, List<Integer> menuIds) {
        MenuExample menuExample = new MenuExample();
        menuExample.createCriteria()
            .andIdIn(menuIds)
            .andPidEqualTo(menu.getId())
            .andValidEqualTo(EnableStatusEnum.ENABLED);
        List<Menu> children = menuMapper.selectByExample(menuExample);
        if (CollectionUtils.isEmpty(children)) {
            return Lists.newArrayList();
        }
        return children;
    }

    @Override
    public ManagerUserDto getUserByUsername(String username) {

        List<TbUser> list;
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andStateEqualTo(1);

        TbUser tbUser = tbUserMapper.selectOneByExample(example);
        if (Objects.isNull(tbUser)) {
            throw new XmallException("通过ID获取用户信息失败");
        }
        Set<String> permissions = tbUserExtMapper.getPermissions(tbUser.getUsername());
        return new ManagerUserDto(tbUser, permissions);
    }

    @Override
    public Set<String> getRoles(String username) {

        return tbUserExtMapper.getRoles(username);
    }

    @Override
    public Set<String> getPermissions(String username) {

        return tbUserExtMapper.getPermissions(username);
    }

    @Override
    public DataTablesResult getRoleList() {

        DataTablesResult result = new DataTablesResult();
        List<RoleDto> list = new ArrayList<>();
        TbRoleExample example = new TbRoleExample();
        List<TbRole> list1 = tbRoleMapper.selectByExample(example);
        if (list1 == null) {
            throw new XmallException("获取角色列表失败");
        }
        for (TbRole tbRole : list1) {
            RoleDto roleDto = new RoleDto();
            roleDto.setId(tbRole.getId());
            roleDto.setName(tbRole.getName());
            roleDto.setDescription(tbRole.getDescription());

            List<String> permissions = tbUserExtMapper.getPermsByRoleId(tbRole.getId());
            String names = "";
            if (permissions.size() > 1) {
                names += permissions.get(0);
                for (int i = 1; i < permissions.size(); i++) {
                    names += "|" + permissions.get(i);
                }
            } else if (permissions.size() == 1) {
                names += permissions.get(0);
            }
            roleDto.setPermissions(names);

            list.add(roleDto);
        }
        result.setData(list);
        return result;
    }

    @Override
    public List<TbRole> getAllRoles() {

        TbRoleExample example = new TbRoleExample();
        List<TbRole> list = tbRoleMapper.selectByExample(example);
        if (list == null) {
            throw new XmallException("获取所有角色列表失败");
        }
        return list;
    }

    @Override
    public int addRole(TbRoleDto tbRole) {

        if (getRoleByRoleName(tbRole.getName()) != null) {
            throw new XmallException("该角色名已存在");
        }
        if (tbRoleMapper.insert(tbRole) != 1) {
            throw new XmallException("添加角色失败");
        }
        if (tbRole.getRoles() != null) {
            TbRole newRole = getRoleByRoleName(tbRole.getName());
            for (int i = 0; i < tbRole.getRoles().length; i++) {
                TbRolePerm tbRolePerm = new TbRolePerm();
                tbRolePerm.setRoleId(newRole.getId());
                tbRolePerm.setPermissionId(tbRole.getRoles()[i]);
                if (tbRolePermMapper.insert(tbRolePerm) != 1) {
                    throw new XmallException("添加角色-权限失败");
                }
            }
        }
        return 1;
    }

    @Override
    public TbRole getRoleByRoleName(String roleName) {

        TbRoleExample example = new TbRoleExample();
        TbRoleExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(roleName);
        List<TbRole> list = new ArrayList<>();
        try {
            list = tbRoleMapper.selectByExample(example);
        } catch (Exception e) {
            throw new XmallException("通过角色名获取角色失败");
        }
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public boolean getRoleByEditName(int id, String roleName) {

        TbRole tbRole = tbRoleMapper.selectByPrimaryKey(id);
        TbRole newRole = null;
        if (tbRole == null) {
            throw new XmallException("通过ID获取角色失败");
        }
        if (!tbRole.getName().equals(roleName)) {
            newRole = getRoleByRoleName(roleName);
        }
        if (newRole == null) {
            return true;
        }
        return false;
    }

    @Override
    public int updateRole(TbRoleDto tbRole) {

        if (!getRoleByEditName(tbRole.getId(), tbRole.getName())) {
            throw new XmallException("该角色名已存在");
        }
        if (tbRoleMapper.updateByPrimaryKey(tbRole) != 1) {
            throw new XmallException("更新角色失败");
        }
        if (tbRole.getRoles() != null) {
            //删除已有角色-权限
            TbRolePermExample example = new TbRolePermExample();
            TbRolePermExample.Criteria criteria = example.createCriteria();
            criteria.andRoleIdEqualTo(tbRole.getId());
            List<TbRolePerm> list = tbRolePermMapper.selectByExample(example);
            if (list != null) {
                for (TbRolePerm tbRolePerm : list) {
                    if (tbRolePermMapper.deleteByPrimaryKey(tbRolePerm.getId()) != 1) {
                        throw new XmallException("删除角色权限失败");
                    }
                }
            }
            //新增
            for (int i = 0; i < tbRole.getRoles().length; i++) {
                TbRolePerm tbRolePerm = new TbRolePerm();
                tbRolePerm.setRoleId(tbRole.getId());
                tbRolePerm.setPermissionId(tbRole.getRoles()[i]);

                if (tbRolePermMapper.insert(tbRolePerm) != 1) {
                    throw new XmallException("编辑角色-权限失败");
                }
            }
        } else {
            TbRolePermExample example = new TbRolePermExample();
            TbRolePermExample.Criteria criteria = example.createCriteria();
            criteria.andRoleIdEqualTo(tbRole.getId());
            List<TbRolePerm> list = tbRolePermMapper.selectByExample(example);
            if (list != null) {
                for (TbRolePerm tbRolePerm : list) {
                    if (tbRolePermMapper.deleteByPrimaryKey(tbRolePerm.getId()) != 1) {
                        throw new XmallException("删除角色权限失败");
                    }
                }
            }
        }
        return 1;
    }

    @Override
    public int deleteRole(int id) {

        List<String> list = tbRoleExtMapper.getUsedRoles(id);
        if (list == null) {
            throw new XmallException("查询用户角色失败");
        }
        if (list.size() > 0) {
            return 0;
        }
        if (tbRoleMapper.deleteByPrimaryKey(id) != 1) {
            throw new XmallException("删除角色失败");
        }
        TbRolePermExample example = new TbRolePermExample();
        TbRolePermExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(id);
        List<TbRolePerm> list1 = tbRolePermMapper.selectByExample(example);
        if (list1 == null) {
            throw new XmallException("查询角色权限失败");
        }
        for (TbRolePerm tbRolePerm : list1) {
            if (tbRolePermMapper.deleteByPrimaryKey(tbRolePerm.getId()) != 1) {
                throw new XmallException("删除角色权限失败");
            }
        }
        return 1;
    }

    @Override
    public Long countRole() {

        TbRoleExample example = new TbRoleExample();
        Long result = tbRoleMapper.countByExample(example);
        return result;
    }

    @Override
    public DataTablesResult getPermissionList() {

        DataTablesResult result = new DataTablesResult();
        TbPermissionExample example = new TbPermissionExample();
        List<TbPermission> list = tbPermissionMapper.selectByExample(example);
        if (list == null) {
            throw new XmallException("获取权限列表失败");
        }
        result.setSuccess(true);
        result.setData(list);
        return result;
    }

    @Override
    public int addPermission(TbPermission tbPermission) {

        if (tbPermissionMapper.insert(tbPermission) != 1) {
            throw new XmallException("添加权限失败");
        }
        return 1;
    }

    @Override
    public int updatePermission(TbPermission tbPermission) {

        if (tbPermissionMapper.updateByPrimaryKey(tbPermission) != 1) {
            throw new XmallException("更新权限失败");
        }
        return 1;
    }

    @Override
    public int deletePermission(int id) {

        if (tbPermissionMapper.deleteByPrimaryKey(id) != 1) {
            throw new XmallException("删除权限失败");
        }
        TbRolePermExample example = new TbRolePermExample();
        TbRolePermExample.Criteria criteria = example.createCriteria();
        criteria.andPermissionIdEqualTo(id);
        tbRolePermMapper.deleteByExample(example);
        return 1;
    }

    @Override
    public Long countPermission() {

        TbPermissionExample example = new TbPermissionExample();
        Long result = tbPermissionMapper.countByExample(example);
        return result;
    }

    @Override
    public DataTablesResult getUserList() {

        DataTablesResult result = new DataTablesResult();
        TbUserExample example = new TbUserExample();
        List<TbUser> list = tbUserMapper.selectByExample(example);
        if (list == null) {
            throw new XmallException("获取用户列表失败");
        }
        List<TbUserDto> tbUserDtos = Lists.transform(list, TbUserDto::new);
        for (TbUserDto tbUser : tbUserDtos) {
            String names = "";
            Iterator it = getRoles(tbUser.getUsername()).iterator();
            while (it.hasNext()) {
                names += it.next() + " ";
            }
            tbUser.setPassword("");
            tbUser.setRoleNames(names);
        }
        result.setData(list);
        return result;
    }

    @Override
    public boolean getUserByName(String username) {

        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = tbUserMapper.selectByExample(example);
        if (list.size() != 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean getUserByPhone(String phone) {

        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andPhoneEqualTo(phone);
        List<TbUser> list = tbUserMapper.selectByExample(example);
        if (list.size() != 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean getUserByEmail(String emaill) {

        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andEmailEqualTo(emaill);
        List<TbUser> list = tbUserMapper.selectByExample(example);
        if (list.size() != 0) {
            return false;
        }
        return true;
    }

    @Override
    public int addUser(TbUser user) {

        if (!getUserByName(user.getUsername())) {
            throw new XmallException("用户名已存在");
        }
        if (!getUserByPhone(user.getPhone())) {
            throw new XmallException("手机号已存在");
        }
        if (!getUserByEmail(user.getEmail())) {
            throw new XmallException("邮箱已存在");
        }
        String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Pass);
        user.setState(1);
        user.setCreated(new Date());
        user.setUpdated(new Date());
        if (tbUserMapper.insert(user) != 1) {
            throw new XmallException("添加用户失败");
        }
        return 1;
    }

    @Override
    public TbUser getUserById(Long id) {

        TbUser tbUser = tbUserMapper.selectByPrimaryKey(id);
        if (tbUser == null) {
            throw new XmallException("通过ID获取用户失败");
        }
        tbUser.setPassword("");
        return tbUser;
    }

    @Override
    public int updateUser(TbUser user) {

        TbUser old = tbUserMapper.selectByPrimaryKey(user.getId());
        user.setPassword(old.getPassword());
        user.setState(old.getState());
        user.setCreated(old.getCreated());
        user.setUpdated(new Date());
        if (tbUserMapper.updateByPrimaryKey(user) != 1) {
            throw new XmallException("更新用户失败");
        }
        return 1;
    }

    @Override
    public int changeUserState(Long id, int state) {

        TbUser tbUser = tbUserMapper.selectByPrimaryKey(id);
        tbUser.setState(state);
        tbUser.setUpdated(new Date());
        if (tbUserMapper.updateByPrimaryKey(tbUser) != 1) {
            throw new XmallException("更新用户状态失败");
        }
        return 1;
    }

    @Override
    public int changePassword(TbUser tbUser) {

        TbUser old = tbUserMapper.selectByPrimaryKey(tbUser.getId());
        old.setUpdated(new Date());
        String md5Pass = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
        old.setPassword(md5Pass);
        if (tbUserMapper.updateByPrimaryKey(old) != 1) {
            throw new XmallException("修改用户密码失败");
        }
        return 1;
    }

    @Override
    public boolean getUserByEditName(Long id, String username) {

        TbUser tbUser = getUserById(id);
        boolean result = true;
        if (tbUser.getUsername() == null || !tbUser.getUsername().equals(username)) {
            result = getUserByName(username);
        }
        return result;
    }

    @Override
    public boolean getUserByEditPhone(Long id, String phone) {

        TbUser tbUser = getUserById(id);
        boolean result = true;
        if (tbUser.getPhone() == null || !tbUser.getPhone().equals(phone)) {
            result = getUserByPhone(phone);
        }
        return result;
    }

    @Override
    public boolean getUserByEditEmail(Long id, String email) {

        TbUser tbUser = getUserById(id);
        boolean result = true;
        if (tbUser.getEmail() == null || !tbUser.getEmail().equals(email)) {
            result = getUserByEmail(email);
        }
        return result;
    }

    @Override
    public int deleteUser(Long userId) {

        if (tbUserMapper.deleteByPrimaryKey(userId) != 1) {
            throw new XmallException("删除用户失败");
        }
        return 1;
    }

    @Override
    public Long countUser() {

        TbUserExample example = new TbUserExample();
        Long result = tbUserMapper.countByExample(example);
        if (result == null) {
            throw new XmallException("统计用户数失败");
        }
        return result;
    }
}
