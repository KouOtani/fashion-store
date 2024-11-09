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
	 * @param model ビューに渡すためのモデル
	 * @param locale ロケール情報
	 * @param signupForm サインアップフォームのデータを保持するオブジェクト
	 * @return ユーザー登録画面のビュー名
	 */
	@GetMapping("/signup")
	public String showSignupForm(Model model,
			Locale locale,
			@ModelAttribute SignupForm signupForm) {
		// 性別の選択肢を取得
		Map<String, Integer> genderMap = userApplicationService.getGenderMap(locale);
		model.addAttribute("genderMap", genderMap);

		// 都道府県の選択肢を取得
		List<String> prefecturesList = userApplicationService.getPrefecturesList();
		model.addAttribute("prefecturesList", prefecturesList);

		// ユーザー登録画面を表示
		return "user/signup";
	}

	@GetMapping("/signup-confirm")
	public String confirmSignup() {

		// ログイン画面にリダイレクト
		return "redirect:/login";
	}

	/**
	 * ユーザー登録フォームの入力チェックを行い、確認画面に遷移します。
	 *
	 * @param model ビューに渡すためのモデル
	 * @param locale ロケール情報
	 * @param signupForm サインアップフォームのデータを保持するオブジェクト
	 * @param bindingResult 入力チェック結果
	 * @return 入力チェックが成功した場合は確認画面のビュー名、失敗した場合はユーザー登録画面のビュー名
	 */
	@PostMapping("/signup-confirm")
	public String confirmSignup(Model model,
			Locale locale,
			@ModelAttribute @Validated(GroupOrder.class) SignupForm signupForm,
			BindingResult bindingResult) {
		// 入力チェック結果を確認
		if (bindingResult.hasErrors()) {
			// NG:ユーザー登録画面に戻る
			return showSignupForm(model, locale, signupForm);
		}

		// パスワードと確認用パスワードの一致を確認
		if (!signupForm.getPassword().equals(signupForm.getRePassword())) {
			bindingResult.rejectValue("password", "error.signupForm", "確認用のパスワードと一致しません。");
			return showSignupForm(model, locale, signupForm);
		}

		// メールアドレスの重複を確認
		if (shoppingService.isEmailRegistered(signupForm.getEMail())) {
			bindingResult.rejectValue("eMail", "error.signupForm", "このメールアドレスは既に登録されています。");
			return showSignupForm(model, locale, signupForm);
		}

		// 確認画面に遷移
		return "user/signup-confirm";
	}

	/**
	 * ユーザーを登録し、ログイン画面にリダイレクトします。
	 *
	 * @param model ビューに渡すためのモデル
	 * @param locale ロケール情報
	 * @param signupForm サインアップフォームのデータを保持するオブジェクト
	 * @param redirectAttributes リダイレクト先にデータを渡すためのオブジェクト
	 * @return ログイン画面へのリダイレクトURL
	 */
	@PostMapping("/signup")
	public String processSignup(Model model,
			Locale locale,
			@ModelAttribute SignupForm signupForm,
			RedirectAttributes redirectAttributes) {
		// サインアップフォームをMUserクラスにマッピング
		MUser user = modelMapper.map(signupForm, MUser.class);

		// ユーザーを登録
		shoppingService.registerUser(user);

		redirectAttributes.addFlashAttribute("message", "ユーザーを登録しました。");

		// ログイン画面にリダイレクト
		return "redirect:/login";
	}

	/**
	 * ゲスト情報入力画面を表示します。
	 *
	 * @param model ビューに渡すためのモデル
	 * @param locale ロケール情報
	 * @param guestSignupForm ゲストサインアップフォームのデータを保持するオブジェクト
	 * @return ゲスト情報入力画面のビュー名
	 */
	@GetMapping("/guest-signup")
	public String showGuestSignupForm(Model model,
			Locale locale,
			@ModelAttribute GuestSignupForm guestSignupForm) {
		// 都道府県の選択肢を取得
		List<String> prefecturesList = userApplicationService.getPrefecturesList();
		model.addAttribute("prefecturesList", prefecturesList);

		// ゲスト情報入力画面を表示
		return "guest/guest-signup";
	}

	/**
	 * ゲスト情報の入力チェックを行い、レジ画面に遷移します。
	 *
	 * @param model ビューに渡すためのモデル
	 * @param locale ロケール情報
	 * @param guestSignupForm ゲストサインアップフォームのデータを保持するオブジェクト
	 * @param bindingResult 入力チェック結果
	 * @return 入力チェックが成功した場合はレジ画面のビュー名、失敗した場合はゲスト情報入力画面のビュー名
	 */
	@PostMapping("/guest-signup-confirm")
	public String confirmGuestSignup(Model model,
			Locale locale,
			@ModelAttribute @Validated(GroupOrder.class) GuestSignupForm guestSignupForm,
			BindingResult bindingResult) {

		// 入力チェック結果を確認
		if (bindingResult.hasErrors()) {
			// NG:ゲスト情報入力画面に戻る
			return showGuestSignupForm(model, locale, guestSignupForm);
		}

		// メールアドレスの重複を確認
		if (shoppingService.isEmailRegistered(guestSignupForm.getEMail())) {
			bindingResult.rejectValue("eMail", "error.guestSignupForm", "このメールアドレスは既に登録されています。");
			return showGuestSignupForm(model, locale, guestSignupForm);
		}

		// レジ画面に遷移
		return "guest/guest-signup-confirm";
	}

	/**
	 * ゲスト情報を登録し、レジ画面にリダイレクトします。
	 *
	 * @param model ビューに渡すためのモデル
	 * @param locale ロケール情報
	 * @param guestSignupForm ゲストサインアップフォームのデータを保持するオブジェクト
	 * @param redirectAttributes リダイレクト先にデータを渡すためのオブジェクト
	 * @return レジ画面へのリダイレクトURL
	 */
	@PostMapping("/guest-signup")
	public String processGuestSignup(Model model,
			Locale locale,
			@ModelAttribute GuestSignupForm guestSignupForm,
			RedirectAttributes redirectAttributes) {

		// ゲストデータをセッションに設定
		sessionGuestData.setGuestData(guestSignupForm);
		session.setAttribute("guestData", sessionGuestData.getGuestData());
		session.setAttribute("cart", cart.getCartList());
		session.setAttribute("totalAmount", cart.totalAmount());

		redirectAttributes.addFlashAttribute("message", "ゲスト情報を登録しました。");

		// レジ画面にリダイレクト
		return "redirect:/order/checkout";
	}

}
