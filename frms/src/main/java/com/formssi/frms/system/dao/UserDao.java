package com.formssi.frms.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.formssi.frms.system.domain.SysUser;

/**
 * 
 */
@Mapper
public interface UserDao {

	SysUser get(Long userId);
	
	List<SysUser> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(SysUser user);
	
	int update(SysUser user);
	
	int remove(Long userId);
	
	int batchRemove(Long[] userIds);
	
	Long[] listAllDept();

}
