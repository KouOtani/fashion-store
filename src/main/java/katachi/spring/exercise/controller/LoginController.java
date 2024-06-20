package katachi.spring.exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

/**
 * ログインページに関連するリクエストを処理するコントローラークラスです。
 */
@Controller
public class LoginController {

	@Autowired
	private HttpSession session;

	/**
	 * ログインページを表示します。
	 *
	 * @return ログインページのビュー名
	 */
	@GetMapping("/login")
	public String showLoginPage() {
		return "login/login";
	}

	/**
	 * ゲストユーザーをログアウトさせ、ログインページにリダイレクトします。
	 *
	 * @return ログインページへのリダイレクト
	 */
	@GetMapping("/guest/logout")
	public String logoutGuestUser() {
		session.invalidate();
		return "redirect:/login";
	}
}
