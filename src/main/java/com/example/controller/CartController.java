package com.example.controller;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Cart;
import com.example.domain.History;
import com.example.domain.Order;
import com.example.domain.Point;
import com.example.domain.RequestCartAfter;
import com.example.domain.RequestOrder;
import com.example.domain.User;
import com.example.service.CartService;
import com.example.service.HistoryService;
import com.example.service.OrderService;
import com.example.service.PointService;

@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PointService pointService;
	
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
	
	/**
	 * あとで買うに追加
	 * @param cart
	 * @return
	 */
	@PostMapping("/after")
	public List<Cart> cartAfterList(@RequestBody RequestCartAfter request){
		cartService.cartUpdateByAfter(request.getId(),request.isAfterExists());
		return cartService.cartList(request.getUserId());
	}
	
	/**
	 * 購入する商品数の更新
	 * @param cart
	 * @return
	 */
	@PostMapping("/countUpdate")
	public List<Cart> countUpdate(@RequestBody Cart cart) {
		cartService.cartCountUpdate(cart.getCount(), cart.getId());
		return cartService.cartList(cart.getUserId());
	}
	
	/**
	 * 商品を購入する
	 * @param cart
	 */
	@PostMapping("/payment")
	public void payment(@RequestBody RequestOrder requestOrder) {
		Integer userId = requestOrder.getUserId();
		Map<Integer, String> orderMap = requestOrder.getOrderDateMap();
		
		List<Cart> cartList = cartService.cartList(userId);
		cartService.cartDeleteByPayment(userId);
		
		History history = new History();
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		String formatDate = format.format(date);
		history.setPaymentDate(formatDate);
		
		Order order = new Order();		
		order.setUserId(userId);
		order.setAddress(requestOrder.getAddress());

		Timestamp timestamp = new Timestamp(date.getTime());
		if(requestOrder.getPoint() != 0) {
			Point usePoint = new Point();
			usePoint.setUserId(userId);
			usePoint.setPoint(requestOrder.getPoint()*-1);
			usePoint.setCreateDate(timestamp);
			pointService.insert(usePoint);			
		}
		
		for(Cart addCart : cartList) {
			if(addCart.getAfterFlg() == 0) {
				BeanUtils.copyProperties(addCart, history);
				historyService.historyInsert(history);
				order.setCartId(addCart.getId());
				order.setOrderDate(orderMap.get(addCart.getId()));
				orderService.orderInsert(order);	
				
				Point point = new Point();
				point.setUserId(userId);
				point.setItemId(addCart.getItemId());
				point.setPoint(addCart.getPrice()/100);
				point.setCreateDate(timestamp);
				pointService.insert(point);
			}
		}
	}
}
