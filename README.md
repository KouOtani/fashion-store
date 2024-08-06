# Fashion Store

## 注意事項

**Renderのフリープランをご利用の際のご注意**

Renderのフリープランでは、アプリケーションが15分間のアイドル状態が続くと、自動的にスピンダウン（休止状態）します。スピンダウン後、アプリケーションが再度アクセスされると、スピンアップ（再起動）が行われるため、初回アクセス時に若干の遅延が発生する場合があります。

**このため、初回アクセス時には以下の点にご留意ください**

- **アクセス遅延**: スピンアップのプロセスにより、アプリケーションの起動に数秒から数十秒の遅延が発生することがあります。
- **データの整合性**: アイドル状態でのスピンダウンは、アプリケーションのパフォーマンスには影響しませんが、リクエストの初回処理に時間がかかることがあります。

ご不便をおかけすることもありますが、ご理解のほどよろしくお願いいたします。

## 概要
このプロジェクトは、完全なECサイトのプロトタイプです。<br>ユーザーは商品を閲覧し、カートに追加し、購入手続きを行うことができます。

## 概要
このプロジェクトは、完全なECサイトのプロトタイプです。<br>ユーザーは商品を閲覧し、カートに追加し、購入手続きを行うことができます。

## デモ
[![Demo](https://img.shields.io/badge/Demo-→%20Click%20Here-blueviolet?style=for-the-badge&logo=appveyor)](https://fashion-store-app.onrender.com)

このリンクから、[ECサイトのデモ](https://fashion-store-app.onrender.com)をご覧いただけます。<br>
サイトの機能や操作感を確認するために、ぜひクリックしてみてください。<br>
デモ環境では、商品の閲覧、カートへの追加、購入手続きなどの基本機能を試すことができます。

### ログイン情報

デモ環境でのログイン情報は以下の通りです

#### ユーザー
- **ログインID**: `xxx@example.com`
- **パスワード**: `1111`

#### 管理者
- **ログインID**: `aaa@example.com`
- **パスワード**: `1111`

これらの情報を使って、サイトの異なる機能を確認できます。<br>
ユーザーアカウントでの一般的な操作と、管理者アカウントでの管理機能をそれぞれ試してみてください。

## 機能
- 商品閲覧
- 商品の詳細ページ
- カート機能
- ユーザー認証とアカウント管理
- 注文履歴の閲覧
- 管理者用ダッシュボード（商品管理、注文管理、顧客管理、月次売上）

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
- **管理者**: 管理者は、ログイン画面から管理者用ID・パスワードでダッシュボードにアクセスし、商品や注文を管理できます。

## デプロイ
- **本番環境へのデプロイ手順**: このプロジェクトは、Renderの無料プランを使用してデプロイされています。データベースには、Spring Boot内蔵のH2データベースを利用しています。

## 工夫した点

- **Spring Boot & MyBatisの活用**
  Spring Bootをフレームワークとして使用し、MyBatisでデータベースアクセスを効率化。これにより、迅速な開発と保守性の向上を実現しました。

- **ゲスト機能の実装**
  ユーザー登録やログインなしで商品閲覧やカート機能を利用できるゲスト機能を導入。これにより、ユーザーが手軽にサイトを利用できるようになりました。

- **セッションでのカート機能**
  ユーザーがログインしていない場合でも、セッションを利用してカートの内容を保持。これにより、ユーザーがページを移動してもカートの内容を維持できます。

- **Dockerによる環境構築**
  Dockerを使用してアプリケーションとデータベースのコンテナを管理。これにより、開発環境の再現性と移植性を向上させました。

- **管理者画面の実装**
  管理者用のダッシュボードを提供し、商品の管理や注文の確認を簡単に行えるようにしました。これにより、管理業務の効率化を図りました。

- **売上グラフの表示**
  売上データをグラフで視覚化する機能を追加。これにより、データの分析がより直感的に行えるようにしました。

- **ログイン後の画面遷移**
  ユーザーがログイン後、ログイン前にいた画面に自動的に遷移する機能を実装。これにより、ユーザーの操作感がスムーズになり、使い勝手が向上しました。

