package katachi.spring.exercise.domain.user.model;

import java.util.Date;

import lombok.Data;

/**
 * ユーザー情報を表すエンティティクラスです。
 */
@Data
public class MUser {

	private Integer id; // ユーザーID
	private String eMail; // メールアドレス
	private String password; // パスワード
	private String userName; // ユーザー名
	private String furigana; // フリガナ
	private String postalCode; // 郵便番号
	private String prefectures; // 都道府県
	private String city; // 市区町村
	private String townName; // 町名
	private String others; // その他の住所情報
	private String phoneNumber; // 電話番号
	private Integer gender; // 性別
	private Date birthday; // 誕生日
	private String role; // ユーザーの権限
	private Date createdAt;

	/**
	 * 配送先住所を結合した文字列を返します。
	 *
	 * @return 配送先住所
	 */
	public String getShippingAddress() {
		return prefectures + city + townName + (others != null ? others : "");
	}
}
