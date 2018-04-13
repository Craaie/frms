package com.formssi.frms.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.formssi.frms.common.domain.Tree;
import com.formssi.frms.common.utils.BuildTree;
import com.formssi.frms.common.utils.MD5Utils;
import com.formssi.frms.system.dao.DeptDao;
import com.formssi.frms.system.dao.UserDao;
import com.formssi.frms.system.dao.UserRoleDao;
import com.formssi.frms.system.domain.SysDept;
import com.formssi.frms.system.domain.SysUser;
import com.formssi.frms.system.domain.SysUserRole;
import com.formssi.frms.system.service.UserService;
import com.formssi.frms.system.vo.UserVO;

@Transactional
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao userMapper;
	@Autowired
	UserRoleDao userRoleMapper;
	@Autowired
	DeptDao deptMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Override
	public SysUser get(Long id) {
		List<Long> roleIds = userRoleMapper.listRoleId(id);
		SysUser user = userMapper.get(id);
		user.setDeptName(deptMapper.get(user.getDeptId()).getName());
		user.setRoleIds(roleIds);
		return user;
	}

	@Override
	public List<SysUser> list(Map<String, Object> map) {
		return userMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return userMapper.count(map);
	}

	@Transactional
	@Override
	public int save(SysUser user) {
		int count = userMapper.save(user);
		Long userId = user.getUserId();
		List<Long> roles = user.getRoleIds();
		userRoleMapper.removeByUserId(userId);
		List<SysUserRole> list = new ArrayList<>();
		for (Long roleId : roles) {
			SysUserRole ur = new SysUserRole();
			ur.setUserId(userId);
			ur.setRoleId(roleId);
			list.add(ur);
		}
		if (list.size() > 0) {
			userRoleMapper.batchSave(list);
		}
		return count;
	}

	@Override
	public int update(SysUser user) {
		int r = userMapper.update(user);
		Long userId = user.getUserId();
		List<Long> roles = user.getRoleIds();
		userRoleMapper.removeByUserId(userId);
		List<SysUserRole> list = new ArrayList<>();
		for (Long roleId : roles) {
			SysUserRole ur = new SysUserRole();
			ur.setUserId(userId);
			ur.setRoleId(roleId);
			list.add(ur);
		}
		if (list.size() > 0) {
			userRoleMapper.batchSave(list);
		}
		return r;
	}

	@Override
	public int remove(Long userId) {
		userRoleMapper.removeByUserId(userId);
		return userMapper.remove(userId);
	}

	@Override
	public boolean exit(Map<String, Object> params) {
		boolean exit;
		exit = userMapper.list(params).size() > 0;
		return exit;
	}

	@Override
	public Set<String> listRoles(Long userId) {
		return null;
	}

	@Override
	public int resetPwd(UserVO userVO,SysUser SysUser) throws Exception {
		if(Objects.equals(userVO.getSysUser().getUserId(),SysUser.getUserId())){
			if(Objects.equals(MD5Utils.encrypt(SysUser.getUsername(),userVO.getPwdOld()),SysUser.getPassword())){
				SysUser.setPassword(MD5Utils.encrypt(SysUser.getUsername(),userVO.getPwdNew()));
				return userMapper.update(SysUser);
			}else{
				throw new Exception("输入的旧密码有误！");
			}
		}else{
			throw new Exception("你修改的不是你登录的账号！");
		}
	}
	@Override
	public int adminResetPwd(UserVO userVO) throws Exception {
		SysUser SysUser =get(userVO.getSysUser().getUserId());
		if("admin".equals(SysUser.getUsername())){
			throw new Exception("超级管理员的账号不允许直接重置！");
		}
		SysUser.setPassword(MD5Utils.encrypt(SysUser.getUsername(), userVO.getPwdNew()));
		return userMapper.update(SysUser);


	}

	@Transactional
	@Override
	public int batchremove(Long[] userIds) {
		int count = userMapper.batchRemove(userIds);
		userRoleMapper.batchRemoveByUserId(userIds);
		return count;
	}

	@Override
	public Tree<SysDept> getTree() {
		List<Tree<SysDept>> trees = new ArrayList<Tree<SysDept>>();
		List<SysDept> depts = deptMapper.list(new HashMap<String, Object>(16));
		Long[] pDepts = deptMapper.listParentDept();
		Long[] uDepts = userMapper.listAllDept();
		Long[] allDepts = (Long[]) ArrayUtils.addAll(pDepts, uDepts);
		for (SysDept dept : depts) {
			if (!ArrayUtils.contains(allDepts, dept.getDeptId())) {
				continue;
			}
			Tree<SysDept> tree = new Tree<SysDept>();
			tree.setId(dept.getDeptId().toString());
			tree.setParentId(dept.getParentId().toString());
			tree.setText(dept.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			state.put("mType", "dept");
			tree.setState(state);
			trees.add(tree);
		}
		List<SysUser> users = userMapper.list(new HashMap<String, Object>(16));
		for (SysUser user : users) {
			Tree<SysDept> tree = new Tree<SysDept>();
			tree.setId(user.getUserId().toString());
			tree.setParentId(user.getDeptId().toString());
			tree.setText(user.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			state.put("mType", "user");
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<SysDept> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public int updatePersonal(SysUser SysUser) {
		return userMapper.update(SysUser);
	}

    @Override
    public Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception {
		return null;
    }

}
