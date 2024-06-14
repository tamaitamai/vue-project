package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.repository.UserRepository;

@Transactional
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * ユーザーを新規登録
	 * @param user
	 */
	public void userInsert(User user) {
		userRepository.userInsert(user);
	}
	
	public User userSelect(String mail,String password) {
		return userRepository.userSelect(mail, password);
	}
}
