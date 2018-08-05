
~~~
2018-07-23T12:50:27.719103Z 5 [Note] [MY-010454] [Server] A temporary password is
 generated for root@localhost: 8iCadawLnY-f

 ALTER USER 'root'@'localhost' IDENTIFIED BY '91K8M!tqwZtJV41J';
~~~

~~~
CREATE DATABASE `xmall` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */
~~~


~~~

CREATE USER 'xmall'@'%' IDENTIFIED BY 'EusbI7pr91%h$3BCX';
GRANT ALL ON xmall.* TO 'xmall'@'%';

ALTER USER 'xmall'@'%' IDENTIFIED WITH mysql_native_password BY 'EusbI7pr91%h$3BCX';
---
GRANT SELECT ON xmall.invoice TO 'xmall'@'%';
ALTER USER 'jeffrey'@'localhost' WITH MAX_QUERIES_PER_HOUR 90;
~~~
