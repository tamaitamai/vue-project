package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;

@Repository
public class ItemRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static RowMapper<Item> ITEM_ROW_MAPPER = (rs,i) ->{
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setImage(rs.getString("image"));
		item.setComment(rs.getString("comment"));
		item.setPrice(rs.getInt("price"));
		item.setTotalReviewCount(rs.getInt("totalReviewCount"));
		return item;
	};
	
	private static RowMapper<Item> ITEM_DETAIL_ROW_MAPPER = (rs,i) ->{
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setImage(rs.getString("image"));
		item.setComment(rs.getString("comment"));
		item.setPrice(rs.getInt("price"));		
		return item;
	};
	
	/**
	 * 商品一覧
	 * @return
	 */
	public List<Item> itemList(){
//		String sql = "SELECT id,name,image,comment,price FROM items;";
		String sql = "SELECT i.*,r.totalReviewCount FROM items as i LEFT JOIN "
				+ "(SELECT item_id,count(*) as totalReviewCount FROM reviews GROUP BY item_id) as r "
				+ "ON i.id = r.item_id ORDER BY id;";
		return template.query(sql, ITEM_ROW_MAPPER);
	}
	
	/**
	 * 商品詳細
	 * @param id
	 * @return
	 */
	public Item itemDetail(Integer id) {
		String sql = "SELECT id,name,image,comment,price FROM items WHERE id = :id;";
		SqlParameterSource params = new MapSqlParameterSource("id",id);
		return template.queryForObject(sql, params, ITEM_DETAIL_ROW_MAPPER);
	}
	
	/**
	 * 商品検索
	 * @param name
	 * @return
	 */
	public List<Item> itemSearch(String name) {
//		String sql = "SELECT * FROM items WHERE name LIKE :name;";
		String sql = "SELECT i.*,r.totalReviewCount FROM items as i LEFT JOIN "
				+ "(SELECT item_id,count(*) as totalReviewCount FROM reviews GROUP BY item_id) as r "
				+ "ON i.id = r.item_id WHERE i.name LIKE :name ORDER BY id;";
		SqlParameterSource params = new MapSqlParameterSource("name","%"+name+"%");
		return template.query(sql, params, ITEM_ROW_MAPPER);
	}
}
