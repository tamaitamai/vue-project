package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.User;
import com.example.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserContoroller {
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;
	
	/**
	 * ユーザー情報新規登録
	 * @param user
	 */
	@PostMapping("/insert")
	public void insert(@RequestBody User user) {
		userService.userInsert(user);
	}
	
	/**
	 * ユーザーログインの確認
	 * @param user
	 */
	@PostMapping("/login")
	public User login(@RequestBody User user) {
		User userSelect = userService.userSelect(user.getMail(), user.getPassword());
		session.setAttribute("user", userSelect);
		return userSelect;
	}
	
	/**
	 * ログアウト
	 */
	@PostMapping("/logOut")
	public void logOut() {
		session.invalidate();
	}
	
	@PostMapping("/update")
	public User userUpdate(@RequestBody User user) {
		userService.userUpdate(user);
		return userService.userSelect(user.getMail(), user.getPassword());
	}
}
