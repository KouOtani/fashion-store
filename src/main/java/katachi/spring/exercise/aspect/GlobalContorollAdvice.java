package katachi.spring.exercise.aspect;

import java.io.IOException;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalContorollAdvice {

	/*データベース関連の例外処理*/
	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionHandler(DataAccessException e, Model model) {

		//空文字をセット
		model.addAttribute("error", "");

		//メッセージをModelに登録
		model.addAttribute("message", "DataAccessExceptionが発生しました");

		//HTTPのエラーコード（500）をModelに登録
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

		return "error/error";
	}

	@ExceptionHandler(IOException.class)
	public String handleIOException(IOException ex,
			RedirectAttributes redirectAttributes) {
		// ログ出力
		ex.printStackTrace();
		// エラーメッセージを表示
		redirectAttributes.addFlashAttribute("message", "ファイルのアップロードに失敗しました。");
		return "admin/goods-management";
	}

	/*その他の例外処理*/
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e, Model model) {

		//空文字をセット
		model.addAttribute("error", "");

		//メッセージをModelに登録
		model.addAttribute("message", "Exceptionが発生しました");

		//HTTPのエラーコード（500）をModelに登録
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

		return "error/error";
	}
}
