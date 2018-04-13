package com.formssi.frms.system.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.formssi.frms.common.domain.Tree;
import com.formssi.frms.common.utils.MD5Utils;
import com.formssi.frms.common.utils.R;
import com.formssi.frms.common.utils.ShiroUtils;
import com.formssi.frms.system.domain.SysMenu;
import com.formssi.frms.system.domain.SysUser;
import com.formssi.frms.system.service.MenuService;

@RestController
public class LoginController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MenuService menuService;
	@GetMapping({ "/", "" })
	String welcome(Model model) {

		return "redirect:/blog";
	}

	@GetMapping({ "/index" })
	String index(Model model) {
		SysUser user = ShiroUtils.getUser();
		List<Tree<SysMenu>> menus = menuService.listMenuTree(user.getUserId());
		model.addAttribute("menus", menus);
		model.addAttribute("name", user.getName());
		model.addAttribute("username", user.getUsername());
		return "index_v1";
	}

	@GetMapping("/login")
	String login() {
		return "login";
	}

	@PostMapping("/login")
	@ResponseBody
	R ajaxLogin(String username, String password) {

		password = MD5Utils.encrypt(username, password);
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			return R.ok();
		} catch (AuthenticationException e) {
			return R.error("用户或密码错误");
		}
	}

	@GetMapping("/logout")
	String logout() {
		ShiroUtils.logout();
		return "redirect:/login";
	}

	@GetMapping("/main")
	String main() {
		return "main";
	}

}
