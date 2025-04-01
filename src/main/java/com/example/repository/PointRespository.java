package com.example.repository;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Point;

@Repository
public class PointRespository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static RowMapper<Point> POINT_ROW_MAPPER = (rs,i) ->{
		Point point = new Point();
		point.setId(rs.getInt("id"));
		point.setUserId(rs.getInt("user_id"));
		point.setItemId(rs.getInt("item_id"));
		point.setPoint(rs.getInt("point"));
		point.setCreateDate(rs.getString("create_date"));
		return point;
	};
	
	/**
	 * ポイントを獲得
	 * @param point
	 */
	public void insert(Point point) {
		String sql = "INSERT INTO points(user_id,item_id,point,create_date)VALUES(:userId,:itemId,:point,:createDate);";
		SqlParameterSource params = new BeanPropertySqlParameterSource(point);
		template.update(sql, params);
	}
	
	/**
	 * ユーザーが獲得しているポイント合計
	 * @param userId
	 * @return
	 */
	public Integer userPoint(Integer userId) {
		String sqlExists = "SELECT EXISTS(SELECT * FROM points WHERE user_id = :userId)";
		SqlParameterSource params = new MapSqlParameterSource("userId",userId);
		boolean exists = template.queryForObject(sqlExists, params, boolean.class);
		if(exists) {
			String sql = "SELECT SUM(point) as count_sum FROM points WHERE user_id = :userId GROUP BY user_id;";			
			return template.queryForObject(sql, params, Integer.class);
		}else {
			return 0;
		}
	}
	
	/**
	 * ポイント一覧
	 * @param userId
	 * @return
	 */
	public List<Point> pointList(Integer userId){
		String sql = "SELECT * FROM points WHERE user_id = :userId;";
		SqlParameterSource params = new MapSqlParameterSource("userId",userId);
		return template.query(sql, params, POINT_ROW_MAPPER);
	}
}
