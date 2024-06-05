package katachi.spring.exercise.domain.user.model;

import lombok.Data;

/**
 * 配送先住所情報を表すクラスです。
 */
@Data
public class DeliveryAddress {

	private Integer id; // 配送先住所ID
	private Integer orderId; // 注文ID
	private String postalCode; // 郵便番号
	private String prefectures; // 都道府県
	private String city; // 市区町村
	private String townName; // 町名
	private String others; // その他の住所情報

}
