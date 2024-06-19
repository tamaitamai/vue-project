package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Cart;
import com.example.repository.CartRepository;

@Transactional
@Service
public class CartService {
	@Autowired
	private CartRepository cartRepository;
	
	/**
	 * ユーザーごとのカート一覧
	 * @param id
	 * @return
	 */
	public List<Cart> cartList(Integer userId){
		return cartRepository.cartList(userId);
	}
	
	/**
	 * 商品をカートに追加
	 * @param cart
	 */
	public void cartInsert(Cart cart) {
		cartRepository.cartInsert(cart);
	}
	
	/**
	 * カートから商品を削除
	 * @param id
	 */
	public void cartDelete(Integer id) {
		cartRepository.cartDelete(id);
	}
}
