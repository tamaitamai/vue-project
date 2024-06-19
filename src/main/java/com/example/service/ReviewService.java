package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Review;
import com.example.repository.ReviewRepostitory;

@Transactional
@Service
public class ReviewService {
	@Autowired
	private ReviewRepostitory reviewRepostitory;
	
	/**
	 * レビュー一覧
	 * @param itemId
	 * @return
	 */
	public List<Review> reviewList(Integer itemId) {
		return reviewRepostitory.reviewList(itemId);
	}
	
	/**
	 * レビュー投稿
	 * @param review
	 */
	public void reviewInsert(Review review) {
		reviewRepostitory.reviewInsert(review);
	}
}
