package com.example.domain;



import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point {
	private Integer id;
	private Integer userId;
	private Integer itemId;
	private Integer point;
	private Timestamp createDate;
}
