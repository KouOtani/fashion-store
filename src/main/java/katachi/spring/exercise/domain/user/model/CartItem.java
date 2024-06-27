package katachi.spring.exercise.domain.user.model;

import lombok.Data;

/**
 * カート内の各商品の情報を表すクラスです。
 */
@Data
public class CartItem {

	private Integer goodsId; // 商品ID
	private Integer quantity; // 数量
	private Integer price; // 価格
	private String goodsName; // 商品名
	private String imageUrl; // 画像URL
	private MGoods goods; // 商品情報

	/**
	 * カート内の小計金額を計算します。
	 *
	 * @return カート内の小計金額
	 */
	public int getSubtotal() {
		return price * quantity;
	}
}
