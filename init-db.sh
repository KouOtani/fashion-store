#!/bin/sh

# H2 データベースに接続して init.sql を実行
java -cp /app/app.jar org.h2.tools.RunScript -url jdbc:h2:file:/initdb -user sa -password password -script /initdb/init.sql
