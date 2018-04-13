package com.formssi.frms.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.formssi.frms.system.domain.SysRole;

/**
 * 角色
 */
@Mapper
public interface RoleDao {

	SysRole get(Long roleId);
	
	List<SysRole> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(SysRole role);
	
	int update(SysRole role);
	
	int remove(Long roleId);
	
	int batchRemove(Long[] roleIds);
}
