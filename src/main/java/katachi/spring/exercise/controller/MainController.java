package katachi.spring.exercise.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import katachi.spring.exercise.domain.user.model.MGoods;
import katachi.spring.exercise.domain.user.service.ShoppingService;

/**
 * メインページに関連するリクエストを処理するコントローラークラスです。
 */
@Controller
public class MainController {

	@Autowired
	private ShoppingService shoppingService;

	/**
	 * メインページを表示します。
	 *
	 * @param model ビューをレンダリングするためのデータを格納するモデル
	 * @param page 表示するページ番号（デフォルトは1）
	 * @param size 1ページあたりのアイテム数（デフォルトは8）
	 * @return メインページのビュー名
	 */
	@GetMapping("/")
	public String showMainPage(Model model,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "8") Integer size) {

		// 商品/アイテムのリストを取得します。
		List<MGoods> goodsList = shoppingService.getGoodsWithPagination(page, size);

		// アイテムの総数とアイテム数に基づいて総ページ数を計算します。
		Integer totalItems = shoppingService.getTotalGoodsCount();
		Integer totalPages = (int) Math.ceil((double) totalItems / size);

		// ページ番号のリストを生成します。
		List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
				.boxed()
				.collect(Collectors.toList());

		// モデルに属性を追加します。
		model.addAttribute("goodsList", goodsList);
		model.addAttribute("page", page);
		model.addAttribute("size", size);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("pageNumbers", pageNumbers);

		return "index";
	}

}
