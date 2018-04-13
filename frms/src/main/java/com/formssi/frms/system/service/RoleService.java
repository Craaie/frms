package com.formssi.frms.system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.formssi.frms.system.domain.SysRole;

@Service
public interface RoleService {

	SysRole get(Long id);

	List<SysRole> list();

	int save(SysRole role);

	int update(SysRole role);

	int remove(Long id);

	List<SysRole> list(Long userId);

	int batchremove(Long[] ids);
}
