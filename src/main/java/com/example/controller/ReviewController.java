package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Item;
import com.example.domain.Review;
import com.example.domain.ReviewGood;
import com.example.domain.User;
import com.example.service.ReviewService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/review")
public class ReviewController {
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private HttpSession session;
	
	/**
	 * レビュー一覧
	 * @return
	 */
	@PostMapping("")
	public List<Review> reviewList(@RequestBody Review review){
		List<Review> reivewList = reviewService.reviewList(review.getItemId(), review.getUserId());
		return reviewService.reviewList(review.getItemId(), review.getUserId());
	}
	
	@PostMapping("/select")
	public Review reviewSelect(@RequestBody Review review) {
		return reviewService.reviewSelect(review.getItemId(), review.getUserId());
	}
	
	/**
	 * レビューの投稿
	 * @param review
	 */
	@PostMapping("/add")
	public void reviewAdd(@RequestBody Review review) {
		if(review.isEditFlag()) {
			reviewService.reviewUpdate(review);
		}else {
			reviewService.reviewInsert(review);	
		}
	}
	
	/**
	 * goodの数の更新
	 * @param review
	 * @return
	 */
	@PostMapping("/goodUpdate")
	public List<Review> reviewGoodUpdate(@RequestBody Review review) {
		Boolean exists = reviewService.reviewGoodExists(review.getId(), review.getUserId());
		reviewService.reviewGoodUpdateByUserId(review.getId(), review.getUserId());
		if(!exists) {
			reviewService.reviewUpdateByGood(review.getId());	
		}else {
			reviewService.reviewGoodDelete(review.getId(), review.getUserId());
		}
		return reviewService.reviewList(review.getItemId(), review.getUserId());
	}
}
