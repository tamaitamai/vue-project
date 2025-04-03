package com.example.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Favorite;

@Repository
public class FavoriteRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static RowMapper<Favorite> FAVORITE_ROW_MAPPER=(rs,i)->{
		Favorite favorite = new Favorite();
		favorite.setItemId(rs.getInt("id"));
		favorite.setUserId(rs.getInt("user_id"));
		favorite.setItemId(rs.getInt("item_id"));
		favorite.setName(rs.getString("name"));
		favorite.setImage(rs.getString("image"));
		favorite.setPrice(rs.getInt("price"));
		favorite.setGenre(rs.getString("genre"));
		return favorite;
	};
	
	/**
	 * お気に入り一覧の確保
	 * @param userId
	 * @return
	 */
	public List<Favorite> favoriteList(Integer userId){
		String sql = "SELECT * FROM favorites WHERE user_id = :userId;";
		SqlParameterSource params = new MapSqlParameterSource("userId",userId);
		return template.query(sql, params, FAVORITE_ROW_MAPPER);
	}
	
	/**
	 * お気に入りをジャンルで絞り込み
	 * @param genre
	 * @return
	 */
	public List<Favorite> favoriteListByGenre(Integer userId, List<String> genres){
//		String sql = "SELECT * FROM　favorites WHERE genre = :genre";
//		SqlParameterSource params = new MapSqlParameterSource("genre",genre);
//		return template.query(sql, params, FAVORITE_ROW_MAPPER);
		
        // ジャンルリストが空の場合は空のリストを返す
        if (genres == null || genres.isEmpty()) {
            return Collections.emptyList();
        }
        
        // プレースホルダのリストを生成
        String placeholders = genres.stream()
                                    .map(genre -> ":genre_" + genres.indexOf(genre))
                                    .collect(Collectors.joining(" OR genre = "));
        
        // 動的に生成されたSQL
        String sql = "SELECT * FROM favorites WHERE user_id = :userId AND (genre = " + placeholders + ")";
        
        // パラメータマップの生成
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        for (int i = 0; i < genres.size(); i++) {
            params.addValue("genre_" + i, genres.get(i));
        }
        
        // クエリの実行
        return template.query(sql, params, FAVORITE_ROW_MAPPER);
	}
	
	/**
	 * お気に入りに追加
	 * @param favorite
	 */
	public void insert(Favorite favorite) {
		String sql = "INSERT INTO favorites(user_id,item_id,name,image,price,genre)VALUES(:userId,:itemId,:name,:image,:price,:genre);";
		SqlParameterSource params = new BeanPropertySqlParameterSource(favorite);
		template.update(sql, params);
	}
	
	/**
	 * お気に入りの削除
	 * @param favorite
	 */
	public void delete(Favorite favorite) {
		String sql = "DELETE FROM favorites WHERE item_id = :itemId AND user_id = :userId;";
		SqlParameterSource params = new BeanPropertySqlParameterSource(favorite);
		template.update(sql, params);
	}
	
	/**
	 * すでにお気に入りに登録しているかを確認
	 * @param favorite
	 * @return
	 */
	public boolean exists(Favorite favorite) {
		String sql = "SELECT EXISTS(SELECT * FROM favorites WHERE item_id = :itemId AND user_id = :userId);";
		SqlParameterSource params = new BeanPropertySqlParameterSource(favorite);
		return template.queryForObject(sql, params, boolean.class);
	}
}
