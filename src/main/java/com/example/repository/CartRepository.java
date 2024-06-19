package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Cart;

@Repository
public class CartRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static RowMapper<Cart> CART_ROW_MAPPER = (rs,i)->{
		Cart cart = new Cart();
		cart.setId(rs.getInt("id"));
		cart.setUserId(rs.getInt("user_id"));
		cart.setName(rs.getString("name"));
		cart.setImage(rs.getString("image"));
		cart.setPrice(rs.getInt("price"));
		return cart;
	};
	
	/**
	 * ユーザーごとのカート一覧
	 * @param userId
	 * @return
	 */
	public List<Cart> cartList(Integer userId){
		String sql = "SELECT id,user_id,name,image,price FROM carts WHERE user_id = :userId;";
		SqlParameterSource params = new MapSqlParameterSource("userId",userId);
		return template.query(sql, params, CART_ROW_MAPPER);
	}
	
	/**
	 * 商品をカートに追加
	 * @param cart
	 */
	public void cartInsert(Cart cart) {
		String sql = "INSERT INTO carts(user_id,name,image,price)VALUES(:userId,:name,:image,:price);";
		SqlParameterSource params = new BeanPropertySqlParameterSource(cart);
		template.update(sql, params);
	}
	
	/**
	 * カートから商品を削除
	 * @param id
	 */
	public void cartDelete(Integer id) {
		String sql = "DELETE FROM carts WHERE id = :id;";
		SqlParameterSource params = new MapSqlParameterSource("id",id);
		template.update(sql, params);
	}
}
