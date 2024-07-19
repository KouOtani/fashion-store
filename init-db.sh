#!/bin/sh

# H2 データベースを起動し、スクリプトを実行
java -cp /h2.jar org.h2.tools.RunScript -url jdbc:h2:file:/data/ecdb -user sa -password password -script /initdb/init.sql
