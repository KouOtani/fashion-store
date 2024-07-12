# 使用するベースイメージを指定（OpenJDKを使用）
FROM openjdk:17-jdk-slim

# コンテナ内にアプリケーションを配置するディレクトリを作成
RUN mkdir -p /app/static/img

# コンテナ内にアプリケーションを配置するディレクトリを作成
RUN mkdir -p /app

# コンテナ内にアプリケーションをコピー
COPY SpringEC-0.0.1-SNAPSHOT.jar /app/app.jar

# MySQLイメージを使用
FROM mysql:8.0.20

# コンテナ内に初期化SQLファイルを配置するディレクトリを作成
RUN mkdir /initdb

# 初期化SQLファイルをコピー
COPY ./src/main/resources/docker/init.sql /initdb/init.sql

# ワーキングディレクトリを設定
WORKDIR /app

# アプリケーションのポート（デフォルトは8080）を公開
EXPOSE 8080

RUN docker-compose up

# アプリケーションの実行コマンドを指定
CMD ["java", "-jar", "app.jar"]
