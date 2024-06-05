package katachi.spring.exercise.domain.user.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 注文情報を表現するクラスです。
 */
@Data
public class Order {

	private Integer id; // 注文ID
	private Integer userId; // 注文者のユーザーID
	private Date orderDate; // 注文日時
	private String orderNumber;//注文完了時に発行する注文ID

	private MUser user; // 注文者のユーザー情報
	private DeliveryAddress deliveryAddress; // 配送先情報
	private List<OrderDetails> orderDetailsList; // 注文詳細情報のリスト

	/**
	 * 注文の合計金額を計算します。
	 * @return 合計金額
	 */
	public int getTotalPrice() {
		int totalPrice = 0;
		for (OrderDetails orderDetail : orderDetailsList) {
			totalPrice += orderDetail.getPrice() * orderDetail.getQuantity();
		}
		return totalPrice;
	}

}
