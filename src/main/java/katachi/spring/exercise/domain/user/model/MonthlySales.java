package katachi.spring.exercise.domain.user.model;

import lombok.Data;

/**
 * 月別の売り上げを表現するクラスです。
 */
@Data
public class MonthlySales {

	private Integer id; // 月別売り上げID
	private Integer month; // 月（例：'01', '02', ..., '12'）
	private Integer sales; // 売り上げ
	private Integer year; // 年
}
