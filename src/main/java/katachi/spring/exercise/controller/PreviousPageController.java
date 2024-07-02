package katachi.spring.exercise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import katachi.spring.exercise.application.service.UserApplicationService;
import katachi.spring.exercise.domain.user.model.Cart;
import katachi.spring.exercise.domain.user.model.CartItem;
import katachi.spring.exercise.domain.user.model.ExtendedUser;
import katachi.spring.exercise.domain.user.service.ShoppingService;

/**
 * ユーザーの前のページへのリダイレクトを管理するコントローラークラスです。
 */
@Controller
public class PreviousPageController {

	@Autowired
	private ShoppingService shoppingService;

	@Autowired
	private Cart cart;

	@Autowired
	private HttpSession session;

	@Autowired
	private UserApplicationService userApplicationService;

	/**
	 * 認証後にユーザーを前のページにリダイレクトします。
	 *
	 * @param request        リクエスト情報を含むオブジェクト
	 * @param authentication 認証情報を含むオブジェクト
	 * @return 前のページまたはホームページへのリダイレクトURL
	 */
	@GetMapping("/redirect")
	public String redirectToPreviousPage(Authentication authentication) {

		ExtendedUser userDetails = userApplicationService.getCurrentUserDetails();

		// 管理者であれば管理者ページにリダイレクト
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
		if (isAdmin) {
			return "redirect:/admin/goods-management";
		}

		// カートのアイテムをユーザーのカートに転送
		shoppingService.transferCartItems(userDetails.getUserId(), cart.getCartList());

		// ユーザーのカートリストを取得して設定
		List<CartItem> userCartList = shoppingService.getCartList(userDetails.getUserId());
		cart.setCartList(userCartList);

		session.setAttribute("cart", userCartList);
		session.setAttribute("totalQuantity", cart.totalQuantity());
		session.setAttribute("totalAmount", cart.totalAmount());

		// 前のURLにリダイレクト
		String previousUrl = (String) session.getAttribute("previousUrl");
		return "redirect:" + (previousUrl != null ? previousUrl : "/");
	}
}
