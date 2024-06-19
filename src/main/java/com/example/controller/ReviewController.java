package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Item;
import com.example.domain.Review;
import com.example.service.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {
	@Autowired
	private ReviewService reviewService;
	
	/**
	 * レビュー一覧
	 * @return
	 */
	@PostMapping("")
	public List<Review> reviewList(@RequestBody Review review){
		return reviewService.reviewList(review.getItemId());
	}
	
	@PostMapping("/add")
	public void reviewAdd(@RequestBody Review review) {
		reviewService.reviewInsert(review);
	}
}
