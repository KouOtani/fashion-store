# 使用するベースイメージを指定（OpenJDKを使用）
FROM openjdk:17-jdk-slim

# コンテナ内にアプリケーションを配置するディレクトリを作成
RUN mkdir -p /app
RUN mkdir -p /app/static/img

# 静的ファイルをリポジトリからコピー
COPY ./src/main/resources/static/img /app/static/img

# コンテナ内にアプリケーションをコピー
COPY SpringEC-0.0.1-SNAPSHOT.jar /app/app.jar

# 初期化SQLファイルと初期化スクリプトをコピー
COPY ./src/main/resources/docker/init.sql /initdb/init.sql
COPY init-db.sh /init-db.sh
RUN chmod +x /init-db.sh

# H2 データベース JAR を追加
RUN curl -L https://repo1.maven.org/maven2/com/h2database/h2/2.1.214/h2-2.1.214.jar -o /h2.jar

# ワーキングディレクトリを設定
WORKDIR /app

# アプリケーションのポート（デフォルトは8080）を公開
EXPOSE 8080

# 初期化スクリプトを実行し、アプリケーションを起動
ENTRYPOINT ["/bin/sh", "-c", "java -cp /h2.jar:/app/app.jar org.h2.tools.RunScript -url jdbc:h2:file:/data/ecdb -user sa -password password -script /initdb/init.sql && java -Djava.security.egd=file:/dev/./urandom -jar app.jar"]
