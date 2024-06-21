package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
	private Integer id;
	private Integer userId;
	private String name;
	private String image;
	private Integer price;
	private Integer count;
}
