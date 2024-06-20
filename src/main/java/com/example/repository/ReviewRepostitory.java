package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Review;
import com.example.domain.ReviewGood;

@Repository
public class ReviewRepostitory {
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static RowMapper<Review> REVIEW_SELECT_ROW_MAPPER = (rs,i) ->{
		Review review = new Review();
		review.setId(rs.getInt("id"));
		review.setGood(rs.getInt("good"));
		review.setStar(rs.getInt("star"));
		review.setUserId(rs.getInt("user_id"));
		review.setItemId(rs.getInt("item_id"));
		review.setName(rs.getString("name"));
		review.setComment(rs.getString("comment"));
		return review;
	};
	
	private static RowMapper<Review> REVIEW_ROW_MAPPER = (rs,i) ->{
		Review review = new Review();
		review.setId(rs.getInt("id"));
		review.setGood(rs.getInt("good"));
		review.setStar(rs.getInt("star"));
		review.setUserId(rs.getInt("user_id"));
		review.setItemId(rs.getInt("item_id"));
		review.setName(rs.getString("name"));
		review.setComment(rs.getString("comment"));
		review.setGoodTotal(rs.getInt("good_total"));
		
		ReviewGood reviewGood = new ReviewGood();
		reviewGood.setUserId(rs.getInt("good_user_id"));
		review.setReviewGood(reviewGood);
		return review;
	};
	
	private static final ResultSetExtractor<List<Review>> REVIEW_GOOD_RESULTSET = (rs) -> {
		List<Review> reviewList = new ArrayList<>();
		List<ReviewGood> reviewGoodList = null;
		int beforeIdNum = 0;
		
		while (rs.next()) {
			int nowIdNum = rs.getInt("id");

			if (nowIdNum != beforeIdNum) {
				Review review = new Review();
				review.setId(nowIdNum);
				review.setId(rs.getInt("id"));
				review.setGood(rs.getInt("good"));
				review.setStar(rs.getInt("star"));
				review.setUserId(rs.getInt("user_id"));
				review.setItemId(rs.getInt("item_id"));
				review.setName(rs.getString("name"));
				review.setComment(rs.getString("comment"));
				
				reviewGoodList = new ArrayList<ReviewGood>();
				review.setReviewGoods(reviewGoodList);
				reviewList.add(review);
			}

			if (rs.getInt("rg_id") != 0) {
				ReviewGood reviewGood = new ReviewGood();
				reviewGood.setId(rs.getInt("rg_id"));
				reviewGood.setUserId(rs.getInt("good_user_id"));
				reviewGoodList.add(reviewGood);
			}
			beforeIdNum = nowIdNum;
		}
		return reviewList;
	};
	
	public Review reviewSelect(Integer itemId, Integer userId) {
		String sql = "SELECT * FROM reviews WHERE item_id = :itemId AND user_id = :userId;";
		SqlParameterSource params = new MapSqlParameterSource("itemId",itemId).addValue("userId", userId);
		return template.queryForObject(sql, params, REVIEW_SELECT_ROW_MAPPER);
	}

	/**
	 * レビュー一覧
	 * @param itemId
	 * @return
	 */
	public List<Review> reviewList(Integer itemId, Integer userId){
//		String sql = "SELECT r.*, rg.user_id as good_user_id "
//				+ "FROM reviews as r LEFT JOIN (SELECT * FROM review_goods WHERE user_id = :userId) as rg "
//				+ "ON r.id = rg.review_id WHERE r.item_id = :itemId order by r.id;";
		String sql = "SELECT r.*, rg.user_id as good_user_id , rt.good_total FROM reviews as r "
				+ "LEFT JOIN (SELECT * FROM review_goods WHERE user_id = :userId) as rg  ON r.id = rg.review_id "
				+ "LEFT JOIN (SELECT r.id, count(*) as good_total "
				+ "FROM review_goods as rg LEFT JOIN reviews as r "
				+ "ON rg.review_id = r.id GROUP BY r.id order by r.id) as rt "
				+ "ON r.id = rt.id WHERE r.item_id = :itemId order by r.id;";
		SqlParameterSource params = new MapSqlParameterSource("itemId",itemId).addValue("userId", userId);
		return template.query(sql, params, REVIEW_ROW_MAPPER);
	}
	
	/**
	 * レビュー一覧
	 * @return
	 */
	public List<Review> reviewAndReviewGoodList(Integer itemId, Integer userId) {
		String sql = "SELECT r.*, rg.id as rg_id, rg.user_id as good_user_id "
				+ "FROM reviews as r LEFT JOIN review_goods as rg "
				+ "ON r.id = rg.review_id WHERE item_id = :itemId";
		SqlParameterSource params = new MapSqlParameterSource("itemId", itemId).addValue("userId", userId);
		return template.query(sql, params, REVIEW_GOOD_RESULTSET);
	}
	
	/**
	 * レビュー投稿
	 * @param review
	 */
	public void reviewInsert(Review review) {
		String sql = "INSERT INTO reviews(good, star, user_id, item_id, name, comment)"
				+ "VALUES(0, :star, :userId, :itemId, :name, :comment);";
		SqlParameterSource params = new BeanPropertySqlParameterSource(review);
		template.update(sql, params);
	}
	
	/**
	 * goodの数の更新
	 * @param reviewId
	 */
	public void reviewUpdateByGood(Integer reviewId) {
		String sql = "UPDATE reviews SET good = (SELECT good FROM reviews WHERE id = :reviewId) + 1 WHERE id = :reviewId;";
		SqlParameterSource params = new MapSqlParameterSource("reviewId", reviewId);
		template.update(sql, params);
	}
	
	/**
	 * ユーザーがすでにいいねしているかを判別
	 * @param reviewId
	 * @param userId
	 * @return
	 */
	public boolean reviewGoodExists(Integer reviewId, Integer userId) {
		String sql = "SELECT EXISTS(SELECT * FROM review_goods WHERE review_id = :reviewId AND user_id = :userId);";
		SqlParameterSource params = new MapSqlParameterSource("reviewId",reviewId).addValue("userId", userId);
		return template.queryForObject(sql, params, boolean.class);
	}
	
	/**
	 * ユーザーがいいねしたレビューを記録
	 * @param reviewId
	 * @param userId
	 */
	public void reviewGoodUpdatebyUserId(Integer reviewId, Integer userId) {
		String sql = "INSERT INTO review_goods(review_id, user_id) SELECT :reviewId, :userId "
				+ "WHERE NOT EXISTS ( "
				+ "SELECT FROM review_goods "
				+ "WHERE review_id = :reviewId AND user_id = :userId);";
		SqlParameterSource params = new MapSqlParameterSource("reviewId",reviewId).addValue("userId", userId);
		template.update(sql, params);
	}
	
	/**
	 * サイドいいねをクリックした際に削除
	 * @param reviewId
	 * @param userId
	 */
	public void reviewGoodDelete(Integer reviewId, Integer userId) {
		String sql = "DELETE FROM review_goods WHERE review_id = :reviewId AND user_id = :userId;";
		SqlParameterSource params = new MapSqlParameterSource("reviewId",reviewId).addValue("userId", userId);
		template.update(sql, params);
	}
}
