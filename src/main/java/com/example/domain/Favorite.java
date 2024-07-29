package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
	private Integer userId;
	private Integer itemId;
	private String name;
	private String image;
	private Integer price;
	private String genre;
}
