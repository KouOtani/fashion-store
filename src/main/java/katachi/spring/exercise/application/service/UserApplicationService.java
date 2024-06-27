package katachi.spring.exercise.application.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import katachi.spring.exercise.domain.user.model.ExtendedUser;

/**
 * ユーザーアプリケーションのサービスを提供するクラスです。
 */
@Service
public class UserApplicationService {

	@Autowired
	private MessageSource messageSource;

	/**
	 * ロケールに応じた性別のMapを生成します。
	 *
	 * @param locale ロケール
	 * @return 性別のMap（キー：性別の表示名、値：性別のID）
	 */
	public Map<String, Integer> getGenderMap(Locale locale) {
		Map<String, Integer> genderMap = new LinkedHashMap<>();
		String male = messageSource.getMessage("male", null, Locale.JAPAN);
		String female = messageSource.getMessage("female", null, Locale.JAPAN);
		String others = messageSource.getMessage("other", null, Locale.JAPAN);
		genderMap.put(male, 1);
		genderMap.put(female, 2);
		genderMap.put(others, 3);

		return genderMap;
	}

	/**
	 * 日本の都道府県のリストを生成します。
	 *
	 * @return 都道府県のリスト
	 */
	public List<String> getPrefecturesList() {
		List<String> prefecturesList = new ArrayList<>();
		// 都道府県のリストを追加
		prefecturesList.add("北海道");
		prefecturesList.add("青森県");
		prefecturesList.add("岩手県");
		prefecturesList.add("宮城県");
		prefecturesList.add("秋田県");
		prefecturesList.add("山形県");
		prefecturesList.add("福島県");
		prefecturesList.add("茨城県");
		prefecturesList.add("栃木県");
		prefecturesList.add("群馬県");
		prefecturesList.add("埼玉県");
		prefecturesList.add("千葉県");
		prefecturesList.add("東京都");
		prefecturesList.add("神奈川県");
		prefecturesList.add("新潟県");
		prefecturesList.add("富山県");
		prefecturesList.add("石川県");
		prefecturesList.add("福井県");
		prefecturesList.add("山梨県");
		prefecturesList.add("長野県");
		prefecturesList.add("岐阜県");
		prefecturesList.add("静岡県");
		prefecturesList.add("愛知県");
		prefecturesList.add("三重県");
		prefecturesList.add("滋賀県");
		prefecturesList.add("京都府");
		prefecturesList.add("大阪府");
		prefecturesList.add("兵庫県");
		prefecturesList.add("奈良県");
		prefecturesList.add("和歌山県");
		prefecturesList.add("鳥取県");
		prefecturesList.add("島根県");
		prefecturesList.add("岡山県");
		prefecturesList.add("広島県");
		prefecturesList.add("山口県");
		prefecturesList.add("徳島県");
		prefecturesList.add("香川県");
		prefecturesList.add("愛媛県");
		prefecturesList.add("高知県");
		prefecturesList.add("福岡県");
		prefecturesList.add("佐賀県");
		prefecturesList.add("長崎県");
		prefecturesList.add("熊本県");
		prefecturesList.add("大分県");
		prefecturesList.add("宮崎県");
		prefecturesList.add("鹿児島県");
		prefecturesList.add("沖縄県");

		return prefecturesList;
	}

	/**
	 * 現在の認証されたユーザーの詳細を取得します。
	 *
	 * このメソッドは、Spring SecurityのSecurityContextから現在の認証情報を取得し、
	 * 認証されたユーザーの詳細（ExtendedUserオブジェクト）を返します。
	 *
	 * @return 現在認証されているユーザーの詳細（ExtendedUserオブジェクト）
	 * @throws ClassCastException 認証されたユーザーがExtendedUser型にキャストできない場合
	 */
	public ExtendedUser getCurrentUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (ExtendedUser) authentication.getPrincipal();
	}

	/**
	 * 注文番号を生成するメソッド。
	 *
	 * @return 生成された注文番号
	 */
	public String generateOrderNumber() {
		// UUIDを生成
		UUID uuid = UUID.randomUUID();
		// UUIDを文字列に変換し、ハイフンを削除
		String uuidString = uuid.toString().replaceAll("-", "");
		// 数字のみに変換
		String numericString = uuidString.replaceAll("[^0-9]", "");
		// 先頭から12桁を取得（もし12桁未満の場合は0で埋める）
		String orderNumber = String.format("%012d", Long.parseLong(numericString.substring(0, Math.min(numericString.length(), 12))));

		return orderNumber;
	}

}
