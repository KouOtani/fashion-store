package katachi.spring.exercise.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ログアウト成功時の処理を行うためのカスタムログアウト成功ハンドラです。
 * ログアウト成功時にはログインページにリダイレクトします。
 */
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

	/**
	 * ログアウト成功時の処理を実行します。
	 * ログアウト後にログインページにリダイレクトします。
	 *
	 * @param request  HTTPリクエスト
	 * @param response HTTPレスポンス
	 * @param authentication 認証情報
	 * @throws IOException 入出力エラーが発生した場合
	 * @throws ServletException サーブレットエラーが発生した場合
	 */
	@Override
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// HTTPレスポンスのステータスを200 OKに設定
		response.setStatus(HttpServletResponse.SC_OK);
		// ログアウト後にログインページにリダイレクト
		response.sendRedirect("/login?logout");
	}
}
