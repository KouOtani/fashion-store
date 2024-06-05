package katachi.spring.exercise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import katachi.spring.exercise.domain.user.model.Order;
import katachi.spring.exercise.domain.user.service.ShoppingService;

/**
 * 注文履歴に関連するリクエストを処理するコントローラークラスです。
 */
@Controller
@RequestMapping("/account")
public class OrderHistoryController {

	@Autowired
	private ShoppingService shoppingService;

	@Autowired
	private HttpSession session;

	/**
	 * ユーザーの注文履歴ページを表示します。
	 *
	 * @param model ビューにデータを提供するためのモデル
	 * @return 注文履歴ページのビュー名
	 */
	@GetMapping("/history")
	public String getOrderHistory(Model model) {
		// 購入履歴を取得
		List<Order> historyList = shoppingService.getHistories((Integer) session.getAttribute("userId"));
		model.addAttribute("historyList", historyList);
		return "user/history";
	}

	/**
	 * 指定された注文の詳細ページを表示します。
	 *
	 * @param model ビューにデータを提供するためのモデル
	 * @param orderId 表示する注文のID
	 * @return 注文詳細ページのビュー名
	 */
	@GetMapping("/history/{id}")
	public String getOrderHistoryDetail(Model model,
			@PathVariable("id") Integer orderId) {

		// 注文履歴を1件取得
		Order orderDetailsOne = shoppingService.getOrderDetailsOne((Integer) session.getAttribute("userId"), orderId);
		model.addAttribute("orderDetailsOne", orderDetailsOne);

		return "user/history-detail";
	}
}
