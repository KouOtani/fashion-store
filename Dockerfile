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

# ワーキングディレクトリを設定
WORKDIR /app

# アプリケーションのポート（デフォルトは8080）を公開
EXPOSE 8080

# アプリケーションの実行コマンドを指定
CMD ["java", "-jar", "app.jar"]
