package katachi.spring.exercise.repository;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import katachi.spring.exercise.domain.user.model.CartItem;
import katachi.spring.exercise.domain.user.model.DeliveryAddress;
import katachi.spring.exercise.domain.user.model.MGoods;
import katachi.spring.exercise.domain.user.model.MUser;
import katachi.spring.exercise.domain.user.model.MonthlySales;
import katachi.spring.exercise.domain.user.model.Order;
import katachi.spring.exercise.domain.user.model.OrderDetails;

@Mapper
public interface EcMapper {

	//ユーザー登録
	public int insertUser(MUser user);

	//ユーザー登録時のメールアドレス重複チェック
	public String findUserByEmail(String email);

	//アイテム一覧取得
	public List<MGoods> findGoodsWithPagination(@Param("offset") Integer offset,
			@Param("limit") Integer limit);

	//アイテムの合計数を取得
	public Integer countAllGoods();

	//アイテム取得（1数）
	public MGoods selectOneGoods(Integer goodsId);

	//アイテム取得（複数）
	public List<MGoods> selectAllGoods();

	//ログインユーザー取得
	public MUser findLoginUserByEmail(String eMail);

	//ログインユーザー取得
	public MUser findLoginUserById(Integer userId);

	//カートに追加(ログインユーザー)
	public int saveToCart(Integer userId,
			Integer goodsId,
			Integer quantity);

	//カートに同じuserIdとgoodsIdがあるか調べる
	public Integer checkCartItemExistence(Integer userId,
			Integer goodsId);

	//同じuserIdとgoodsIdに個数を加算する
	public void incrementCartItemQuantity(Integer userId,
			Integer goodsId,
			Integer quantity);

	//同じuserIdとgoodsIdに個数を変更する
	public void updateQuantity(Integer userId,
			Integer goodsId,
			Integer quantity);

	//カートを取得する
	public List<CartItem> findUserCartItems(Integer userId);

	//アイテム削除（１件）
	public int deleteItem(Integer userId, Integer goodsId);

	//アイテム削除（全件）
	public int deleteAllItem(Integer userId);

	//アイテム更新（１件）アドミン
	public void updateGoods(MGoods goods);

	//アイテム削除（１件）アドミン
	public int deleteGoods(@Param("id") Integer id);

	//アイテム登録　アドミン
	public int insertGoods(MGoods goods);

	//ユーザー情報更新(メールアドレス)
	public void updateAddress(@Param("userId") Integer userId,
			@Param("newAddress") String newAddress);

	//ユーザー情報更新(パスワード)
	public void updatePassword(@Param("userId") Integer userId,
			@Param("newPassword") String newPassword);

	//ユーザー情報更新(詳細)
	public void updateMany(@Param("userId") Integer userId,
			@Param("user") MUser user);

	//ユーザー情報更新(配送先)
	public void updateShippingAddress(@Param("userId") Integer userId,
			@Param("user") MUser user);

	//注文ユーザーを登録
	public int insertOrder(Order order);

	//オーダーの詳細を登録
	public int insertOrderDetails(OrderDetails orderDetails);

	//オーダーの配送先を登録
	public int insertDeliveryAddress(DeliveryAddress address);

	//月別の売り上げを更新
	public void updateMonthlySales(Integer year, Integer month, BigDecimal sales);

	//月別の売り上げを取得
	public List<MonthlySales> getMonthlySales(@Param("currentYear") Integer currentYear);

	//注文履歴一覧取得
	public List<Order> findManyHistories(@Param("userId") Integer userId);

	//注文履歴取得（1件）
	public Order findOneHistory(@Param("userId") Integer userId,
			@Param("orderId") Integer orderId);

	//全ユーザーの注文履歴を取得
	public List<Order> selectAllOrders(@Param("searchQuery") String query);

	//注文の詳細を取得
	public Order selectUserOrders(@Param("orderId") Integer orderId);

	//顧客一覧を取得
	public List<MUser> selectAllUsers(@Param("searchQuery") String query);

	//顧客情報を取得（1件）
	public MUser selectUser(@Param("userId") Integer userId);

	//顧客情報を更新
	public void updateUser(MUser user);

	//顧客情報を削除
	public int deleteUser(@Param("userId") Integer userId);
}
