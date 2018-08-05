#!/usr/bin/env bash

BASE=/Users/dongjiejie/Documents/xmall/xmall
scp ${BASE}/xmall-manager/xmall-manager-service/target/xmall-manager-service-1.0-SNAPSHOT.war \
root@192.168.31.234:/root/data/apache-tomcat-manager/webapps/ROOT.war
