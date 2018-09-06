#!/bin/zsh
BASE=/Users/dongjiejie/Documents/xmall/xmall
cd ${BASE}/xmall-search/
/Users/dongjiejie/Documents/opt/normal-maven/bin/mvn clean install -DskipTests
scp ${BASE}/xmall-search/xmall-search-service/target/xmall-search-service-1.0-SNAPSHOT.jar \
root@192.168.31.234:/root/data/search
