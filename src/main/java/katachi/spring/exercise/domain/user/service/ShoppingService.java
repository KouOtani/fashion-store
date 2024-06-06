package katachi.spring.exercise.domain.user.service;

import java.util.List;

import katachi.spring.exercise.domain.user.model.CartItem;
import katachi.spring.exercise.domain.user.model.DeliveryAddress;
import katachi.spring.exercise.domain.user.model.MGoods;
import katachi.spring.exercise.domain.user.model.MUser;
import katachi.spring.exercise.domain.user.model.Order;
import katachi.spring.exercise.domain.user.model.OrderDetails;

/**
 * ユーザー関連のサービスで提供される操作を定義するインターフェースです。
 */
public interface ShoppingService {

	//ユーザー登録
	public void signup(MUser user);

	//アイテム取得
	public List<MGoods> getGoods(Integer page, Integer size);

	//アイテムの合計数を取得
	public Integer getTotalGoodsCount();

	//アイテム取得（1件）
	public MGoods getGoodsOne(Integer id);

	//アイテム取得（複数）
	public List<MGoods> getGoods();

	//アイテム更新（1件）
	public void updateGoodsOne(MGoods goods);

	//アイテム削除（1件）
	public void deleteGoodsOne(Integer id);

	//アイテム登録
	public void registerNewGoods(MGoods goods);

	//ログインユーザー情報取得
	public MUser getLoginUserByEmail(String eMail);

	//ログインユーザー情報取得
	public MUser getLoginUserById(Integer userId);

	//カートに追加(ログインユーザー)
	public void addToCart(Integer userId,
			Integer goodsId,
			Integer quantity);

	//カートに同じ商品があるか確認する
	public boolean checkCartItemExistence(Integer userId,
			Integer goodsId);

	//同じ商品に個数を加算する
	public void addToCartItemQuantity(Integer userId,
			Integer goodsId,
			Integer quantity);

	//同じ商品の個数を変更する
	public void changeCartItemQuantity(Integer userId,
			Integer goodsId,
			Integer quantity);

	//カート取得
	public List<CartItem> getCartList(Integer userId);

	//カート内の商品を1件削除
	public void deleteItem(Integer userId, Integer goodsId);

	//カート内の商品を全件削除
	public void allClearCart(Integer userId);

	//ユーザー情報変更(メールアドレス)
	public void addressUpdate(Integer userId, String newAdress);

	//ユーザー情報変更(パスワード)
	public void passwordUpdate(Integer userId, String newPassword);

	//ユーザー情報変更(詳細)
	public void detailUpdate(Integer userId, MUser user);

	//ユーザー配送先住所変更
	public void shippingAddressUpdate(Integer userId, MUser user);

	//商品購入者、日時登録
	public void saveCustomer(Order order);

	//購入商品の詳細を登録
	public void saveOrder(List<OrderDetails> orderDetailsList);

	//購入商品の配送先を登録
	public void saveDeliveryAddress(DeliveryAddress address);

	//購入履歴を取得
	public List<Order> getHistories(Integer userId);

	//購入履歴を取得（1件）
	public Order getOrderDetailsOne(Integer userId, Integer orderId);

	//全ユーザーの注文履歴を取得
	public List<Order> getAllOrders(String query);

	//注文の詳細を取得
	public Order getUserOrders(Integer orderId);

	//ユーザー一覧を取得
	public List<MUser> getUsers(String query);

	//顧客情報を取得（1件）
	public MUser getCustomerOne(Integer userId);

	//顧客情報を更新
	public void updateCustomer(MUser user);

	//顧客情報を削除
	public void deleteCustomer(Integer userId);
}
