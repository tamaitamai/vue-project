package com.example.domain;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestOrder {
	private Integer userId;
	private Map<Integer, String> orderDateMap;
	private String address;
}
