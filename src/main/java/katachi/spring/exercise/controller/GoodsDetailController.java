package katachi.spring.exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import katachi.spring.exercise.domain.user.model.MGoods;
import katachi.spring.exercise.domain.user.service.ShoppingService;

/**
 * 商品の詳細ページに関連するリクエストを処理するコントローラークラスです。
 */
@Controller
@RequestMapping("/goods")
public class GoodsDetailController {

	@Autowired
	private ShoppingService shoppingService;

	/**
	 * 商品詳細ページを表示します。
	 *
	 * @param model ビューにデータを提供するためのモデル
	 * @param id 詳細を表示する商品のID
	 * @return 商品詳細ページのビュー名
	 */
	@GetMapping("/detail/{id}")
	public String getGoods(Model model, @PathVariable("id") Integer id) {

		// 商品を1件取得
		MGoods goodsOne = shoppingService.getGoodsOne(id);

		// モデルに商品情報を追加
		model.addAttribute("goodsOne", goodsOne);

		return "goods/detail";
	}
}
