package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.History;
import com.example.service.HistoryService;

@RestController
@RequestMapping("/history")
public class HistoryController {
	@Autowired
	private HistoryService historyService;
	
	@PostMapping("")
	public List<History> historyList(@RequestBody History history){
		return historyService.historyList(history.getUserId());
	}
}
