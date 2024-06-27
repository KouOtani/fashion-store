package katachi.spring.exercise.domain.user.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

/**
 * ユーザーのカート情報を管理するクラスです。
 * カートはセッションスコープで管理され、ユーザーのセッション間で情報が共有されます。
 */
@SessionScope
@Component
public class Cart implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<CartItem> cartList = new ArrayList<>();

	/**
	 * カートに商品を追加します。
	 * もし同じ商品が既にカートに存在する場合は、数量を更新します。
	 *
	 * @param item    追加する商品情報
	 * @param quantity 追加する数量
	 */
	public void addToCart(CartItem item, Integer quantity) {
		//すでに商品がカートに存在するかチェック
		for (CartItem cartItem : cartList) {
			if (cartItem.getGoodsId().equals(item.getGoodsId())) {
				cartItem.setQuantity(cartItem.getQuantity() + quantity);
				return;
			}
		}
		item.setQuantity(quantity);
		cartList.add(item);
	}

	/**
	 * カート内の特定の商品の数量を変更します。
	 *
	 * @param itemId   数量を変更する商品のID
	 * @param quantity 新しい数量
	 */
	public void changeQuantity(Integer itemId, Integer quantity) {
		// カート内の各商品についてループし、itemIdが一致する商品の数量を更新
		for (CartItem cartItem : cartList) {
			if (cartItem.getGoodsId().equals(itemId)) {
				cartItem.setQuantity(quantity);
				return;
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
		cartList.removeIf(item -> item.getGoodsId() == itemId);
	}

	/**
	 * カートを空にします。
	 */
	public void clearCart() {
		cartList.clear();
	}

}
