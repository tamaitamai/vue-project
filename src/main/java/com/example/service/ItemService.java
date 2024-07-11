package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Item;
import com.example.repository.ItemRepository;

@Transactional
@Service
public class ItemService {
	@Autowired
	private ItemRepository itemRepository;
	
	/**
	 * 商品一覧
	 * @return
	 */
	public List<Item> itemList(){
		return itemRepository.itemList();
	}
	
	/**
	 * 商品詳細
	 * @param id
	 * @return
	 */
	public Item itemDetail(Integer id) {
		return itemRepository.itemDetail(id);
	}
	
	public List<Item> itemSearch(String name) {
		return itemRepository.itemSearch(name);
	}
}
