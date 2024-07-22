package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Item;
import com.example.service.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@GetMapping("")
	public List<Item> itemList(){
		List<Item> itemList = itemService.itemList();
		return itemService.itemList();
	}
	
	@PostMapping("/detail")
	public Item itemDetail(@RequestBody Item item) {
		return itemService.itemDetail(item.getId());
	}
	
	@PostMapping("/search")
	public List<Item> itemSearch(@RequestBody Item item){
		return itemService.itemSearch(item.getName());
	}
	
	@PostMapping("/genre")
	public List<Item> itemByGenre(@RequestBody Item item){
		return itemService.itemByGenre(item.getGenre());
	}
}
