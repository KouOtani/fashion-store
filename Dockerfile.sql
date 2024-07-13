# MySQLイメージを使用
FROM mysql:8.0.20

# コンテナ内に初期化SQLファイルを配置するディレクトリを作成
RUN mkdir /initdb

# 初期化SQLファイルをコピー
COPY ./src/main/resources/docker/init.sql /initdb/init.sql

ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=ecdb
ENV MYSQL_USER=app_user
ENV MYSQL_PASSWORD=root
ENV SERVER_PORT=3306
