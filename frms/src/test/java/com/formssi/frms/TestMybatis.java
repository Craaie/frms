package com.formssi.frms;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.formssi.frms.system.dao.UserDao;
import com.formssi.frms.system.domain.SysUser;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMybatis {

	@Autowired
	private UserDao userDao;
	
	@Test
	public void testUser() {
		SysUser user = userDao.get(Long.valueOf(1));
		System.out.println(user.getUsername() + ";" + user.getMobile());
	}
	
	@Test
	public void testAddUser() {
		SysUser user = new SysUser();
		user.setUserId(Long.valueOf(66666));
		user.setCity("香港");
		user.setBirth(new Date());
		user.setEmail("g@163.com");
		user.setName("张三");
		user.setUsername("zhangsan");
		user.setPassword("111111");
		userDao.save(user);
	}
	
	@Test
	public void testUpdateUser() {
		SysUser user = userDao.get(Long.valueOf(138));
		user.setPassword("111111");
		userDao.update(user);
	}
}
