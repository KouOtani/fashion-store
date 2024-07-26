package katachi.spring.exercise.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import katachi.spring.exercise.application.service.UserApplicationService;
import katachi.spring.exercise.domain.user.model.Cart;
import katachi.spring.exercise.domain.user.model.CartItem;
import katachi.spring.exercise.domain.user.model.DeliveryAddress;
import katachi.spring.exercise.domain.user.model.ExtendedUser;
import katachi.spring.exercise.domain.user.model.MUser;
import katachi.spring.exercise.domain.user.model.Order;
import katachi.spring.exercise.domain.user.model.OrderDetails;
import katachi.spring.exercise.domain.user.model.SessionGuestData;
import katachi.spring.exercise.domain.user.service.ShoppingService;

/**
 * 注文に関連するリクエストを処理するコントローラークラスです。
 */
@Controller
@RequestMapping("/order")
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
	@GetMapping("/checkout")
	public String proceedToCheckout(Model model, Authentication authentication) {

		if (sessionGuestData.getGuestData() != null) {
			model.addAttribute("guestData", sessionGuestData.getGuestData());
			return "user/checkout";

		} else if (authentication != null && authentication.isAuthenticated()) {
			ExtendedUser userDetails = userApplicationService.getCurrentUserDetails();
			MUser user = shoppingService.getLoginUserById(userDetails.getUserId());
			model.addAttribute("user", user);
			return "user/checkout";

		} else {
			return "redirect:/login";
		}
	}

	/**
	 * 注文完了ページを表示します。
	 *
	 * @param authentication 認証情報を含むオブジェクト
	 * @param redirectAttributes リダイレクト後のフラッシュメッセージを設定するためのオブジェクト
	 * @return 注文完了ページのビュー名
	 */
	@Transactional
	@GetMapping("/complete")
	public String completeOrder(Authentication authentication,
			RedirectAttributes redirectAttributes) {

		// 注文完了時に注文者を登録する
		Order order = new Order();
		ExtendedUser userDetails = null;

		if (authentication != null && authentication.isAuthenticated()) {
			userDetails = userApplicationService.getCurrentUserDetails();
			order.setUserId(userDetails.getUserId());
		} else {
			order.setUserId(0);
		}

		order.setOrderDate(new Date());

		// 一意の注文番号を生成
		String orderNumber = userApplicationService.generateOrderNumber();
		order.setOrderNumber(orderNumber);

		shoppingService.saveCustomer(order);

		redirectAttributes.addFlashAttribute("orderNumber", orderNumber);

		// 注文完了時に注文した商品の詳細を登録する
		List<CartItem> cartItemsList = cart.getCartList();
		List<OrderDetails> orderDetailsList = new ArrayList<>();

		BigDecimal totalSales = BigDecimal.ZERO;

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

			// 売上を加算
			BigDecimal itemTotal = BigDecimal.valueOf(price).multiply(BigDecimal.valueOf(quantity));
			totalSales = totalSales.add(itemTotal);
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

		// 月別売上を更新
		//		shoppingService.updateMonthlySales(order.getOrderDate(), totalSales);
		shoppingService.updateOrInsertMonthlySales(order.getOrderDate(), totalSales); //デプロイ用メソッド

		return "redirect:/order/complete-order";
	}

	/**
	 * 注文完了ページを表示します。
	 *
	 * @return 注文完了ページのビュー名
	 */
	@GetMapping("/complete-order")
	public String showCompleteOrderPage() {

		// セッションとカートをクリアする
		cart.clearCart();
		session.removeAttribute("cart");
		session.removeAttribute("totalQuantity");
		session.removeAttribute("totalAmount");

		return "user/complete";
	}

}
