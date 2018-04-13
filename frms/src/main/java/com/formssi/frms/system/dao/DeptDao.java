package com.formssi.frms.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.formssi.frms.system.domain.SysDept;

/**
 * 部门管理
 */
@Mapper
public interface DeptDao {

	SysDept get(Long deptId);
	
	List<SysDept> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(SysDept dept);
	
	int update(SysDept dept);
	
	int remove(Long deptId);
	
	int batchRemove(Long[] deptIds);
	
	Long[] listParentDept();
	
	int getDeptUserNumber(Long deptId);
}
