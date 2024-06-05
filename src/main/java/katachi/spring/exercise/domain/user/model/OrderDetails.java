package katachi.spring.exercise.domain.user.model;

import lombok.Data;

/**
 * 注文の詳細情報を表現するクラスです。
 */
@Data
public class OrderDetails {

	private Integer id; // 注文詳細ID
	private Integer orderId; // 注文ID
	private Integer goodsId; // 商品ID
	private Integer price; // 商品価格
	private Integer quantity; // 注文数量
	private MGoods goods; // 注文された商品の情報

	/**
	 * 注文詳細の小計金額を計算します。
	 * @return 小計金額
	 */
	public int getSubPrice() {
		return quantity * price;
	}

}