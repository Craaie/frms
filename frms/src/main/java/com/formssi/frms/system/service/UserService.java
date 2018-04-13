package com.formssi.frms.system.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.formssi.frms.system.domain.SysDept;
import com.formssi.frms.system.domain.SysUser;
import com.formssi.frms.system.vo.UserVO;

@Service
public interface UserService {
	SysUser get(Long id);

	List<SysUser> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(SysUser user);

	int update(SysUser user);

	int remove(Long userId);

	int batchremove(Long[] userIds);

	boolean exit(Map<String, Object> params);

	Set<String> listRoles(Long userId);

	int resetPwd(UserVO userVO,SysUser SysUser) throws Exception;
	int adminResetPwd(UserVO userVO) throws Exception;

	/**
	 * 更新个人信息
	 * @param SysUser
	 * @return
	 */
	int updatePersonal(SysUser SysUser);

	/**
	 * 更新个人图片
	 * @param file 图片
	 * @param avatar_data 裁剪信息
	 * @param userId 用户ID
	 * @throws Exception
	 */
    Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception;

	com.formssi.frms.common.domain.Tree<SysDept> getTree();
}
