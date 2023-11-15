package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Item;
import com.example.demo.repository.ItemRepository;

@Controller
public class ItemController {
	
	@Autowired
	ItemRepository itemRepository;
	
	// 初期表示
	@GetMapping("/items")
	public String index(
			@RequestParam(name = "sort", defaultValue = "") String sort,
			@RequestParam(name = "maxPrice", defaultValue = "") Integer maxPrice,
			@RequestParam(name = "keyword", defaultValue = "") String keyword,
			Model model) {
		// リクエストパラメータによって処理を分割
		List<Item> list = null;
		if (keyword.length() > 0) {
			// キーワードが入力されている場合
			if (maxPrice != null) {
				// 価格上限が指定されている場合
				list = itemRepository.findByNameContainingAndPriceLessThanEqual(keyword, maxPrice);
			} else {
				// 価格上限が指定されていない場合
				list = itemRepository.findByNameContaining(keyword);
			}
		} else {
			// キーワードが入力されてない場合
			if (maxPrice != null) {
				// 価格上限が指定されている場合
				list = itemRepository.findByPriceLessThanEqual(maxPrice);
			} else {
				// 価格上限が指定されていない場合
				list = itemRepository.findAll();
			}
		}
		// 商品リストをスコープに登録
		model.addAttribute("itemList", list);
		// 画面遷移
		return "items";
	}
	
}
