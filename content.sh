#!/bin/zsh
BASE=/Users/dongjiejie/Documents/xmall/xmall
cd ${BASE}/xmall-content/
/Users/dongjiejie/Documents/opt/normal-maven/bin/mvn clean install -DskipTests
scp ${BASE}/xmall-content/xmall-content-service/target/xmall-content-service-1.0-SNAPSHOT.jar \
root@192.168.31.234:/root/data/content
