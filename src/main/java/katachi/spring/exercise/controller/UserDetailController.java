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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import katachi.spring.exercise.application.service.UserApplicationService;
import katachi.spring.exercise.domain.user.model.ExtendedUser;
import katachi.spring.exercise.domain.user.model.MUser;
import katachi.spring.exercise.domain.user.service.ShoppingService;
import katachi.spring.exercise.form.AddressEditForm;
import katachi.spring.exercise.form.GroupOrder;
import katachi.spring.exercise.form.PasswordEditForm;
import katachi.spring.exercise.form.ShippingAddressEditForm;
import katachi.spring.exercise.form.SignupForm;
import katachi.spring.exercise.form.UserDetailEditForm;

/**
 * ユーザー詳細および情報更新に関連するリクエストを処理するコントローラークラスです。
 */
@Controller
@RequestMapping("/account")
public class UserDetailController {

	@Autowired
	private UserApplicationService userApplicationService;

	@Autowired
	private ShoppingService shoppingService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private HttpSession session;

	/**
	 * ユーザー詳細画面を表示します。
	 *
	 * @param model ビューにデータを提供するためのモデル
	 * @param form  サインアップフォームのデータを保持するオブジェクト
	 * @return ユーザー詳細画面のビュー名
	 */
	@GetMapping("/detail")
	public String getUserDetail(Model model,
			SignupForm form) {
		ExtendedUser userDetails = userApplicationService.getCurrentUserDetails();
		MUser user = shoppingService.getLoginUserById(userDetails.getUserId());
		model.addAttribute("user", user);
		return "user/detail";
	}

	/**
	 * ユーザーの住所更新画面を表示します。
	 *
	 * @param form   アドレス編集フォームのデータを保持するオブジェクト
	 * @param locale ロケール情報
	 * @return 住所更新画面のビュー名
	 */
	@GetMapping("/address-update")
	public String getAddressUpdate(@ModelAttribute AddressEditForm form,
			Locale locale) {
		return "user/address-edit";
	}

	/**
	 * ユーザーの住所更新処理を行います。
	 *
	 * @param locale         ロケール情報
	 * @param form           アドレス編集フォームのデータを保持するオブジェクト
	 * @param bindingResult  入力チェックの結果
	 * @param model          ビューにデータを提供するためのモデル
	 * @return 入力チェックが成功した場合はユーザー詳細画面へのリダイレクトURL、失敗した場合は住所更新画面のビュー名
	 */
	@PostMapping("/address-update-confirm")
	public String confirmAddressUpdate(Locale locale,
			@ModelAttribute @Validated(GroupOrder.class) AddressEditForm form,
			BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			return getAddressUpdate(form, locale);
		}

		if (!form.getEMail().equals(form.getConfirmationEmail())) {
			bindingResult.rejectValue("eMail", "error.addressEditForm", "確認用のメールアドレスと一致しません。");
			return getAddressUpdate(form, locale);
		}

		if (shoppingService.isEmailRegistered(form.getEMail())) {
			bindingResult.rejectValue("eMail", "error.addressEditForm", "このメールアドレスは既に登録されています。");
			return getAddressUpdate(form, locale);
		}

		ExtendedUser userDetails = userApplicationService.getCurrentUserDetails();
		MUser user = shoppingService.getLoginUserById(userDetails.getUserId());
		model.addAttribute("beforeEmail", user.getEMail());

		return "user/address-update-confirm";
	}

	/**
	 * ユーザーの住所更新処理を行います。
	 *
	 * @param form              アドレス編集フォームのデータを保持するオブジェクト
	 * @param redirectAttributes リダイレクト先にデータを渡すためのオブジェクト
	 * @return ユーザー詳細画面へのリダイレクトURL
	 */
	@PostMapping("/address-update")
	public String addressUpdate(@ModelAttribute AddressEditForm form,
			RedirectAttributes redirectAttributes) {
		ExtendedUser userDetails = userApplicationService.getCurrentUserDetails();
		shoppingService.addressUpdate(userDetails.getUserId(), form.getEMail());
		redirectAttributes.addFlashAttribute("message", "メールアドレスを更新しました。");
		return "redirect:/account/detail";
	}

	/**
	 * ユーザーのパスワード更新画面を表示します。
	 *
	 * @param form   パスワード編集フォームのデータを保持するオブジェクト
	 * @param locale ロケール情報
	 * @return パスワード更新画面のビュー名
	 */
	@GetMapping("/password-update")
	public String getPasswordUpdate(@ModelAttribute PasswordEditForm form,
			Locale locale) {
		return "user/password-edit";
	}

	/**
	 * ユーザーのパスワード更新処理を行います。
	 *
	 * @param locale         ロケール情報
	 * @param form           パスワード編集フォームのデータを保持するオブジェクト
	 * @param bindingResult  入力チェックの結果
	 * @param redirectAttributes リダイレクト先にデータを渡すためのオブジェクト
	 * @return ユーザー詳細画面へのリダイレクトURL、またはパスワード更新画面のビュー名
	 */
	@PostMapping("/password-update")
	public String passwordUpdate(Locale locale,
			@ModelAttribute @Validated(GroupOrder.class) PasswordEditForm form,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return getPasswordUpdate(form, locale);
		}

		if (!form.getPassword().equals(form.getRePassword())) {
			bindingResult.rejectValue("password", "error.passwordEditForm", "確認用のパスワードと一致しません。");
			return getPasswordUpdate(form, locale);
		}

		ExtendedUser userDetails = userApplicationService.getCurrentUserDetails();
		shoppingService.passwordUpdate(userDetails.getUserId(), form.getPassword());
		redirectAttributes.addFlashAttribute("message", "パスワードを更新しました。");
		return "redirect:/account/detail";
	}

	/**
	 * ユーザーの配送先住所更新画面を表示します。
	 *
	 * @param form  配送先住所編集フォームのデータを保持するオブジェクト
	 * @param locale ロケール情報
	 * @param model ビューにデータを提供するためのモデル
	 * @return 配送先住所更新画面のビュー名
	 */
	@GetMapping("/shipping-address-update")
	public String getShippingAddressUpdate(@ModelAttribute ShippingAddressEditForm form,
			Locale locale,
			Model model) {
		List<String> prefecturesList = userApplicationService.getPrefecturesList();
		model.addAttribute("prefecturesList", prefecturesList);
		return "user/shippingaddress-edit";
	}

	/**
	 * ユーザーの配送先住所更新処理を行います。
	 *
	 * @param locale ロケール情報
	 * @param form 配送先住所編集フォームのデータを保持するオブジェクト
	 * @param bindingResult 入力チェックの結果
	 * @param model ビューにデータを提供するためのモデル
	 * @return 入力チェックが成功した場合はレジ画面へのリダイレクトURL、失敗した場合は配送先住所更新画面のビュー名
	 */
	@PostMapping("/shipping-address-update-confirm")
	public String confirmShippingAddressUpdate(Locale locale,
			@ModelAttribute @Validated(GroupOrder.class) ShippingAddressEditForm form,
			BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			return getShippingAddressUpdate(form, locale, model);
		}

		return "user/shippingaddress-update-confirm";
	}

	/**
	 * ユーザーの配送先住所更新処理を行います。
	 *
	 * @param locale ロケール情報
	 * @param form 配送先住所編集フォームのデータを保持するオブジェクト
	 * @param redirectAttributes リダイレクト先にデータを渡すためのオブジェクト
	 * @return レジ画面へのリダイレクトURL
	 */
	@PostMapping("/shipping-address-update")
	public String shippingAddressUpdate(Locale locale, @ModelAttribute ShippingAddressEditForm form,
			RedirectAttributes redirectAttributes) {

		MUser user = modelMapper.map(form, MUser.class);

		if (form.getIsEverytime()) {
			ExtendedUser userDetails = userApplicationService.getCurrentUserDetails();
			shoppingService.shippingAddressUpdate(userDetails.getUserId(), user);

		} else {
			session.setAttribute("user", user);
		}

		redirectAttributes.addFlashAttribute("message", "配送先住所を更新しました。");

		return "redirect:/order/checkout";
	}

	/**
	 * ユーザーの詳細情報更新画面を表示します。
	 *
	 * @param form  ユーザー詳細編集フォームのデータを保持するオブジェクト
	 * @param locale ロケール情報
	 * @param model ビューにデータを提供するためのモデル
	 * @return ユーザー詳細情報更新画面のビュー名
	 */
	@GetMapping("/detail-update")
	public String getDetailUpdate(@ModelAttribute UserDetailEditForm form,
			Locale locale,
			Model model) {
		ExtendedUser userDetails = userApplicationService.getCurrentUserDetails();
		MUser user = shoppingService.getLoginUserById(userDetails.getUserId());
		form = modelMapper.map(user, UserDetailEditForm.class);
		model.addAttribute("userDetailEditForm", form);

		Map<String, Integer> genderMap = userApplicationService.getGenderMap(locale);
		model.addAttribute("genderMap", genderMap);

		List<String> prefecturesList = userApplicationService.getPrefecturesList();
		model.addAttribute("prefecturesList", prefecturesList);

		return "user/userdetail-edit";
	}

	/**
	 * ユーザーの詳細情報更新処理を行います。
	 *
	 * @param model          ビューにデータを提供するためのモデル
	 * @param locale         ロケール情報
	 * @param form           ユーザー詳細編集フォームのデータを保持するオブジェクト
	 * @param bindingResult  入力チェックの結果
	 * @return 入力チェックが成功した場合はユーザー詳細画面へのリダイレクトURL、失敗した場合は詳細情報更新画面のビュー名
	 */
	@PostMapping("/detail-update-confirm")
	public String ConfirmUserDetailUpdate(Model model, Locale locale,
			@ModelAttribute @Validated(GroupOrder.class) UserDetailEditForm form,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return getDetailUpdate(form, locale, model);
		}

		return "user/userdetail-update-confirm";
	}

	/**
	 * ユーザーの詳細情報更新処理を行います。
	 *
	 * @param form              ユーザー詳細編集フォームのデータを保持するオブジェクト
	 * @param redirectAttributes リダイレクト先にデータを渡すためのオブジェクト
	 * @return ユーザー詳細画面へのリダイレクトURL
	 */
	@PostMapping("/detail-update")
	public String userDetailUpdate(@ModelAttribute UserDetailEditForm form,
			RedirectAttributes redirectAttributes) {
		MUser user = modelMapper.map(form, MUser.class);
		ExtendedUser userDetails = userApplicationService.getCurrentUserDetails();
		shoppingService.detailUpdate(userDetails.getUserId(), user);
		redirectAttributes.addFlashAttribute("message", "ユーザー情報を更新しました。");
		return "redirect:/account/detail";
	}

}
