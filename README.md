# fashion store

## 概要
このプロジェクトは、完全なECサイトのプロトタイプです。ユーザーは商品を閲覧し、カートに追加し、購入手続きを行うことができます。

## デモ
- **デモリンク**: [fashion store](https://fashion-store-app.onrender.com)

- **スクリーンショット**:
  
  <div style="text-align: center;">
    <p><strong>ホームページ</strong></p>
    <img src="docs/screenshots/homepage.png" alt="ホームページ" style="max-width: 100%; height: auto;">
  </div>
  
  <div style="text-align: center;">
    <p><strong>商品詳細</strong></p>
    <img src="docs/screenshots/goods-details.png" alt="商品詳細" style="max-width: 100%; height: auto;">
  </div>

  <div style="text-align: center;">
    <p><strong>カート</strong></p>
    <img src="docs/screenshots/cart.png" alt="カート" style="max-width: 100%; height: auto;">
  </div>
  
## 機能
- 商品閲覧と検索
- 商品の詳細ページ
- カート機能
- ユーザー認証とアカウント管理
- 注文履歴の閲覧
- 管理者用ダッシュボード（商品の管理、注文管理）

## 技術スタック
- **フロントエンド**: HTML, CSS, JavaScript, Bootstrap5
- **バックエンド**: Java, Spring Boot, MyBatis
- **データベース**: MySQL
- **インフラ**: Docker, Docker Compose
- **その他**: H2 データベース（デプロイ用）

## 使用方法
- **ユーザー**: ユーザーは、ホームページから商品を閲覧し、カートに追加、注文を完了できます。
- **管理者**: 管理者は、/admin URLからダッシュボードにアクセスし、商品や注文を管理できます。

## デプロイ
- **本番環境へのデプロイ手順**: Render使用してデプロイしました。（無料枠でデプロイするためspring bootに内包されているH2データベースを使用しました。）
