package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Point;
import com.example.service.PointService;

@RestController
@RequestMapping("point")
public class PointController {
	@Autowired
	private PointService pointService;
	
	@PostMapping("")
	public Integer userPoint(@RequestBody Point point){
		return pointService.userPoint(point.getUserId());
	}
	
	@PostMapping("/list")
	public List<Point> pointList(@RequestBody Point point){
		return pointService.pointList(point.getUserId());
	}
}
