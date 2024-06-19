package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Review;

@Repository
public class ReviewRepostitory {
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static RowMapper<Review> REVIEW_ROW_MAPPER = (rs,i) ->{
		Review review = new Review();
		review.setId(rs.getInt("id"));
		review.setGood(rs.getInt("good"));
		review.setUserId(rs.getInt("user_id"));
		review.setItemId(rs.getInt("item_id"));
		review.setName(rs.getString("name"));
		review.setComment(rs.getString("comment"));
		return review;
	};
	
	/**
	 * レビュー一覧
	 * @param itemId
	 * @return
	 */
	public List<Review> reviewList(Integer itemId){
		String sql = "SELECT id,good,user_id,item_id,name,comment FROM reviews WHERE item_id = :itemId;";
		SqlParameterSource params = new MapSqlParameterSource("itemId",itemId);
		return template.query(sql, params, REVIEW_ROW_MAPPER);
	}
	
	/**
	 * レビュー投稿
	 * @param review
	 */
	public void reviewInsert(Review review) {
		String sql = "INSERT INTO reviews(good, user_id, item_id, name, comment)"
				+ "VALUES(:good, :userId, :itemId, :name, :comment);";
		SqlParameterSource params = new BeanPropertySqlParameterSource(review);
		template.update(sql, params);
	}
}
