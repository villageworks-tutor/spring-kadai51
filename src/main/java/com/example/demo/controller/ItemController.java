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
		// sortキーによって処理を分割
		if ("priceAsc".equals(sort)) {
			// 指定された並び順の商品リストを取得
			List<Item> list = itemRepository.findAllByOrderByPriceAsc();
			// スコープに商品リストを登録
			model.addAttribute("itemList", list);
		} else if (maxPrice != null) {
			// 指定された金額以下の商品リストを取得
			List<Item> list = itemRepository.findByPriceLessThanEqual(maxPrice);
			// スコープに商品リストを登録
			model.addAttribute("itemList", list);
			model.addAttribute("maxPrice", maxPrice);
		} else if (keyword != null) {
			// 指定されたキーワードの部分一致検索を実行
			List<Item> list = itemRepository.findByNameContaining(keyword);
			// スコープに商品リストを登録
			model.addAttribute("itemList", list);
			model.addAttribute("keyword", keyword);
		} else {
			// すべての商品リストを取得
			List<Item> list = itemRepository.findAll();
			// スコープに商品リストを登録
			model.addAttribute("itemList", list);
		}
		// 画面遷移
		return "items";
	}
	
}
