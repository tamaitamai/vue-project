package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.History;
import com.example.repository.HistoryRepository;

@Transactional
@Service
public class HistoryService {
	@Autowired
	private HistoryRepository historyRepository;
	
	/**
	 * 購入したものを履歴に追加
	 * @param history
	 */
	public void historyInsert(History history) {
		historyRepository.historyInsert(history);
	}
	
	/**
	 * 各ユーザーごとの履歴一覧
	 * @param userId
	 * @return
	 */
	public List<History> historyList(Integer userId){
		return historyRepository.historyList(userId);
	}
}
