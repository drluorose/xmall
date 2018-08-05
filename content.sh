#!/usr/bin/env bash

BASE=/Users/dongjiejie/Documents/xmall/xmall
scp ${BASE}/xmall-content/xmall-content-service/target/xmall-content-service-1.0-SNAPSHOT.war \
root@192.168.31.234:/root/data/apache-tomcat-content/webapps/ROOT.war
