#!/bin/zsh
BASE=/Users/dongjiejie/Documents/xmall/xmall
cd ${BASE}/xmall-manager/
/Users/dongjiejie/Documents/opt/normal-maven/bin/mvn clean install -DskipTests
scp ${BASE}/xmall-manager/xmall-manager-service/target/xmall-manager-service-1.0-SNAPSHOT.jar \
root@192.168.31.234:/root/data/manager
