#!/bin/zsh
BASE=/Users/dongjiejie/Documents/xmall/xmall
ssh root@192.168.31.234 \
"/root/data/apache-tomcat-search/bin/shutdown.sh  && rm -fr  /root/data/apache-tomcat-search/webapps/*"
sleep 10s
cd ${BASE}/xmall-search/
/Users/dongjiejie/Documents/opt/normal-maven/bin/mvn clean install -DskipTests
scp ${BASE}/xmall-search/xmall-search-service/target/xmall-search-service-1.0-SNAPSHOT.war \
root@192.168.31.234:/root/data/apache-tomcat-search/webapps/ROOT.war
ssh root@192.168.31.234 "/root/data/apache-tomcat-search/bin/startup.sh"
