#!/bin/zsh
BASE=/Users/dongjiejie/Documents/xmall/xmall
ssh root@192.168.31.234 \
"/root/data/apache-tomcat-content/bin/shutdown.sh  && rm -fr  /root/data/apache-tomcat-content/webapps/*"
sleep 10s
cd ${BASE}/xmall-content/
/Users/dongjiejie/Documents/opt/normal-maven/bin/mvn clean install -DskipTests
scp ${BASE}/xmall-content/xmall-content-service/target/xmall-content-service-1.0-SNAPSHOT.war \
root@192.168.31.234:/root/data/apache-tomcat-content/webapps/ROOT.war
ssh root@192.168.31.234 "/root/data/apache-tomcat-content/bin/startup.sh"
