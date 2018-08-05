#!/usr/bin/env bash

BASE=/Users/dongjiejie/Documents/xmall/xmall
scp ${BASE}/xmall-sso/xmall-sso-service/target/xmall-sso-service-1.0-SNAPSHOT.war \
root@192.168.31.234:/root/data/apache-tomcat-sso/webapps/ROOT.war
