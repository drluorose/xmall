#/usr/bin/env bash

BASE=/Users/dongjiejie/Documents/xmall/xmall
cd ${BASE}/xmall-parent
/Users/dongjiejie/Documents/opt/normal-maven/bin/mvn  clean install -DskipTests

cd ${BASE}/xmall-common
/Users/dongjiejie/Documents/opt/normal-maven/bin/mvn  clean install -DskipTests

cd ${BASE}/xmall-manager
/Users/dongjiejie/Documents/opt/normal-maven/bin/mvn  clean install -DskipTests

cd ${BASE}/xmall-content
/Users/dongjiejie/Documents/opt/normal-maven/bin/mvn  clean install -DskipTests

cd ${BASE}/xmall-search
/Users/dongjiejie/Documents/opt/normal-maven/bin/mvn  clean install -DskipTests

cd ${BASE}/xmall-sso
/Users/dongjiejie/Documents/opt/normal-maven/bin/mvn  clean install -DskipTests
