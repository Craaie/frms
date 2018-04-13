package com.formssi.frms.system.service;

import java.util.Collection;
import java.util.List;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import com.formssi.frms.system.domain.SysUser;
import com.formssi.frms.system.domain.UserOnline;

@Service
public interface SessionService {
	List<UserOnline> list();

	List<SysUser> listOnlineUser();

	Collection<Session> sessionList();
	
	boolean forceLogout(String sessionId);
}
