package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	private Integer id;
	private Integer userId;
	private Integer cartId;
	private String address;
	private String orderDate;
}
