# 修改管理后台位vue结构

## 添加目录支持
表
~~~
drop table if exists tb_menu;
CREATE TABLE `tb_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) default null comment  '父菜单的id,当为父菜单时，pid 为 null',
  `title` varchar(255) DEFAULT NULL,
  `component` varchar(255) DEFAULT NULL,
  `perm_type` varchar(128) default null,
  `valid` int(1) default 1 comment '表示数据是否有效0 无效 1 有效',
  `create_time` DATETIME default null comment '数据创建时间',
  `update_time` DATETIME default null comment  '数据更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

~~~

~~~
insert into tb_menu(pid,title,component,path,perm_type,valid,create_time,update_time) 
values(null,"首页","Main","1",'/sys',1,now(),now());

insert into tb_menu(pid,title,name,component,path,icon,perm_type,valid,create_time,update_time) 
value(1,"地图2",'map',"sys/user-manage/userManage",'user-manage','user',"1",1,now(),now());
~~~


role-menu 映射表
~~~
drop table if exists tb_role_menu;
CREATE TABLE `tb_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `menu_id` int(11) DEFAULT NULL,
  `valid` int(1) default 1 comment '表示数据是否有效0 无效 1 有效',
  `create_time` DATETIME default null comment '数据创建时间',
  `update_time` DATETIME default null comment  '数据更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
~~~

~~~
insert into tb_role_menu(role_id,menu_id,valid,create_time,update_time)
values(1,1,1,now(),now());
insert into tb_role_menu(role_id,menu_id,valid,create_time,update_time)
values(1,2,1,now(),now());
~~~


CREATE DATABASE `xmall` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */