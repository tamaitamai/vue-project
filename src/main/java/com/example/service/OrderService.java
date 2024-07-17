package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.repository.OrderRepository;

@Transactional
@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;
	
	public void orderInsert(Order order) {
		orderRepository.orderInsert(order);
	}
}
