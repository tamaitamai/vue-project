package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.History;

@Repository
public class HistoryRepository {
	@Autowired
	NamedParameterJdbcTemplate template;
	
	public static RowMapper<History> HISTORY_ROW_MAPPER=(rs,i)->{
		History history = new History();
		history.setId(rs.getInt("id"));
		history.setUserId(rs.getInt("user_id"));
		history.setItemId(rs.getInt("item_id"));
		history.setName(rs.getString("name"));	
		history.setImage(rs.getString("image"));
		history.setPrice(rs.getInt("price"));
		history.setCount(rs.getInt("count"));
		history.setPaymentDate(rs.getString("payment_date"));
		return history;
	};
	
	/**
	 * 購入したものを履歴に追加
	 * @param history
	 */
	public void historyInsert(History history) {
		String sql = "INSERT INTO historys(user_id,item_id,name,image,price,count,payment_date) "
				+ "VALUES(:userId,:itemId,:name,:image,:price,:count,:paymentDate);";
		SqlParameterSource params = new BeanPropertySqlParameterSource(history);
		template.update(sql, params);
	}
	
	/**
	 * 各ユーザーごとの履歴一覧
	 * @param userId
	 * @return
	 */
	public List<History> historyList(Integer userId){
		String sql = "SELECT * FROM historys WHERE user_id = :userId ORDER BY id desc;";
		SqlParameterSource params = new MapSqlParameterSource("userId",userId);
		return template.query(sql, params,HISTORY_ROW_MAPPER);
	}
}
