package katachi.spring.exercise.domain.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import katachi.spring.exercise.domain.user.model.CartItem;
import katachi.spring.exercise.domain.user.model.DeliveryAddress;
import katachi.spring.exercise.domain.user.model.MGoods;
import katachi.spring.exercise.domain.user.model.MUser;
import katachi.spring.exercise.domain.user.model.Order;
import katachi.spring.exercise.domain.user.model.OrderDetails;
import katachi.spring.exercise.domain.user.service.ShoppingService;
import katachi.spring.exercise.repository.UserMapper;

/**
 * ユーザー関連のサービスを提供するクラスです。
 */
@Service
public class ShoppingServiceImpl implements ShoppingService {

	@Autowired
	private UserMapper mapper;

	@Autowired
	private PasswordEncoder encoder;

	//ユーザー登録
	@Override
	public void signup(MUser user) {
		user.setRole("ROLE_GENERAL");

		//パスワード暗号化
		String rawPassword = user.getPassword();
		user.setPassword(encoder.encode(rawPassword));

		mapper.insertOne(user);
	}

	//メールアドレスの重複チェックを
	public boolean isEmailRegistered(String email) {
		return mapper.findByEmail(email);
	}

	//アイテム取得
	@Override
	public List<MGoods> getGoods(Integer page, Integer size) {
		Integer offset = (page - 1) * size;
		return mapper.selectGoodsWithPagination(offset, size);
	}

	//アイテムの合計数を取得
	@Override
	public Integer getTotalGoodsCount() {
		return mapper.getTotalGoodsCount();
	}

	//アイテム取得（1件）
	@Override
	public MGoods getGoodsOne(Integer goodsId) {
		return mapper.selectOneGoods(goodsId);
	}

	//アイテム取得（全件）
	@Override
	public List<MGoods> getGoods() {
		return mapper.selectAllGoods();
	}

	/*アイテム更新(1件)*/
	@Override
	public void updateGoodsOne(MGoods goods) {
		mapper.updateGoods(goods);
	}

	/*アイテム削除(1件)*/
	@Override
	public void deleteGoodsOne(Integer goodsId) {
		mapper.deleteGoods(goodsId);
	}

	/*アイテム登録*/
	@Override
	public void registerNewGoods(MGoods goods) {
		mapper.insertGoods(goods);
	}

	//ログインユーザー情報取得
	@Override
	public MUser getLoginUserByEmail(String eMail) {
		return mapper.findLoginUserByEmail(eMail);
	}

	//ログインユーザー情報取得
	@Override
	public MUser getLoginUserById(Integer userId) {
		return mapper.findLoginUserById(userId);
	}

	//カートに追加(ログインユーザー)
	@Override
	public void addToCart(Integer userId,
			Integer goodsId,
			Integer quantity) {

		mapper.saveToCart(userId, goodsId, quantity);
	}

	//カートに同じ商品があるか確認する
	@Override
	public boolean checkCartItemExistence(Integer userId,
			Integer goodsId) {
		if (mapper.findTwo(userId, goodsId) == null) {
			return false;
		} else {
			return true;
		}
	}

	//同じ商品に個数を加算する
	@Override
	public void addToCartItemQuantity(Integer userId,
			Integer goodsId,
			Integer quantity) {
		mapper.addQuantity(userId, goodsId, quantity);
	}

	//同じ商品の個数を変更する
	public void changeCartItemQuantity(Integer userId,
			Integer goodsId,
			Integer quantity) {
		mapper.updateQuantity(userId, goodsId, quantity);
	}

	//カート取得
	@Override
	public List<CartItem> getCartList(Integer userId) {
		return mapper.findCartInformation(userId);
	}

	//カート内の商品を1件削除
	@Override
	public void deleteItem(Integer userId, Integer goodsId) {
		mapper.deleteItem(userId, goodsId);
	}

	//カート内の商品を全件削除
	@Override
	public void allClearCart(Integer userId) {
		mapper.deleteAllItem(userId);
	}

	//ユーザー情報変更(メールアドレス)
	@Override
	public void addressUpdate(Integer userId, String newAdress) {
		mapper.updateAddress(userId, newAdress);
	}

	//ユーザー情報変更(パスワード)
	@Override
	public void passwordUpdate(Integer userId, String newPassword) {

		//パスワード暗号化
		String encryptPassword = encoder.encode(newPassword);
		mapper.updatePassword(userId, encryptPassword);
	}

	//ユーザー情報変更(詳細)
	@Override
	public void detailUpdate(Integer userId, MUser user) {
		mapper.updateMany(userId, user);
	}

	//ユーザー配送先住所変更
	@Override
	public void shippingAddressUpdate(Integer userId, MUser user) {
		mapper.updateShippingAddress(userId, user);
	}

	//商品購入者、日時登録
	@Override
	public void saveCustomer(Order order) {
		mapper.insertOrderOne(order);
	}

	//購入商品の詳細を登録
	@Override
	public void saveOrder(List<OrderDetails> orderDetailsList) {

		for (OrderDetails element : orderDetailsList) {
			mapper.insertOrderDetail(element);
		}

	}

	//購入商品の配送先を登録
	@Override
	public void saveDeliveryAddress(DeliveryAddress address) {
		mapper.insertDeliveryAddress(address);
	}

	//ユーザーの購入履歴を取得
	@Override
	public List<Order> getHistories(Integer userId) {
		return mapper.findManyHistories(userId);
	}

	//購入履歴を取得（1件）
	@Override
	public Order getOrderDetailsOne(Integer userId, Integer orderId) {
		return mapper.findOneHistory(userId, orderId);
	}

	//全ユーザーの注文履歴を取得
	@Override
	public List<Order> getAllOrders(String query) {
		return mapper.selectAllOrders(query);
	}

	//注文の詳細を取得
	@Override
	public Order getUserOrders(Integer orderId) {
		return mapper.selectUserOrders(orderId);
	}

	//ユーザー一覧を取得
	@Override
	public List<MUser> getUsers(String query) {
		return mapper.selectAllUsers(query);
	}

	//顧客情報を取得（1件）
	@Override
	public MUser getCustomerOne(Integer userId) {
		return mapper.selectUser(userId);
	}

	//顧客情報を更新
	@Override
	public void updateCustomer(MUser user) {
		mapper.updateUser(user);
	}

	//顧客情報を削除
	@Override
	public void deleteCustomer(Integer userId) {
		mapper.deleteUser(userId);
	}
}
