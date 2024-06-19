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
import katachi.spring.exercise.domain.user.model.Cart;
import katachi.spring.exercise.domain.user.model.MUser;
import katachi.spring.exercise.domain.user.model.SessionGuestData;
import katachi.spring.exercise.domain.user.service.ShoppingService;
import katachi.spring.exercise.form.GroupOrder;
import katachi.spring.exercise.form.GuestSignupForm;
import katachi.spring.exercise.form.SignupForm;

/**
 * ユーザーおよびゲストのサインアップに関連するリクエストを処理するコントローラークラスです。
 */
@Controller
@RequestMapping("/user")
public class SignupController {

	@Autowired
	private UserApplicationService userApplicationService;

	@Autowired
	private ShoppingService shoppingService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private Cart cart;

	@Autowired
	private SessionGuestData sessionGuestData;

	@Autowired
	private HttpSession session;

	/**
	 * ユーザー登録画面を表示します。
	 *
	 * @param model ビューにデータを提供するためのモデル
	 * @param locale ロケール情報
	 * @param form サインアップフォームのデータを保持するオブジェクト
	 * @return ユーザー登録画面のビュー名
	 */
	@GetMapping("/signup")
	public String getSignup(Model model,
			Locale locale,
			@ModelAttribute SignupForm form) {
		// 性別を取得
		Map<String, Integer> genderMap = userApplicationService.getGenderMap(locale);
		model.addAttribute("genderMap", genderMap);

		// 都道府県を取得
		List<String> prefecturesList = userApplicationService.getPrefecturesList();
		model.addAttribute("prefecturesList", prefecturesList);

		// ユーザー登録画面に遷移
		return "user/signup";
	}

	/**
	 * ユーザー登録処理を行います。
	 *
	 * @param model ビューにデータを提供するためのモデル
	 * @param locale ロケール情報
	 * @param form サインアップフォームのデータを保持するオブジェクト
	 * @param bindingResult 入力チェックの結果
	 * @return 入力チェックが成功した場合はログイン画面へのリダイレクトURL、失敗した場合はユーザー登録画面のビュー名
	 */
	@PostMapping("/signup-comfirm")
	public String postSignupComfirm(Model model,
			Locale locale,
			@ModelAttribute @Validated(GroupOrder.class) SignupForm form,
			BindingResult bindingResult) {
		// 入力チェック結果
		if (bindingResult.hasErrors()) {
			// NG:ユーザー登録画面に戻る
			return getSignup(model, locale, form);
		}

		if (!form.getPassword().equals(form.getRePassword())) {

			// NG:確認パスワードと一致しない
			bindingResult.rejectValue("password", "error.signupForm", "確認用のパスワードと一致しません。");
			return getSignup(model, locale, form);
		}

		if (shoppingService.isEmailRegistered(form.getEMail())) {

			// NG:メールアドレスが重複
			bindingResult.rejectValue("eMail", "error.signupForm", "このメールアドレスは既に登録されています。");
			return getSignup(model, locale, form);
		}

		return "user/signup-comfirm";
	}

	@PostMapping("/signup")
	public String postSignup(Model model,
			Locale locale,
			@ModelAttribute SignupForm form,
			RedirectAttributes redirectAttributes) {
		// formをMUserクラスに変換
		MUser user = modelMapper.map(form, MUser.class);

		// ユーザー登録
		shoppingService.signup(user);

		redirectAttributes.addFlashAttribute("message", "ユーザーを登録しました。");

		return "redirect:/login";
	}

	/**
	 * ゲスト情報入力画面を表示します。
	 *
	 * @param model ビューにデータを提供するためのモデル
	 * @param locale ロケール情報
	 * @param form ゲストサインアップフォームのデータを保持するオブジェクト
	 * @return ゲスト登録画面のビュー名
	 */
	@GetMapping("/guest-signup")
	public String getGuestSignup(Model model,
			Locale locale,
			@ModelAttribute GuestSignupForm form) {
		// 都道府県を取得
		List<String> prefecturesList = userApplicationService.getPrefecturesList();
		model.addAttribute("prefecturesList", prefecturesList);

		// ゲスト登録画面に遷移
		return "guest/guest-signup";
	}

	/**
	 * ゲスト情報の登録処理を行います。
	 *
	 * @param model ビューにデータを提供するためのモデル
	 * @param locale ロケール情報
	 * @param form ゲストサインアップフォームのデータを保持するオブジェクト
	 * @param bindingResult 入力チェックの結果
	 * @return 入力チェックが成功した場合はレジ画面のビュー名、失敗した場合はゲスト登録画面のビュー名
	 */
	@PostMapping("/guest-signup-comfirm")
	public String postGuestSignupComfirm(Model model,
			Locale locale,
			@ModelAttribute @Validated(GroupOrder.class) GuestSignupForm form,
			BindingResult bindingResult) {

		// 入力チェック結果
		if (bindingResult.hasErrors()) {
			// NG:ゲスト登録画面に戻る
			return getGuestSignup(model, locale, form);
		}

		if (shoppingService.isEmailRegistered(form.getEMail())) {

			// NG:メールアドレスが重複
			bindingResult.rejectValue("eMail", "error.guestSignupForm", "このメールアドレスは既に登録されています。");
			return getGuestSignup(model, locale, form);
		}

		// レジ画面に遷移
		return "guest/guest-signup-comfirm";
	}

	@PostMapping("/guest-signup")
	public String postGuestSignup(Model model,
			Locale locale,
			@ModelAttribute GuestSignupForm form,
			RedirectAttributes redirectAttributes) {

		sessionGuestData.setGuestData(form);
		session.setAttribute("guestData", sessionGuestData.getGuestData());
		session.setAttribute("cart", cart.getCartList());
		session.setAttribute("totalAmount", cart.totalAmount());

		redirectAttributes.addFlashAttribute("message", "ゲスト情報を登録しました。");

		// レジ画面に遷移
		return "redirect:/goods/casher";
	}

}
