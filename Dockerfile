# 使用するベースイメージを指定（OpenJDKを使用）
FROM openjdk:17-jdk-slim

# コンテナ内にアプリケーションを配置するディレクトリを作成
RUN mkdir -p /app

# コンテナ内にアプリケーションを配置するディレクトリを作成
RUN mkdir -p /app/static/img

# 静的ファイルをリポジトリからコピー
COPY ./src/main/resources/static/img /app/static/img

# コンテナ内にアプリケーションをコピー
COPY SpringEC-0.0.1-SNAPSHOT.jar /app/app.jar

# データベースの初期化用 SQL ファイルをコピー
COPY ./src/main/resources/schema.sql /initdb/schema.sql
COPY ./src/main/resources/data.sql /initdb/data.sql

# ワーキングディレクトリを設定
WORKDIR /app

# アプリケーションのポート（デフォルトは8080）を公開
EXPOSE 8080

# アプリケーションの実行コマンドを指定
ENTRYPOINT ["java", "-Duser.timezone=JST", "-jar", "app.jar"]
