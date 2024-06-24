package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Cart;
import com.example.domain.User;
import com.example.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;
	
	/**
	 * カート一覧の表示
	 * @param user
	 * @return
	 */
	@PostMapping("")
	public List<Cart> cartList(@RequestBody User user){
		return cartService.cartList(user.getId());
	}
	
	/**
	 * カートに商品を追加
	 * @param cart
	 * @return
	 */
	@PostMapping("/add")
	public List<Cart> cartInsert(@RequestBody Cart cart) {
		cartService.cartInsert(cart);
		return cartService.cartList(cart.getUserId());
	}
	
	/**
	 * カート内の商品を削除
	 * @param cart
	 * @return
	 */
	@PostMapping("/delete")
	public List<Cart> cartDelete(@RequestBody Cart cart){
		cartService.cartDelete(cart.getId());
		return cartService.cartList(cart.getUserId());
	}
	
	@PostMapping("/countUpdate")
	public List<Cart> countUpdate(@RequestBody Cart cart) {
		cartService.cartCountUpdate(cart.getCount(), cart.getId());
		return cartService.cartList(cart.getUserId());
	}
}
