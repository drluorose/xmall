#!/bin/zsh
BASE=/Users/dongjiejie/Documents/xmall/xmall
cd ${BASE}/xmall-search/
/Users/dongjiejie/Documents/opt/normal-maven/bin/mvn clean install -DskipTests
scp ${BASE}/xmall-search/xmall-search-service/target/xmall-search-service-1.0-SNAPSHOT.jar \
root@192.168.31.234:/root/data/apache-tomcat-search/xmall-search-service.jar
ssh root@192.168.31.234 "cd /root/data/apache-tomcat-search/ && nohup  java -jar xmall-search-service.jar   --server.port=8282 > log.log 2>&1 &"
