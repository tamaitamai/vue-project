package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Favorite;
import com.example.domain.RequestFavoriteGenre;
import com.example.service.FavoriteService;

@RestController
@RequestMapping("favorite")
public class FavoriteController {
	@Autowired
	private FavoriteService favoriteService;
	
	/**
	 * お気に入り一覧の確保
	 * @param favorite
	 * @return
	 */
	@PostMapping("")
	public List<Favorite> favoriteList(@RequestBody Favorite favorite){
		return favoriteService.favoriteList(favorite.getUserId());
	}
	
	/**
	 * お気に入りに追加
	 * @param favorite
	 */
	@PostMapping("/add")
	public void add(@RequestBody Favorite favorite) {
		if(favoriteService.exists(favorite)) {
			favoriteService.delete(favorite);	
		}else {
			favoriteService.insert(favorite);
		}
	}
	
	/**
	 * お気に入りがすでに登録されているか確認
	 * @param favorite
	 * @return
	 */
	@PostMapping("/exists")
	public boolean exists(@RequestBody Favorite favorite) {
		return favoriteService.exists(favorite);
	}
	
	@PostMapping("/genre")
	public List<Favorite> favoriteListByGenre(@RequestBody RequestFavoriteGenre requestFavoriteGenre){
		List<Favorite> favoriteList = favoriteService.favoriteListByGenre(requestFavoriteGenre.getUserId(), requestFavoriteGenre.getGenreList());
		return favoriteList;
	}
}
