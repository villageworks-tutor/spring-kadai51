package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {

	// 価格の安い順（昇順）に商品を取得する
	// SELECT * FROM items ORDER BY price
	List<Item> findAllByOrderByPriceAsc();

	// 上限金額以下の価格の商品を取得する
	// SELECT * FROM items WHERE price <= ?
	List<Item> findByPriceLessThanEqual(Integer maxPrice);

	// nameフィールドで部分一致検索を実行する
	// SELECT * FROM items WHERE name LIKE ?
	List<Item> findByNameContaining(String keyword);

	// キーワードと価格の組み合わせ検索
	// SELECT * FROM items WHERE name LIKE ? AND price <= ?
	List<Item> findByNameContainingAndPriceLessThanEqual(String keyword, Integer maxPrice);


}
