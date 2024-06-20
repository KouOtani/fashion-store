package katachi.spring.exercise.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import katachi.spring.exercise.application.service.UserApplicationService;
import katachi.spring.exercise.domain.user.model.Cart;
import katachi.spring.exercise.domain.user.model.CartItem;
import katachi.spring.exercise.domain.user.model.MGoods;
import katachi.spring.exercise.domain.user.service.ShoppingService;
import katachi.spring.exercise.userwithcode.UserWithCode;

/**
 * カートに関連するリクエストを処理するコントローラークラスです。
 */
@Controller
@RequestMapping("/goods")
public class CartController {

	@Autowired
	private ShoppingService shoppingService;

	@Autowired
	private Cart cart;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private HttpSession session;

	@Autowired
	private UserApplicationService userApplicationService;

	/**
	 * カートの内容を表示します。
	 *
	 * @param model ビューにデータを提供するためのモデル
	 * @param authentication 認証情報を含むオブジェクト
	 * @return カートページのビュー名
	 */
	@GetMapping("/cart")
	public String showCartContents(Model model, Authentication authentication) {
		session.setAttribute("totalAmount", cart.totalAmount());
		return "user/cart";
	}

	/**
	 * カートに商品を追加します。
	 *
	 * @param userId ユーザーID（ログインユーザーを識別するために使用）
	 * @param goodsId 商品ID（追加する商品の識別子）
	 * @param quantity 追加する商品の数量
	 * @param redirectAttributes リダイレクト後のフラッシュメッセージを設定するためのオブジェクト
	 * @param authentication 認証情報を含むオブジェクト
	 * @return 商品詳細ページへのリダイレクトURL
	 */
	@PostMapping("/add-to-cart")
	public String addItemToCart(@Nullable @RequestParam Integer userId,
			@RequestParam Integer goodsId,
			@RequestParam Integer quantity,
			RedirectAttributes redirectAttributes,
			Authentication authentication) {

		// ログインユーザーのカートに商品を追加する処理
		if (authentication != null && authentication.isAuthenticated()) {
			boolean itemExists = shoppingService.checkCartItemExistence(userId, goodsId);
			if (itemExists) {
				shoppingService.addToCartItemQuantity(userId, goodsId, quantity);
			} else {
				shoppingService.addToCart(userId, goodsId, quantity);
			}
		}

		MGoods goods = shoppingService.getGoodsOne(goodsId);
		CartItem item = modelMapper.map(goods, CartItem.class);
		cart.addToCart(item, quantity);
		session.setAttribute("cart", cart.getCartList());

		// リダイレクト先にデータを渡す
		redirectAttributes.addFlashAttribute("message", "商品がカートに追加されました。");

		return "redirect:/goods/detail/" + goodsId;
	}

	/**
	 * カート内の商品数量を更新します。
	 *
	 * @param userId         ユーザーID（オプション）
	 * @param goodsId        更新する商品のID
	 * @param quantity       新しい数量
	 * @param authentication 認証情報を含むオブジェクト
	 * @return カートページへのリダイレクトURL
	 */
	@PostMapping("/update-quantity")
	public String updateCartItemQuantity(@Nullable @RequestParam Integer userId,
			@RequestParam Integer goodsId,
			@RequestParam Integer quantity,
			Authentication authentication) {

		// ログインユーザーのカートに商品を追加する処理
		if (authentication != null && authentication.isAuthenticated()) {
			shoppingService.changeCartItemQuantity(userId, goodsId, quantity);
		}

		cart.changeQuantity(goodsId, quantity);
		session.setAttribute("cart", cart.getCartList());

		return "redirect:/goods/cart";
	}

	/**
	 * カートから商品を削除します。
	 *
	 * @param goodsId 削除する商品のID
	 * @param authentication 認証情報を含むオブジェクト
	 * @return カートページへのリダイレクトURL
	 */
	@GetMapping("/remove-item")
	public String removeItemFromCart(@RequestParam Integer goodsId,
			Authentication authentication) {

		if (authentication != null && authentication.isAuthenticated()) {
			UserWithCode userDetails = userApplicationService.getCurrentUserDetails();
			shoppingService.deleteItem(userDetails.getUserId(), goodsId);
		}

		// カートから商品を削除する処理
		cart.removeItemById(goodsId);
		session.removeAttribute("totalQuantity");

		return "redirect:/goods/cart";
	}
}
