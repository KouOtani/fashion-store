# Fashion Store

## 概要
このプロジェクトは、完全なECサイトのプロトタイプです。<br>ユーザーは商品を閲覧し、カートに追加し、購入手続きを行うことができます。

## デモ
[![Demo](https://img.shields.io/badge/Demo-→%20Click%20Here-blueviolet?style=for-the-badge&logo=appveyor)](https://fashion-store-app.onrender.com)

## スクリーンショット

<div style="text-align: center;">
  <p><strong>ホームページ</strong></p>
  <img src="docs/screenshots/homepage.png" alt="ホームページ" style="max-width: 100%; height: auto;">
</div>
<br>
<div style="text-align: center;">
  <p><strong>商品詳細</strong></p>
  <img src="docs/screenshots/goods-details.png" alt="商品詳細" style="max-width: 100%; height: auto;">
</div>
<br>
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
- ⚙管理者用ダッシュボード（商品の管理、注文管理）

## 技術スタック
### フロントエンド
![HTML5](https://img.shields.io/badge/HTML5-E34F26?logo=html5&logoColor=white&style=for-the-badge)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?logo=css3&logoColor=white&style=for-the-badge)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?logo=javascript&logoColor=black&style=for-the-badge)
![Bootstrap](https://img.shields.io/badge/Bootstrap-563D7C?logo=bootstrap&logoColor=white&style=for-the-badge)

### バックエンド
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=spring-boot&logoColor=white&style=for-the-badge)
![MyBatis](https://img.shields.io/badge/MyBatis-CB3837?logo=mybatis&logoColor=white&style=for-the-badge)

### データベース
![MySQL](https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=white&style=for-the-badge)

### インフラ
![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white&style=for-the-badge)
![Docker Compose](https://img.shields.io/badge/Docker%20Compose-2496ED?logo=docker&logoColor=white&style=for-the-badge)

### その他
![H2 Database](https://img.shields.io/badge/H2%20Database-003B57?logo=h2&logoColor=white&style=for-the-badge)
## 使用方法
- **ユーザー**: ユーザーは、ホームページから商品を閲覧し、カートに追加、注文を完了できます。
- **管理者**: 管理者は、`/admin` URLからダッシュボードにアクセスし、商品や注文を管理できます。

## デプロイ
- **本番環境へのデプロイ手順**: Renderを使用してデプロイしました。無料枠でデプロイするため、Spring Bootに内包されているH2データベースを使用しました。
