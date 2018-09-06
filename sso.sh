#!/bin/zsh
BASE=/Users/dongjiejie/Documents/xmall/xmall
cd ${BASE}/xmall-sso/
/Users/dongjiejie/Documents/opt/normal-maven/bin/mvn clean install -DskipTests
scp ${BASE}/xmall-sso/xmall-sso-service/target/xmall-sso-service-1.0-SNAPSHOT.jar \
root@192.168.31.234:/root/data/sso
