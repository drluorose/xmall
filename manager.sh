#!/bin/zsh
BASE=/Users/dongjiejie/Documents/xmall/xmall
ssh root@192.168.31.234 \
"/root/data/apache-tomcat-manager/bin/shutdown.sh  && rm -fr  /root/data/apache-tomcat-manager/webapps/*"
sleep 10s
cd ${BASE}/xmall-manager/
/Users/dongjiejie/Documents/opt/normal-maven/bin/mvn clean install -DskipTests
scp ${BASE}/xmall-manager/xmall-manager-service/target/xmall-manager-service-1.0-SNAPSHOT.war \
root@192.168.31.234:/root/data/apache-tomcat-manager/webapps/ROOT.war
ssh root@192.168.31.234 "/root/data/apache-tomcat-manager/bin/startup.sh"
