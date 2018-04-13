package com.formssi.frms.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formssi.frms.system.dao.RoleDao;
import com.formssi.frms.system.dao.RoleMenuDao;
import com.formssi.frms.system.dao.UserDao;
import com.formssi.frms.system.dao.UserRoleDao;
import com.formssi.frms.system.domain.SysRole;
import com.formssi.frms.system.domain.SysRoleMenu;
import com.formssi.frms.system.service.RoleService;


@Service
public class RoleServiceImpl implements RoleService {

    public static final String ROLE_ALL_KEY = "\"role_all\"";

    public static final String DEMO_CACHE_NAME = "role";

    @Autowired
    RoleDao roleMapper;
    @Autowired
    RoleMenuDao roleMenuMapper;
    @Autowired
    UserDao userMapper;
    @Autowired
    UserRoleDao userRoleMapper;

    @Override
    public List<SysRole> list() {
        List<SysRole> roles = roleMapper.list(new HashMap<>(16));
        return roles;
    }


    @Override
    public List<SysRole> list(Long userId) {
        List<Long> rolesIds = userRoleMapper.listRoleId(userId);
        List<SysRole> roles = roleMapper.list(new HashMap<>(16));
        for (SysRole SysRole : roles) {
            SysRole.setRoleSign("false");
            for (Long roleId : rolesIds) {
                if (Objects.equals(SysRole.getRoleId(), roleId)) {
                    SysRole.setRoleSign("true");
                    break;
                }
            }
        }
        return roles;
    }
    @Transactional
    @Override
    public int save(SysRole role) {
        int count = roleMapper.save(role);
        List<Long> menuIds = role.getMenuIds();
        Long roleId = role.getRoleId();
        List<SysRoleMenu> rms = new ArrayList<>();
        for (Long menuId : menuIds) {
            SysRoleMenu rmDo = new SysRoleMenu();
            rmDo.setRoleId(roleId);
            rmDo.setMenuId(menuId);
            rms.add(rmDo);
        }
        roleMenuMapper.removeByRoleId(roleId);
        if (rms.size() > 0) {
            roleMenuMapper.batchSave(rms);
        }
        return count;
    }

    @Transactional
    @Override
    public int remove(Long id) {
        int count = roleMapper.remove(id);
        userRoleMapper.removeByRoleId(id);
        roleMenuMapper.removeByRoleId(id);
        return count;
    }

    @Override
    public SysRole get(Long id) {
        SysRole SysRole = roleMapper.get(id);
        return SysRole;
    }

    @Override
    public int update(SysRole role) {
        int r = roleMapper.update(role);
        List<Long> menuIds = role.getMenuIds();
        Long roleId = role.getRoleId();
        roleMenuMapper.removeByRoleId(roleId);
        List<SysRoleMenu> rms = new ArrayList<>();
        for (Long menuId : menuIds) {
            SysRoleMenu rmDo = new SysRoleMenu();
            rmDo.setRoleId(roleId);
            rmDo.setMenuId(menuId);
            rms.add(rmDo);
        }
        if (rms.size() > 0) {
            roleMenuMapper.batchSave(rms);
        }
        return r;
    }

    @Override
    public int batchremove(Long[] ids) {
        int r = roleMapper.batchRemove(ids);
        return r;
    }

}
