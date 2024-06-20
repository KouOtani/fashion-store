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
	 * @param item    追加する商品情報
	 * @param quantity 追加する数量
	 */
	public void addToCart(CartItem item, Integer quantity) {
		if (cartList == null) {
			cartList = new ArrayList<>();
		}

		//すでに商品がカートに存在するかチェック
		for (CartItem cartItem : cartList) {
			if (cartItem.getGoodsId().equals(item.getGoodsId())) {
				cartItem.setQuantity(cartItem.getQuantity() + quantity);
				return;
			}
		}

		//カートに新しい商品を追加
		CartItem newItem = new CartItem();
		newItem.setUserId(item.getUserId()); // ユーザーIDを設定
		newItem.setGoodsId(item.getGoodsId()); // 商品IDを設定
		newItem.setQuantity(item.getQuantity()); // 数量を設定
		newItem.setGoodsName(item.getGoodsName()); // 商品名を設定
		newItem.setImageUrl(item.getImageUrl()); // 画像URLを設定
		newItem.setPrice(item.getPrice()); //価格を設定
		newItem.setQuantity(quantity); //個数を設定
		cartList.add(newItem);
	}

	/**
	 * カート内の特定の商品の数量を変更します。
	 *
	 * @param itemId   数量を変更する商品のID
	 * @param quantity 新しい数量
	 */
	public void changeQuantity(Integer itemId, Integer quantity) {
		// カート内の商品リストがnullであれば、何もせずに終了
		if (cartList == null) {
			return;
		}

		// カート内の各商品についてループし、itemIdが一致する商品の数量を更新
		for (CartItem cartItem : cartList) {
			if (cartItem.getGoodsId().equals(itemId)) {
				cartItem.setQuantity(quantity);
				return;
			}
		}
	}

	/**
	 * ゲストカートの商品をユーザーカートに追加します。
	 *
	 * @param userId   ユーザーID
	 * @param guestCartItems ゲストカートの商品リスト
	 */
	public void transferCartItems(Integer userId, List<CartItem> guestCartItems) {
		for (CartItem item : guestCartItems) {

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
		for (CartItem item : cartList) {
			totalQuantity += item.getQuantity();
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
		for (CartItem item : cartList) {
			amount += (item.getPrice()) * (item.getQuantity());
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
	public void removeItemById(Integer itemId) {
		Iterator<CartItem> iterator = cartList.iterator();
		while (iterator.hasNext()) {
			CartItem cartItem = iterator.next();
			if (cartItem.getGoodsId() == itemId) {
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
