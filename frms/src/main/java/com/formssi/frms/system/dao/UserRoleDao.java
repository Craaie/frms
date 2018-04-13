package com.formssi.frms.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.formssi.frms.system.domain.SysUserRole;

/**
 * 用户与角色对应关系
 * 
 */
@Mapper
public interface UserRoleDao {

	SysUserRole get(Long id);

	List<SysUserRole> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(SysUserRole userRole);

	int update(SysUserRole userRole);

	int remove(Long id);

	int batchRemove(Long[] ids);

	List<Long> listRoleId(Long userId);

	int removeByUserId(Long userId);

	int removeByRoleId(Long roleId);

	int batchSave(List<SysUserRole> list);

	int batchRemoveByUserId(Long[] ids);
}
