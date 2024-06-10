package katachi.spring.exercise.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import katachi.spring.exercise.application.service.UserApplicationService;
import katachi.spring.exercise.domain.user.model.MGoods;
import katachi.spring.exercise.domain.user.model.MUser;
import katachi.spring.exercise.domain.user.model.Order;
import katachi.spring.exercise.domain.user.service.ShoppingService;
import katachi.spring.exercise.form.CustomerEditForm;
import katachi.spring.exercise.form.GoodsEditForm;
import katachi.spring.exercise.form.GroupOrder;

/**
 * 管理者用コントローラークラス。
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	/** ショッピングサービス */
	@Autowired
	private ShoppingService shoppingService;

	/** モデルマッパー */
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserApplicationService userApplicationService;

	/**
	 * 商品一覧を取得し、表示する。
	 *
	 * @param model モデル
	 * @return 商品一覧のビュー名
	 */
	@GetMapping("/goods-management")
	public String getGoodsList(Model model) {
		List<MGoods> goodsList = shoppingService.getGoods();
		model.addAttribute("goodsList", goodsList);
		return "admin/goods-list";
	}

	/**
	 * 指定された商品の詳細を取得し、編集フォームに表示する。
	 *
	 * @param form     商品編集フォーム
	 * @param model    モデル
	 * @param goodsId  商品ID
	 * @return 商品編集のビュー名
	 */
	@GetMapping("/goods-list/{goodsId}")
	public String getGoods(GoodsEditForm form,
			Model model,
			@PathVariable("goodsId") Integer goodsId) {

		MGoods goods = shoppingService.getGoodsOne(goodsId);
		form = modelMapper.map(goods, GoodsEditForm.class);
		model.addAttribute("goodsEditForm", form);

		return "admin/goods-edit";
	}

	/**
	 * 商品登録フォームを表示する。
	 *
	 * @param form 商品登録フォーム
	 * @return 商品登録のビュー名
	 */
	@GetMapping("/goods-register")
	public String getRegisterGoods(@ModelAttribute GoodsEditForm form) {
		return "admin/goods-register";
	}

	/**
	 * 商品登録を処理する。
	 *
	 * @param form 商品登録フォーム
	 * @return 商品管理ページへのリダイレクト
	 */
	@PostMapping("/goods-register")
	public String postRegisterGoods(@ModelAttribute @Validated(GroupOrder.class) GoodsEditForm form, BindingResult bindingResult) {

		// 入力チェック結果
		if (bindingResult.hasErrors()) {
			// NG:ゲスト登録画面に戻る
			return getRegisterGoods(form);
		}

		MGoods goods = modelMapper.map(form, MGoods.class);
		shoppingService.registerNewGoods(goods);

		return "redirect:/admin/goods-management";
	}

	/**
	 * 商品更新処理を行う。
	 *
	 * @param form  更新する商品の情報を含むフォーム
	 * @param model モデル
	 * @return 商品管理ページへのリダイレクト
	 */
	@PostMapping(value = "/goods-management", params = "update")
	public String updateGoods(@Validated(GroupOrder.class) GoodsEditForm form, BindingResult bindingResult) {

		// 入力チェック結果
		if (bindingResult.hasErrors()) {

			return "admin/goods-edit";
		}

		MGoods goods = modelMapper.map(form, MGoods.class);
		shoppingService.updateGoodsOne(goods);

		return "redirect:/admin/goods-management";
	}

	/**
	 * 商品削除処理を行う。
	 *
	 * @param form  削除する商品の情報を含むフォーム
	 * @param model モデル
	 * @return 商品管理ページへのリダイレクト
	 */
	@PostMapping(value = "/goods-management", params = "delete")
	public String deleteGoods(GoodsEditForm form) {
		shoppingService.deleteGoodsOne(form.getId());
		return "redirect:/admin/goods-management";
	}

	/**
	 * 注文一覧を取得し、表示する。
	 *
	 * @param query フィルタリングするクエリ
	 * @param model モデル
	 * @return 注文一覧のビュー名
	 */
	@GetMapping("/order-list")
	public String getOrderList(@RequestParam(required = false) String query, Model model) {
		List<Order> orderList = shoppingService.getAllOrders(query);
		model.addAttribute("orderList", orderList);
		return "admin/order-list";
	}

	/**
	 * 注文詳細を取得し、表示する。
	 *
	 * @param model   モデル
	 * @param orderId 注文ID
	 * @return 注文詳細のビュー名
	 */
	@GetMapping("/order-details/{orderId}")
	public String getOrderDetails(Model model, @PathVariable("orderId") Integer orderId) {
		Order orderDetailsOne = shoppingService.getUserOrders(orderId);
		model.addAttribute("orderDetailsOne", orderDetailsOne);
		return "admin/order-details";
	}

	/**
	 * 顧客一覧を取得し、表示する。
	 *
	 * @param query フィルタリングするクエリ
	 * @param model モデル
	 * @return 顧客一覧のビュー名
	 */
	@GetMapping("/customer-list")
	public String getUserList(@RequestParam(required = false) String query, Model model) {
		List<MUser> customerList = shoppingService.getUsers(query);
		model.addAttribute("customerList", customerList);
		return "admin/customer-list";
	}

	@GetMapping("/customer-edit/{userId}")
	public String getGoods(CustomerEditForm form,
			Model model,
			@PathVariable("userId") Integer userId,
			Locale locale) {

		// 性別を取得
		Map<String, Integer> genderMap = userApplicationService.getGenderMap(locale);
		model.addAttribute("genderMap", genderMap);

		// 都道府県を取得
		List<String> prefecturesList = userApplicationService.getPrefecturesList();
		model.addAttribute("prefecturesList", prefecturesList);

		MUser user = shoppingService.getCustomerOne(userId);
		form = modelMapper.map(user, CustomerEditForm.class);
		model.addAttribute("customerEditForm", form);

		return "admin/customer-edit";
	}

	/**
	 * 顧客更新処理を行う。
	 *
	 * @param form  更新する顧客の情報を含むフォーム
	 * @param model モデル
	 * @return 顧客管理ページへのリダイレクト
	 */
	@PostMapping(value = "/customer-edit", params = "update")
	public String updateCustomer(@Validated(GroupOrder.class) CustomerEditForm form, BindingResult bindingResult) {

		// 入力チェック結果
		if (bindingResult.hasErrors()) {
			// NG:ゲスト登録画面に戻る
			return "admin/customer-edit";
		}

		MUser user = modelMapper.map(form, MUser.class);
		shoppingService.updateCustomer(user);

		return "redirect:/admin/customer-list";
	}

	/**
	 * 顧客削除処理を行う。
	 *
	 * @param form  削除する顧客の情報を含むフォーム
	 * @param model モデル
	 * @return 顧客管理ページへのリダイレクト
	 */
	@PostMapping(value = "/customer-edit", params = "delete")
	public String deleteCustomer(CustomerEditForm form) {

		shoppingService.deleteCustomer(form.getId());
		return "redirect:/admin/customer-list";
	}
}
