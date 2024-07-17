package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Order;

@Repository
public class OrderRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static RowMapper<Order> ORDER_ROW_MAPPER = (rs,i) ->{
		Order order = new Order();
		order.setId(rs.getInt("id"));
		order.setUserId(rs.getInt("user_id"));
		order.setCartId(rs.getInt("cart_id"));
		order.setAddress(rs.getString("address"));
		order.setOrderDate(rs.getString("order_date"));
		return order;
	};
	
	/**
	 * 注文詳細情報の追加
	 * @param order
	 */
	public void orderInsert(Order order) {
		String sql = "INSERT INTO orders(user_id,cart_id,address,order_date)VALUES(:userId,:cartId,:address,:orderDate)";
		SqlParameterSource params = new BeanPropertySqlParameterSource(order);
		template.update(sql, params);
	}
}
