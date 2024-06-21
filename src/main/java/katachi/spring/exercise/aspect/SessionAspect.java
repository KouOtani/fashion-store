package katachi.spring.exercise.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import katachi.spring.exercise.domain.user.model.Cart;

/**
 * セッションに関する処理を行うアスペクトクラスです。
 */
@Aspect
@Component
public class SessionAspect {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private Cart cart;

	@Autowired
	private HttpSession session;

	/**
	 * ログイン前のリクエストURLをセッションに保存します。
	 */
	@Before("execution(* katachi.spring.exercise.controller.LoginController.*(..))")
	public void savePreviousUrl() {
		// リクエストのリファラヘッダーから前のURLを取得
		String previousUrl = request.getHeader("Referer");

		// URLが有効かどうかをチェック
		if (isValidPreviousUrl(previousUrl)) {
			// 前のURLをセッションに保存
			session.setAttribute("previousUrl", previousUrl);
		}
	}

	/**
	 * URLが有効かどうかを判断します。
	 *
	 * @param url チェックするURL
	 * @return URLが有効であればtrue、無効であればfalse
	 */
	private boolean isValidPreviousUrl(String url) {
		if (url == null) {
			return false;
		}
		return !(url.endsWith("/login") || url.endsWith("/signup") || url.endsWith("/guest-signup"));
	}

	/**
	 * カートの総数量をセッションに設定します。
	 */
	@After("@within(org.springframework.stereotype.Controller)")
	public void setTotalQuantity() {
		if (!cart.getCartList().isEmpty()) {
			session.setAttribute("totalQuantity", cart.totalQuantity());
		}
	}

}
