package katachi.spring.exercise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import katachi.spring.exercise.application.service.UserApplicationService;
import katachi.spring.exercise.domain.user.model.ExtendedUser;
import katachi.spring.exercise.domain.user.model.Order;
import katachi.spring.exercise.domain.user.service.ShoppingService;

/**
 * 注文履歴に関連するリクエストを処理するコントローラークラスです。
 */
@Controller
@RequestMapping("/account")
public class HistoryController {

	@Autowired
	private ShoppingService shoppingService;

	@Autowired
	private UserApplicationService userApplicationService;

	/**
	 * ユーザーの注文履歴ページを表示します。
	 *
	 * @param model ビューにデータを提供するためのモデル
	 * @return 注文履歴ページのビュー名
	 */
	@GetMapping("/history")
	public String showOrderHistory(Model model) {

		// ユーザーの注文履歴を取得
		ExtendedUser userDetails = userApplicationService.getCurrentUserDetails();
		List<Order> historyList = shoppingService.getHistories(userDetails.getUserId());
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
	@GetMapping("/history-details/{orderId}")
	public String showOrderHistoryDetail(Model model,
			@PathVariable("orderId") Integer orderId) {

		// 指定された注文の詳細を取得
		ExtendedUser userDetails = userApplicationService.getCurrentUserDetails();
		Order orderDetails = shoppingService.getOrderDetailsOne(userDetails.getUserId(), orderId);
		model.addAttribute("orderDetails", orderDetails);

		return "user/history-detail";
	}
}
