package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Favorite;
import com.example.repository.FavoriteRepository;

@Transactional
@Service
public class FavoriteService {
	@Autowired
	private FavoriteRepository favoriteRepository;
	
	/**
	 * お気に入り一覧の確保
	 * @param userId
	 * @return
	 */
	public List<Favorite> favoriteList(Integer userId){
		return favoriteRepository.favoriteList(userId);
	}
	
	/**
	 * お気に入りをジャンルで絞り込み
	 * @param genre
	 * @return
	 */
	public List<Favorite> favoriteListByGenre(List<String> genres){
		return favoriteRepository.favoriteListByGenre(genres);
	}
	
	/**
	 * お気に入りに追加
	 * @param favorite
	 */
	public void insert(Favorite favorite) {
		favoriteRepository.insert(favorite);
	}
	
	/**
	 * お気に入りの削除
	 * @param favorite
	 */
	public void delete(Favorite favorite) {
		favoriteRepository.delete(favorite);
	}
	
	/**
	 * すでにお気に入りに登録しているかを確認
	 * @param favorite
	 * @return
	 */
	public boolean exists(Favorite favorite) {
		return favoriteRepository.exists(favorite);
	}
}
