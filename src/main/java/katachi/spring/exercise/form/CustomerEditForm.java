package katachi.spring.exercise.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerEditForm {

	private Integer id; // ユーザーID

	@NotBlank(groups = ValidGroup1.class)
	@Email(groups = ValidGroup2.class)
	private String eMail; // メールアドレス

	@NotBlank(groups = ValidGroup1.class)
	private String userName; // ユーザー名

	@NotBlank(groups = ValidGroup1.class)
	private String furigana; // フリガナ

	@NotBlank(groups = ValidGroup1.class)
	@Pattern(regexp = "^0\\d{9,10}$", groups = ValidGroup2.class)
	private String phoneNumber; // 電話番号

	@NotBlank(groups = ValidGroup1.class)
	@Pattern(regexp = "^\\d{3}-\\d{4}$", groups = ValidGroup2.class)
	private String postalCode; // 郵便番号

	@NotBlank(groups = ValidGroup1.class)
	private String prefectures; // 都道府県

	@NotBlank(groups = ValidGroup1.class)
	private String city; // 市区町村

	@NotBlank(groups = ValidGroup1.class)
	private String townName; // 町名

	private String others; // その他の住所情報

	@NotNull(groups = ValidGroup1.class)
	private Integer gender; // 性別

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull(groups = ValidGroup1.class)
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
