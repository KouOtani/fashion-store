# 使用するベースイメージを指定（OpenJDKを使用）
FROM openjdk:17-jdk-slim

# コンテナ内にアプリケーションを配置するディレクトリを作成
RUN mkdir -p /app/static/img
RUN mkdir -p /app

# アプリケーションをコピー
COPY SpringEC-0.0.1-SNAPSHOT.jar /app/app.jar

# ワーキングディレクトリを設定
WORKDIR /app

# アプリケーションのポートを公開
EXPOSE 8080

# アプリケーションの実行コマンドを指定
CMD ["java", "-jar", "app.jar"]

# MySQLイメージを指定
FROM mysql:8.0.20

# コンテナ内に初期化SQLファイルを配置するディレクトリを作成
RUN mkdir /initdb

# 初期化SQLファイルをコピー
COPY ./src/main/resources/docker/init.sql /initdb/init.sql

# データベースの設定（環境変数で指定）
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=ecdb
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root

# コマンドで初期化SQLを実行
CMD ["--init-file", "/initdb/init.sql"]
