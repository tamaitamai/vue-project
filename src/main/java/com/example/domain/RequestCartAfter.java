package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestCartAfter {
	private Integer id;
	private Integer userId;
	private boolean afterExists;
}
