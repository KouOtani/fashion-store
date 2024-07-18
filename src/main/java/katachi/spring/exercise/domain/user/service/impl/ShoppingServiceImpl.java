package katachi.spring.exercise.domain.user.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import katachi.spring.exercise.domain.user.model.CartItem;
import katachi.spring.exercise.domain.user.model.DeliveryAddress;
import katachi.spring.exercise.domain.user.model.MGoods;
import katachi.spring.exercise.domain.user.model.MUser;
import katachi.spring.exercise.domain.user.model.MonthlySales;
import katachi.spring.exercise.domain.user.model.Order;
import katachi.spring.exercise.domain.user.model.OrderDetails;
import katachi.spring.exercise.domain.user.service.ShoppingService;
import katachi.spring.exercise.repository.EcMapper;

/**
 * ユーザー関連のサービスを提供するクラスです。
 */
@Service
public class ShoppingServiceImpl implements ShoppingService {

	@Autowired
	private EcMapper mapper;

	@Autowired
	private PasswordEncoder encoder;

	/**
	 * ユーザーを新規登録します。
	 *
	 * @param user 登録するユーザー情報
	 */
	@Override
	public void registerUser(MUser user) {
		user.setRole("ROLE_GENERAL");

		//パスワード暗号化
		String rawPassword = user.getPassword();
		user.setPassword(encoder.encode(rawPassword));

		mapper.insertUser(user);
	}

	/**
	* メールアドレスが登録済みかどうかを確認します。
	*
	* @param email 確認するメールアドレス
	* @return 登録済みの場合はtrue、そうでない場合はfalse
	*/
	@Override
	public boolean isEmailRegistered(String email) {
		return mapper.findUserByEmail(email) != null;
	}

	/**
	 * ページネーションを使用してアイテムを取得します。
	 *
	 * @param page ページ番号
	 * @param size 1ページあたりのアイテム数
	 * @return 指定されたページに表示されるアイテムのリスト
	 */
	@Override
	public List<MGoods> getGoodsWithPagination(Integer page, Integer size) {
		Integer offset = (page - 1) * size;
		return mapper.findGoodsWithPagination(offset, size);
	}

	/**
	 * アイテムの総数を取得します。
	 *
	 * @return アイテムの総数
	 */
	@Override
	public Integer getTotalGoodsCount() {
		return mapper.countAllGoods();
	}

	/**
	 * 特定のアイテムを取得します。
	 *
	 * @param goodsId 取得するアイテムのID
	 * @return 指定されたIDのアイテム情報
	 */
	@Override
	public MGoods getGoodsOne(Integer goodsId) {
		return mapper.selectOneGoods(goodsId);
	}

	/**
	 * 全てのアイテムを取得します。
	 *
	 * @return 全てのアイテムのリスト
	 */
	@Override
	public List<MGoods> getGoods() {
		return mapper.selectAllGoods();
	}

	/**
	 * 特定のアイテムを更新します。
	 *
	 * @param goods 更新するアイテム情報
	 */
	@Override
	public void updateGoods(MGoods goods) {
		mapper.updateGoods(goods);
	}

	/**
	 * 特定のアイテムを削除します。
	 *
	 * @param goodsId 削除するアイテムのID
	 */
	@Override
	public void deleteGoods(Integer goodsId) {
		mapper.deleteGoods(goodsId);
	}

	/**
	 * 新しいアイテムを登録します。
	 *
	 * @param goods 登録するアイテム情報
	 */
	@Override
	public void registerNewGoods(MGoods goods) {
		mapper.insertGoods(goods);
	}

	/**
	 * 指定されたメールアドレスに対応するログインユーザー情報を取得します。
	 *
	 * @param email 取得するログインユーザーのメールアドレス
	 * @return 指定されたメールアドレスのログインユーザー情報
	 */
	@Override
	public MUser getLoginUserByEmail(String eMail) {
		return mapper.findLoginUserByEmail(eMail);
	}

	/**
	 * 指定されたユーザーIDに対応するログインユーザー情報を取得します。
	 *
	 * @param userId 取得するログインユーザーのID
	 * @return 指定されたユーザーIDのログインユーザー情報
	 */
	@Override
	public MUser getLoginUserById(Integer userId) {
		return mapper.findLoginUserById(userId);
	}

	/**
	 * ログインユーザーのカートにアイテムを追加します。
	 *
	 * @param userId   カートに追加するユーザーのID
	 * @param goodsId  カートに追加するアイテムのID
	 * @param quantity カートに追加するアイテムの数量
	 */
	@Override
	public void addToCart(Integer userId,
			Integer goodsId,
			Integer quantity) {
		Integer cartItem = mapper.checkCartItemExistence(userId, goodsId);
		if (cartItem != null) {
			mapper.incrementCartItemQuantity(userId, goodsId, quantity);
		} else {
			mapper.saveToCart(userId, goodsId, quantity);
		}
	}

	/**
	 * 指定されたユーザーのカート内の同じ商品の数量を変更します。
	 *
	 * @param userId   カートを更新するユーザーのID
	 * @param goodsId  更新する商品のID
	 * @param quantity 新しい数量
	 */
	public void changeCartItemQuantity(Integer userId,
			Integer goodsId,
			Integer quantity) {
		mapper.updateQuantity(userId, goodsId, quantity);
	}

	/**
	* 指定されたユーザーのカート情報を取得します。
	*
	* @param userId 取得するカート情報のユーザーID
	* @return 指定されたユーザーのカート情報
	*/
	@Override
	public List<CartItem> getCartList(Integer userId) {
		return mapper.findUserCartItems(userId);
	}

	/**
	* 指定されたユーザーのカートから特定の商品を削除します。
	*
	* @param userId  カートから削除するユーザーのID
	* @param goodsId 削除する商品のID
	*/
	@Override
	public void deleteItem(Integer userId, Integer goodsId) {
		mapper.deleteItem(userId, goodsId);
	}

	/**
	 * 指定されたユーザーのカート内の全ての商品を削除します。
	 *
	 * @param userId カートをクリアするユーザーのID
	 */
	@Override
	public void allClearCart(Integer userId) {
		mapper.deleteAllItem(userId);
	}

	/**
	 * ゲストカートの商品をユーザーカートに追加します。
	 *
	 * @param userId   ユーザーID
	 * @param guestCartItems ゲストカートの商品リスト
	 */
	public void transferCartItems(Integer userId, List<CartItem> guestCartItems) {
		for (CartItem item : guestCartItems) {
			addToCart(userId, item.getGoodsId(), item.getQuantity());
		}
	}

	/**
	 * 指定されたユーザーの配送先住所を更新します。
	 *
	 * @param userId    更新するユーザーのID
	 * @param newAdress 新しい配送先住所
	 */
	@Override
	public void addressUpdate(Integer userId, String newAdress) {
		mapper.updateAddress(userId, newAdress);
	}

	/**
	* 指定されたユーザーのパスワードを更新します。
	*
	* @param userId      更新するユーザーのID
	* @param newPassword 新しいパスワード
	*/
	@Override
	public void passwordUpdate(Integer userId, String newPassword) {

		//パスワード暗号化
		String encryptPassword = encoder.encode(newPassword);
		mapper.updatePassword(userId, encryptPassword);
	}

	/**
	* 指定されたユーザーの詳細情報を更新します。
	*
	* @param userId 更新するユーザーのID
	* @param user   更新するユーザーの詳細情報
	*/
	@Override
	public void detailUpdate(Integer userId, MUser user) {
		mapper.updateMany(userId, user);
	}

	/**
	* 指定されたユーザーの配送先住所を更新します。
	*
	* @param userId 更新するユーザーのID
	* @param user   更新するユーザーの詳細情報（配送先住所のみ）
	*/
	@Override
	public void shippingAddressUpdate(Integer userId, MUser user) {
		mapper.updateShippingAddress(userId, user);
	}

	/**
	 * 指定された注文情報を保存します。
	 *
	 * @param orderDetailsList 保存する注文情報のリスト
	 */
	@Override
	public void saveCustomer(Order order) {
		mapper.insertOrder(order);
	}

	//注文商品の詳細を登録
	@Override
	public void saveOrder(List<OrderDetails> orderDetailsList) {
		for (OrderDetails element : orderDetailsList) {
			mapper.insertOrderDetails(element);
		}
	}

	/**
	 * 指定された配送先情報を保存します。
	 *
	 * @param address 保存する配送先情報
	 */
	@Override
	public void saveDeliveryAddress(DeliveryAddress address) {
		mapper.insertDeliveryAddress(address);
	}

	/**
	 * 月次売上を更新するメソッド。
	 * 指定された注文日付に基づいて売上データを更新します。
	 *
	 * @param orderDate 注文日付
	 * @param sales 売上金額
	 */
	@Override
	public void updateMonthlySales(Date orderDate, BigDecimal sales) {
		int year = getYearFromDate(orderDate);
		int month = getMonthFromDate(orderDate);

		mapper.updateMonthlySales(year, month, sales);
	}

	/**
	 * 指定された日付から年を取得するヘルパーメソッド。
	 *
	 * @param date 日付
	 * @return 年
	 */
	private int getYearFromDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 指定された日付から月を取得するヘルパーメソッド。
	 * Calendar.MONTHは0から11の範囲を返すため、1を加算して1から12の範囲とする。
	 *
	 * @param date 日付
	 * @return 月（1から12の範囲）
	 */
	private int getMonthFromDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	//月別の売り上げを取得する
	public List<MonthlySales> getMonthlySales() {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR); // 今年の年を取得するロジック
		return mapper.getMonthlySales(currentYear);
	}

	/**
	 * 指定されたユーザーの注文履歴を取得します。
	 *
	 * @param userId 取得する注文履歴のユーザーID
	 * @return 指定されたユーザーの注文履歴のリスト
	 */
	@Override
	public List<Order> getHistories(Integer userId) {
		return mapper.findManyHistories(userId);
	}

	/**
	 * 指定されたユーザーと注文IDに対応する注文詳細情報を取得します。
	 *
	 * @param userId  取得する注文詳細情報のユーザーID
	 * @param orderId 取得する注文詳細情報の注文ID
	 * @return 指定されたユーザーと注文IDに対応する注文詳細情報
	 */
	@Override
	public Order getOrderDetailsOne(Integer userId, Integer orderId) {
		return mapper.findOneHistory(userId, orderId);
	}

	/**
	 * 指定されたクエリに基づく全ての注文情報を取得します。
	 *
	 * @param query 検索クエリ
	 * @return 指定されたクエリに基づく全ての注文情報のリスト
	 */
	@Override
	public List<Order> getAllOrders(String query) {
		return mapper.selectAllOrders(query);
	}

	/**
	 * 指定された注文IDに対応する注文情報を取得します。
	 *
	 * @param orderId 取得する注文情報の注文ID
	 * @return 指定された注文IDに対応する注文情報
	 */
	@Override
	public Order getUserOrders(Integer orderId) {
		return mapper.selectUserOrders(orderId);
	}

	/**
	 * 指定されたクエリに基づく全てのユーザー情報を取得します。
	 *
	 * @param query 検索クエリ
	 * @return 指定されたクエリに基づく全てのユーザー情報のリスト
	 */
	@Override
	public List<MUser> getUsers(String query) {
		return mapper.selectAllUsers(query);
	}

	/**
	 * 指定されたユーザーIDに対応する顧客情報を取得します。
	 *
	 * @param userId 取得する顧客情報のユーザーID
	 * @return 指定されたユーザーIDに対応する顧客情報
	 */
	@Override
	public MUser getCustomerOne(Integer userId) {
		return mapper.selectUser(userId);
	}

	/**
	* 指定された顧客情報を更新します。
	*
	* @param user 更新する顧客情報
	*/
	@Override
	public void updateCustomer(MUser user) {
		mapper.updateUser(user);
	}

	/**
	* 指定されたユーザーIDに対応する顧客情報を削除します。
	*
	* @param userId 削除する顧客情報のユーザーID
	*/
	@Override
	public void deleteCustomer(Integer userId) {
		mapper.deleteUser(userId);
	}
}
