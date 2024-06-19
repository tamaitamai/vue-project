package com.example.domain;

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
	private String name;
	private String comment;
}
