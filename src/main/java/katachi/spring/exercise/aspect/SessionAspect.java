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
	public void getPreUrl() {
		String prevUrl = request.getHeader("Referer");
		if (prevUrl != null && !prevUrl.endsWith("/login")) {
			session.setAttribute("prevUrl", prevUrl);
		}
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
