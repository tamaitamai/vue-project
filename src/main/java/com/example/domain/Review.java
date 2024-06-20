package com.example.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
	private Integer id;
	private Integer userId;
	private Integer itemId;
	private Integer good;
	private Integer star;
	private String name;
	private String comment;
	private Integer goodTotal;
	private User user;
	private ReviewGood reviewGood;
	private List<ReviewGood> reviewGoods = new ArrayList<>();
}
