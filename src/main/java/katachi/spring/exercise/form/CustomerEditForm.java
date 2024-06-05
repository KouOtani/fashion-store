package katachi.spring.exercise.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CustomerEditForm {

	private Integer id; // ユーザーID
	private String eMail; // メールアドレス
	private String userName; // ユーザー名
	private String furigana; // フリガナ
	private String phoneNumber; // 電話番号
	private String postalCode; // 郵便番号
	private String prefectures; // 都道府県
	private String city; // 市区町村
	private String townName; // 町名
	private String others; // その他の住所情報
	private Integer gender; // 性別

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date birthday; // 誕生日

	/**
	 * 配送先住所を結合した文字列を返します。
	 *
	 * @return 配送先住所
	 */
	public String getShippingAddress() {
		return prefectures + city + townName + (others != null ? others : "");
	}

}
