#!/bin/zsh
BASE=/Users/dongjiejie/Documents/xmall/xmall
ssh root@192.168.31.234 \
"/root/data/apache-tomcat-sso/bin/shutdown.sh  && rm -fr  /root/data/apache-tomcat-sso/webapps/*"
cd ${BASE}/xmall-sso/
/Users/dongjiejie/Documents/opt/normal-maven/bin/mvn clean install -DskipTests
scp ${BASE}/xmall-sso/xmall-sso-service/target/xmall-sso-service-1.0-SNAPSHOT.war \
root@192.168.31.234:/root/data/apache-tomcat-sso/webapps/ROOT.war
ssh root@192.168.31.234 "/root/data/apache-tomcat-sso/bin/startup.sh"
