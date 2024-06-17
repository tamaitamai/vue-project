package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.User;

@Repository
public class UserRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static RowMapper<User> USER_ROW_MAPPER = (rs,i) ->{
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));;
		user.setMail(rs.getString("mail"));
		user.setPassword(rs.getString("password"));
		return user;
	};
	
	/**
	 * ユーザーを新規登録
	 * @param user
	 */
	public void userInsert(User user) {
		String sql = "INSERT INTO users(name,mail,password)VALUES(:name,:mail,:password);";
		SqlParameterSource params = new BeanPropertySqlParameterSource(user);
		template.update(sql, params);
	}
	
	/**
	 * 特定のユーザー情報の取り出し
	 * @param mail
	 * @param password
	 * @return
	 */
	public User userSelect(String mail, String password) {
		String sql = "SELECT id,name,mail,password FROM users WHERE mail = :mail AND password = :password;";
		SqlParameterSource params = new MapSqlParameterSource("mail",mail).addValue("password", password);
		return template.queryForObject(sql, params, USER_ROW_MAPPER);
	}
}
