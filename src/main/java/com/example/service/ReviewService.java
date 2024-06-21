package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Review;
import com.example.domain.ReviewGood;
import com.example.repository.ReviewRepostitory;

@Transactional
@Service
public class ReviewService {
	@Autowired
	private ReviewRepostitory reviewRepostitory;
	
	public Review reviewSelect(Integer itemId, Integer userId) {
		return reviewRepostitory.reviewSelect(itemId, userId);
	}
	
	/**
	 * レビュー一覧
	 * @param itemId
	 * @return
	 */
	public List<Review> reviewList(Integer itemId, Integer userId) {
		return reviewRepostitory.reviewList(itemId, userId);
	}
	
	/**
	 * レビュー投稿
	 * @param review
	 */
	public void reviewInsert(Review review) {
		reviewRepostitory.reviewInsert(review);
	}
	
	/**
	 * レビュー更新
	 * @param review
	 */
	public void reviewUpdate(Review review) {
		reviewRepostitory.reviewUpdate(review);
	}
	
	/**
	 * レビュー一覧
	 * @return
	 */
	public List<Review> reviewAndReviewGoodList(Integer itemId, Integer userId){
		return reviewRepostitory.reviewAndReviewGoodList(itemId, userId);
	}
	
	/**
	 * goodの数の更新
	 * @param reviewId
	 */
	public void reviewUpdateByGood(Integer reviewId) {
		reviewRepostitory.reviewUpdateByGood(reviewId);
	}
	
	/**
	 * ユーザーがすでにいいねしているかを判別
	 * @param reviewId
	 * @param userId
	 * @return
	 */
	public boolean reviewGoodExists(Integer reviewId, Integer userId) {
		return reviewRepostitory.reviewGoodExists(reviewId, userId);
	}
	
	public void reviewGoodUpdateByUserId(Integer reviewId, Integer userId) {
		reviewRepostitory.reviewGoodUpdatebyUserId(reviewId, userId);
	}
	
	public void reviewGoodDelete(Integer reviewId, Integer userId) {
		reviewRepostitory.reviewGoodDelete(reviewId, userId);
	}	
}
