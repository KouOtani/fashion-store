package katachi.spring.exercise;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

public class UuidTest {

	@Test
	public void testGenerateOrderNumber() {
		String orderNumber = generateOrderNumber();

		// orderNumberが12桁の数字であることを確認
		assertTrue(orderNumber.matches("\\d{12}"));

		// 追加のアサーションやロジックが必要であればここに追加
		System.out.println(orderNumber);
	}

	private String generateOrderNumber() {
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
