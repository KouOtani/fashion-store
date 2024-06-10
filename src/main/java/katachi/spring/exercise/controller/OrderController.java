package katachi.spring.exercise.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import katachi.spring.exercise.application.service.UserApplicationService;
import katachi.spring.exercise.domain.user.model.Cart;
import katachi.spring.exercise.domain.user.model.CartItem;
import katachi.spring.exercise.domain.user.model.DeliveryAddress;
import katachi.spring.exercise.domain.user.model.MUser;
import katachi.spring.exercise.domain.user.model.Order;
import katachi.spring.exercise.domain.user.model.OrderDetails;
import katachi.spring.exercise.domain.user.model.SessionGuestData;
import katachi.spring.exercise.domain.user.service.ShoppingService;
import katachi.spring.exercise.userwithcode.UserWithCode;

/**
 * 注文に関連するリクエストを処理するコントローラークラスです。
 */
@Controller
@RequestMapping("/goods")
public class OrderController {

	@Autowired
	private Cart cart;

	@Autowired
	private ShoppingService shoppingService;

	@Autowired
	private HttpSession session;

	@Autowired
	private SessionGuestData sessionGuestData;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserApplicationService userApplicationService;

	/**
	 * レジ画面に進むための処理を行います。
	 *
	 * @param model ビューにデータを提供するためのモデル
	 * @param authentication 認証情報を含むオブジェクト
	 * @return レジ画面のビュー名またはログイン画面へのリダイレクトURL
	 */
	@GetMapping("/casher")
	public String proceedToCheckout(Model model, Authentication authentication) {

		if (sessionGuestData.getGuestData() != null) {
			model.addAttribute("guestData", sessionGuestData.getGuestData());
			return "user/casher";

		} else if (authentication != null && authentication.isAuthenticated()) {
			UserWithCode userDetails = userApplicationService.getCurrentUserDetails();
			MUser user = shoppingService.getLoginUserById(userDetails.getUserId());
			model.addAttribute("user", user);
			return "user/casher";

		} else {
			return "redirect:/login";
		}
	}

	/**
	 * 購入完了ページを表示します。
	 *
	 * @param authentication 認証情報を含むオブジェクト
	 * @return 購入完了ページのビュー名
	 */
	@GetMapping("/complete")
	public String getComplete(Authentication authentication,
			RedirectAttributes redirectAttributes) {

		// 購入完了時に購入者を登録する
		Order order = new Order();
		UserWithCode userDetails = null;

		if (authentication != null && authentication.isAuthenticated()) {
			userDetails = userApplicationService.getCurrentUserDetails();
			order.setUserId(userDetails.getUserId());
		} else {
			order.setUserId(0);
		}

		order.setOrderDate(new Date());

		// 一意の注文番号を生成
		String orderNumber = generateOrderNumber();
		order.setOrderNumber(orderNumber);

		shoppingService.saveCustomer(order);

		redirectAttributes.addFlashAttribute("orderNumber", orderNumber);

		// 購入完了時に購入した商品の詳細を登録する
		List<CartItem> cartItemsList = cart.getCartList();
		List<OrderDetails> orderDetailsList = new ArrayList<>();

		for (CartItem cartItem : cartItemsList) {
			Integer goodsId = cartItem.getGoodsId();
			Integer quantity = cartItem.getQuantity();
			Integer price = cartItem.getPrice();

			OrderDetails orderDetail = new OrderDetails();
			orderDetail.setOrderId(order.getId());
			orderDetail.setGoodsId(goodsId);
			orderDetail.setPrice(price);
			orderDetail.setQuantity(quantity);
			orderDetailsList.add(orderDetail);
		}
		shoppingService.saveOrder(orderDetailsList);

		// 配送先を登録する
		DeliveryAddress address = null;
		if (session.getAttribute("user") != null) {
			address = modelMapper.map((MUser) session.getAttribute("user"), DeliveryAddress.class);
			address.setOrderId(order.getId());

		} else if (session.getAttribute("user") == null && userDetails != null) {
			userDetails = userApplicationService.getCurrentUserDetails();
			MUser user = shoppingService.getLoginUserById(userDetails.getUserId());
			address = modelMapper.map(user, DeliveryAddress.class);
			address.setOrderId(order.getId());

		} else {
			address = modelMapper.map(sessionGuestData.getGuestData(), DeliveryAddress.class);
			address.setOrderId(order.getId());
		}

		shoppingService.saveDeliveryAddress(address);

		// 認証済みの場合はカートをクリアする
		if (authentication != null && authentication.isAuthenticated()) {
			userDetails = userApplicationService.getCurrentUserDetails();
			shoppingService.allClearCart(userDetails.getUserId());
		}

		// セッションとカートをクリアする
		cart.clearCart();
		session.invalidate();

		return "redirect:/goods/complete-order";
	}

	@GetMapping("/complete-order")
	public String viewComplete() {
		return "user/complete";
	}

	/**
	 * 注文番号を生成するメソッド。
	 *
	 * @return 生成された注文番号
	 */
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
