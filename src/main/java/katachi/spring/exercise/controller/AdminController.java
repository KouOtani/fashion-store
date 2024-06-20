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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	/** アプリケーションサービス */
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
	@GetMapping("/goods-edit/{goodsId}")
	public String getGoodsDetails(GoodsEditForm form,
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
	 * 商品登録データを確認する。
	 *
	 * @param form 商品登録フォーム
	 * @param bindingResult バインディング結果
	 * @return 商品登録確認ページまたは商品登録ページ
	 */
	@PostMapping("/goods-register-confirm")
	public String confirmGoodsData(@ModelAttribute @Validated(GroupOrder.class) GoodsEditForm form,
			BindingResult bindingResult) {
		// 入力チェック結果
		if (bindingResult.hasErrors()) {
			// NG:商品登録画面に戻る
			return getRegisterGoods(form);
		}

		return "admin/goods-register-confirm";
	}

	/**
	 * 商品登録を処理する。
	 *
	 * @param form 商品登録フォーム
	 * @return 商品管理ページへのリダイレクト
	 */
	@PostMapping("/goods-register")
	public String registerGoods(@ModelAttribute GoodsEditForm form,
			RedirectAttributes redirectAttributes) {

		MGoods goods = modelMapper.map(form, MGoods.class);
		shoppingService.registerNewGoods(goods);

		redirectAttributes.addFlashAttribute("message", "新規商品を登録しました。");

		return "redirect:/admin/goods-management";
	}

	/**
	 * 商品更新確認処理を行う。
	 *
	 * @param form  更新する商品の情報を含むフォーム
	 * @param bindingResult バインディング結果
	 * @return 商品更新確認ページまたは商品編集ページ
	 */
	@PostMapping(value = "/goods-management-confirm", params = "update")
	public String confirmGoodsUpdate(@ModelAttribute @Validated(GroupOrder.class) GoodsEditForm form,
			BindingResult bindingResult) {

		// 入力チェック結果
		if (bindingResult.hasErrors()) {

			return "admin/goods-edit";
		}

		return "admin/goods-update-confirm";
	}

	/**
	 * 商品を更新する。
	 *
	 * @param form 商品編集フォーム
	 * @param redirectAttributes リダイレクト属性
	 * @return 商品管理ページへのリダイレクト
	 */
	@PostMapping("/goods-update")
	public String updateGoods(@ModelAttribute GoodsEditForm form,
			RedirectAttributes redirectAttributes) {

		MGoods goods = modelMapper.map(form, MGoods.class);
		shoppingService.updateGoods(goods);

		redirectAttributes.addFlashAttribute("message", "商品を更新しました。");

		return "redirect:/admin/goods-management";
	}

	/**
	 * 商品削除確認処理を行う。
	 *
	 * @param form  削除する商品の情報を含むフォーム
	 * @return 商品削除確認ページ
	 */
	@PostMapping(value = "/goods-management-confirm", params = "delete")
	public String confirmGoodsDelete(@ModelAttribute GoodsEditForm form) {

		return "admin/goods-delete-confirm";
	}

	/**
	 * 商品を削除する。
	 *
	 * @param form 商品編集フォーム
	 * @param redirectAttributes リダイレクト属性
	 * @return 商品管理ページへのリダイレクト
	 */
	@PostMapping("/goods-delete")
	public String deleteGoods(@ModelAttribute GoodsEditForm form,
			RedirectAttributes redirectAttributes) {

		shoppingService.deleteGoods(form.getId());

		redirectAttributes.addFlashAttribute("message", "商品を削除しました。");

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
	public String getOrderList(@RequestParam(required = false) String query,
			Model model) {
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
	public String getOrderDetail(Model model, @PathVariable("orderId") Integer orderId) {
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
	public String getCustomerList(@RequestParam(required = false) String query, Model model) {
		List<MUser> customerList = shoppingService.getUsers(query);
		model.addAttribute("customerList", customerList);
		return "admin/customer-list";
	}

	/**
	 * 指定された顧客の詳細を取得し、編集フォームに表示する。
	 *
	 * @param form     顧客編集フォーム
	 * @param model    モデル
	 * @param userId   ユーザーID
	 * @param locale   ロケール情報
	 * @return 顧客編集のビュー名
	 */
	@GetMapping("/customer-edit/{userId}")
	public String getCustomerDetails(CustomerEditForm form,
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
	 * 顧客更新確認処理を行う。
	 *
	 * @param form  更新する顧客の情報を含むフォーム
	 * @param bindingResult バインディング結果
	 * @return 顧客更新確認ページまたは顧客編集ページ
	 */
	@PostMapping(value = "/customer-edit-confirm", params = "update")
	public String confirmCustomerUpdate(@ModelAttribute @Validated(GroupOrder.class) CustomerEditForm form,
			BindingResult bindingResult) {

		// 入力チェック結果
		if (bindingResult.hasErrors()) {
			// NG:顧客編集画面に戻る
			return "admin/customer-edit";
		}

		return "admin/customer-update-confirm";
	}

	/**
	 * 顧客を更新する。
	 *
	 * @param form 顧客編集フォーム
	 * @param redirectAttributes リダイレクト属性
	 * @return 顧客管理ページへのリダイレクト
	 */
	@PostMapping("/customer-update")
	public String updateCustomer(@ModelAttribute CustomerEditForm form,
			RedirectAttributes redirectAttributes) {
		MUser user = modelMapper.map(form, MUser.class);
		shoppingService.updateCustomer(user);

		redirectAttributes.addFlashAttribute("message", "顧客情報を更新しました。");

		return "redirect:/admin/customer-list";
	}

	/**
	 * 顧客削除確認処理を行う。
	 *
	 * @param form  削除する顧客の情報を含むフォーム
	 * @return 顧客削除確認ページ
	 */
	@PostMapping(value = "/customer-edit-confirm", params = "delete")
	public String confirmCustomerDelete(@ModelAttribute CustomerEditForm form) {

		return "admin/customer-delete-confirm";
	}

	/**
	 * 顧客を削除する。
	 *
	 * @param form 顧客編集フォーム
	 * @param redirectAttributes リダイレクト属性
	 * @return 顧客管理ページへのリダイレクト
	 */
	@PostMapping("/customer-delete")
	public String deleteCustomer(@ModelAttribute CustomerEditForm form,
			RedirectAttributes redirectAttributes) {

		shoppingService.deleteCustomer(form.getId());

		redirectAttributes.addFlashAttribute("message", "顧客を削除しました。");
		return "redirect:/admin/customer-list";
	}
}
