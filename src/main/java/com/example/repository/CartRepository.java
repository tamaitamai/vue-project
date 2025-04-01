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
		cart.setItemId(rs.getInt("item_id"));
		cart.setName(rs.getString("name"));
		cart.setImage(rs.getString("image"));
		cart.setPrice(rs.getInt("price"));
		cart.setCount(rs.getInt("count"));
		cart.setAfterFlg(rs.getInt("after_flg"));
		cart.setDelFlg(rs.getInt("del_flg"));
		return cart;
	};
	
	/**
	 * ユーザーごとのカート一覧
	 * @param userId
	 * @return
	 */
	public List<Cart> cartList(Integer userId){
		String sql = "SELECT * FROM carts "
				+ "WHERE user_id = :userId AND del_flg = 0 ORDER BY id;";
		SqlParameterSource params = new MapSqlParameterSource("userId",userId);
		return template.query(sql, params, CART_ROW_MAPPER);
	}
	
	/**
	 * 商品をカートに追加
	 * @param cart
	 */
	public void cartInsert(Cart cart) {
		String sql = "INSERT INTO carts(user_id,item_id,name,image,price,count,after_flg,del_flg)"
				+ "VALUES(:userId,:itemId,:name,:image,:price,:count,0,0);";
		SqlParameterSource params = new BeanPropertySqlParameterSource(cart);
		template.update(sql, params);
	}
	
	/**
	 * カートから商品を削除
	 * @param id
	 */
	public void cartDelete(Integer id) {
//		String sql = "DELETE FROM carts WHERE id = :id;";
		String sql = "UPDATE carts SET del_flg = 1 WHERE id = :id;";
		SqlParameterSource params = new MapSqlParameterSource("id",id);
		template.update(sql, params);
	}
	
	/**
	 * あとで買うに追加
	 * @param id
	 */
	public void cartUpdateByAfter(Integer id,boolean exists) {
		String sql = "";
		if(exists) {
			sql = "UPDATE carts SET after_flg = 0 WHERE id = :id;";	
		}else {
			sql = "UPDATE carts SET after_flg = 1 WHERE id = :id;";
		}
		SqlParameterSource params = new MapSqlParameterSource("id",id);
		template.update(sql, params);
	}
	
	/**
	 * カートの商品数を更新
	 * @param count
	 * @param id
	 */
	public void cartCountUpdate(Integer count, Integer id) {
		String sql = "UPDATE carts SET count = :count WHERE id = :id;";
		SqlParameterSource params = new MapSqlParameterSource("count", count).addValue("id", id);
		template.update(sql, params);
	}
	
	/**
	 * 支払い完了時に購入したものをカートからなくす
	 * @param userId
	 */
	public void cartDeleteByPayment(Integer userId) {
		String sql = "UPDATE carts SET del_flg = 1 WHERE user_id = :userId AND after_flg = 0;";
		SqlParameterSource params = new MapSqlParameterSource("userId",userId);
		template.update(sql, params);
	}
}
