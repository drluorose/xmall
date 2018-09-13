create table tb_transaction(
  id bigint(20) NOT NULL AUTO_INCREMENT,
  order_id varchar(32) not null default '' comment '订单号',
  tid varchar(32) not null default '' COMMENT '支付账号id',
  tid_num varchar(64) not null default '' comment '支付账号id十精制表示',
  total Decimal(10,2) not null default 0 COMMENT '总共金额',
  currency varchar(20) not null default '' COMMENT '支付的货币种类',
  payment tinyint not null default 0 comment '1 paypal 2 alipay 3 wechat_pay',
  payment_des varchar(32) not null default 0 comment '支付方式的说明',
  PRIMARY key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE tb_transaction_paypal (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  tid varchar(32) not null default '' COMMENT '支付账号id',
  paypal_id varchar(128) not null default '' comment 'paypay支付id号',
  total Decimal(10,2) not null default 0 COMMENT '总共金额',
  currency varchar(20) not null default '' COMMENT '支付的货币种类',
  subtotal decimal(10,2) not null default 0 comment '货物总价',
  tax decimal(10,2) not null default 0 comment '税',
  shipping decimal(10,2) not null default 0 comment '运费',
  handling_fee decimal(10,2) not null default 0  comment '手提费 手续费',
  shipping_discount decimal(10,2) not null default 0 comment '航运折扣',
  insurance decimal(10,2) not null default 0 comment '保险',
  return_url varchar(512) not null default '' comment  'return url',
  cancel_url varchar(512) not null default '' COMMENT  'cancel url',
  PRIMARY KEY (id),
  unique key (tid),
  unique key (paypal_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

create table tb_transaction_paypal_item(
  id bigint(20) NOT null AUTO_INCREMENT,
  tid varchar(32) not null default '' comment '交易号',
  name varchar(128) not null default '' comment '名称',
  description varchar(512) not null default  '' comment '描述',
  quantity int(10) not null default 1 comment '数量',
  price decimal(10,2) not null default 0 comment '价格',
  tax decimal(10,2) not null default 0 comment '税',
  sku varchar(64) not null default '' comment 'sku',
  currency varchar(128) not null default '' comment 'currency',
  primary key(id)
) ENGINE=InnoDB Default CHARSET=utf8 COLLATE=utf8_bin;

create table tb_transaction_paypal_shipping_address(
  id bigint(20) NOT null Auto_increment,
  tid varchar(32) not null default '' comment '交易号',
  recipient_name varchar(512) not null default '' comment  '',
  line1 varchar(512) not null default '' comment  '街道1',
  line2 varchar(512) not null default '' comment  '街道2',
  city varchar(128) not null default '' comment  '城市' ,
  country_code varchar(64) not null default '' comment  '国家代码',
  postal_code varchar(64) not null default  '' comment '邮政编码',
  phone varchar(64)  not null default  '' comment  '电话号码+86 这种',
  state varchar(64) not null default '' comment  '洲简称',
  primary key(id),
  unique key(tid)
)ENGINE=InnoDB Default CHARSET=utf8 COLLATE=utf8_bin;