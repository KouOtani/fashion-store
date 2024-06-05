package katachi.spring.exercise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import katachi.spring.exercise.domain.user.model.Cart;
import katachi.spring.exercise.domain.user.model.CartItem;
import katachi.spring.exercise.domain.user.model.MUser;
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

	/**
	 * 認証後にユーザーを前のページにリダイレクトします。
	 *
	 * @param request        リクエスト情報を含むオブジェクト
	 * @param authentication 認証情報を含むオブジェクト
	 * @return 前のページまたはホームページへのリダイレクトURL
	 */
	@GetMapping("/redirect")
	public String redirect(HttpServletRequest request, Authentication authentication) {

		MUser user = shoppingService.getLoginUserByEmail(authentication.getName());
		session.setAttribute("userId", user.getId());
		System.out.println(user.getId());

		// カートのアイテムをユーザーのカートに転送
		cart.transferCartItems(user.getId(), cart.getCartList());

		// ユーザーのカートリストを取得して設定
		List<CartItem> cartList = shoppingService.getCartList(authentication.getName());
		cart.setCartList(cartList);

		session.setAttribute("cart", cartList);
		session.setAttribute("totalQuantity", cart.totalQuantity());
		session.setAttribute("totalAmount", cart.totalAmount());

		// 前のURLにリダイレクト
		String prevUrl = (String) session.getAttribute("prevUrl");
		return "redirect:" + (prevUrl != null ? prevUrl : "/");
	}
}
