CREATE TABLE tb_payment (
  id varchar(32) not null default '' COMMENT '支付账号id',
  order_id varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '订单id',
  currency varchar(20) not null default '' COMMENT '支付的货币种类',
  "subtotal": "30.00",
          "tax": "0.07",
          "shipping": "0.03",
          "handling_fee": "1.00",
          "shipping_discount": "-1.00",
          "insurance": "0.01"
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;