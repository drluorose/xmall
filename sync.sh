#!/usr/bin/env bash
BASE=/Users/dongjiejie/Documents/xmall/xmall
cp -f ${BASE}/conf/all.properties  ${BASE}/xmall-content/xmall-content-service/src/main/resources/conf/
cp -f ${BASE}/conf/all.properties  ${BASE}/xmall-manager/xmall-manager-service/src/main/resources/conf/
cp -f ${BASE}/conf/all.properties  ${BASE}/xmall-search/xmall-search-service/src/main/resources/conf/
cp -f ${BASE}/conf/all.properties  ${BASE}/xmall-sso/xmall-sso-service/src/main/resources/conf/
cp -f ${BASE}/conf/all.properties  ${BASE}/xmall-manager-web/src/main/resources/conf/

