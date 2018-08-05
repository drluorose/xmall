#!/usr/bin/env bash

BASE=/Users/dongjiejie/Documents/xmall/xmall
scp ${BASE}/xmall-search/xmall-search-service/target/xmall-search-service-1.0-SNAPSHOT.war \
root@192.168.31.234:/root/data/apache-tomcat-search/webapps/ROOT.war
