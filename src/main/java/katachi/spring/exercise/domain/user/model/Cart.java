package katachi.spring.exercise.domain.user.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import katachi.spring.exercise.domain.user.service.ShoppingService;

/**
 * ユーザーのカート情報を管理するクラスです。
 * カートはセッションスコープで管理され、ユーザーのセッション間で情報が共有されます。
 */
@SessionScope
@Component
public class Cart implements Serializable {

	@Autowired
	private ShoppingService shoppingService;

	private static final long serialVersionUID = 1L;

	private List<CartItem> cartList;

	/**
	 * カートに商品を追加します。
	 * もし同じ商品が既にカートに存在する場合は、数量を更新します。
	 *
	 * @param goods    追加する商品情報
	 * @param quantity 追加する数量
	 */
	public void addToCart(CartItem goods, Integer quantity) {
		if (cartList == null) {
			cartList = new ArrayList<>();
		}

		//すでに商品がカートに存在するかチェック
		for (CartItem cartItem : cartList) {
			if (cartItem.getGoodsId().equals(goods.getGoodsId())) {
				cartItem.setQuantity(cartItem.getQuantity() + quantity);
				return;
			}
		}

		//カートに新しい商品を追加
		CartItem newItem = new CartItem();
		newItem.setUserId(goods.getUserId()); // ユーザーIDを設定
		newItem.setGoodsId(goods.getGoodsId()); // 商品IDを設定
		newItem.setQuantity(goods.getQuantity()); // 数量を設定
		newItem.setGoodsName(goods.getGoodsName()); // 商品名を設定
		newItem.setImageUrl(goods.getImageUrl()); // 画像URLを設定
		newItem.setPrice(goods.getPrice()); //価格を設定
		newItem.setQuantity(quantity); //個数を設定
		cartList.add(newItem);
	}

	public void changeQuantity(Integer goodsId, Integer quantity) {
		// カート内の商品リストがnullであれば、何もせずに終了
		if (cartList == null) {
			return;
		}

		// カート内の各商品についてループし、goodsIdが一致する商品の個数を更新
		for (CartItem cartItem : cartList) {
			if (cartItem.getGoodsId().equals(goodsId)) {
				cartItem.setQuantity(quantity);
				return;
			}
		}
	}

	/**
	 * ゲストカートの商品をユーザーカートに追加します。
	 *
	 * @param userId   ユーザーID
	 * @param cartList ゲストカートの商品リスト
	 */
	public void transferCartItems(Integer userId, List<CartItem> cartList) {
		for (CartItem item : cartList) {

			if (shoppingService.checkCartItemExistence(userId,
					item.getGoodsId())) {
				shoppingService.addToCartItemQuantity(userId,
						item.getGoodsId(),
						item.getQuantity());

			} else {
				shoppingService.addToCart(userId,
						item.getGoodsId(),
						item.getQuantity());
			}
		}
	}

	/**
	 * カート内の合計数量を計算します。
	 *
	 * @return カート内の合計数量
	 */
	public int totalQuantity() {
		int totalQuantity = 0;
		for (CartItem cartItem : cartList) {
			totalQuantity += cartItem.getQuantity();
		}
		return totalQuantity;
	}

	/**
	 * カート内の合計金額を計算します。
	 *
	 * @return カート内の合計金額
	 */
	public int totalAmount() {
		int amount = 0;
		for (CartItem cartItem : cartList) {
			amount += (cartItem.getPrice()) * (cartItem.getQuantity());
		}
		return amount;
	}

	/**
	 * カート内の商品リストを取得します。
	 *
	 * @return カート内の商品リスト
	 */
	public List<CartItem> getCartList() {
		if (cartList == null) {
			cartList = new ArrayList<>();
		}
		return cartList;
	}

	/**
	 * ログインユーザーのカート情報を設定します。
	 *
	 * @param cartList カート情報
	 */
	public void setCartList(List<CartItem> cartList) {
		this.cartList = cartList;
	}

	/**
	 * カートから特定の商品を削除します。
	 *
	 * @param goodsId 削除する商品のID
	 */
	public void removeItemById(Integer goodsId) {
		Iterator<CartItem> iterator = cartList.iterator();
		while (iterator.hasNext()) {
			CartItem cartItem = iterator.next();
			if (cartItem.getGoodsId() == goodsId) {
				iterator.remove();
				break; // 見つかったらループを抜ける
			}
		}

	}

	/**
	 * カートを空にします。
	 */
	public void clearCart() {
		this.cartList = null;
	}

}
