package katachi.spring.exercise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ログインページに関連するリクエストを処理するコントローラークラスです。
 */
@Controller
public class LoginController {

	/**
	 * ログインページを表示します。
	 *
	 * @return ログインページのビュー名
	 */
	@GetMapping("/login")
	public String getLogin() {
		return "login/login";
	}
}
