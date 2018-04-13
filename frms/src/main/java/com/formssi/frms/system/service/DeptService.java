package com.formssi.frms.system.service;

import java.util.List;
import java.util.Map;

import com.formssi.frms.common.domain.Tree;
import com.formssi.frms.system.domain.SysDept;

/**
 * 部门管理
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-27 14:28:36
 */
public interface DeptService {
	
	SysDept get(Long deptId);
	
	List<SysDept> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SysDept sysDept);
	
	int update(SysDept sysDept);
	
	int remove(Long deptId);
	
	int batchRemove(Long[] deptIds);

	Tree<SysDept> getTree();
	
	boolean checkDeptHasUser(Long deptId);
}
