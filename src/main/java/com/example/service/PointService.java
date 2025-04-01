package com.example.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Point;
import com.example.repository.PointRespository;

@Transactional
@Service
public class PointService {
	@Autowired
	private PointRespository pointRespository;
	
	/**
	 * ポイントを獲得
	 * @param point
	 */
	public void insert(Point point) {
		pointRespository.insert(point);
	}
	
	/**
	 * ポイントを獲得
	 * @param point
	 */
	public Integer userPoint(Integer userId) {
		return pointRespository.userPoint(userId);
	}
	
	/**
	 * ポイント一覧
	 * @param userId
	 * @return
	 */
	public List<Point> pointList(Integer userId){
		return pointRespository.pointList(userId);
	}
}
