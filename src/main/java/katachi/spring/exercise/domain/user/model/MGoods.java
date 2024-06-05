package katachi.spring.exercise.domain.user.model;

import lombok.Data;

/**
 * 商品情報を表すクラスです。
 */
@Data
public class MGoods {

	private Integer id; // 商品ID
	private String goodsName; // 商品名
	private String description; // 商品の説明
	private Integer price; // 商品の価格
	private String imageUrl; // 商品の画像URL

}
