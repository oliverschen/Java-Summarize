学习笔记

### 第十一课

#### Guava/Stream/Lambda 使用

Stream 和 Lambda 在工作中经常使用，目前业务代码只要不是老旧代码都是用的是 java8 方式来处理的，Guava 没有使用过，练习的[代码地址](https://github.com/oliverschen/Java-Summarize/tree/main/weeks/Week_06/homework)

#### 在工作中如何用设计模式解决问题？

在工作中使用最多的模式是模版模式和策略模式

1. 模版模式可以将一些冗余的代码写在抽象类中，将复杂的业务解偶出来，简化业务代码，方便维护。
2. 策略模式典型的应用是在支付模块中，我们引入了微信App，微信公众号，微信H5，支付宝App，支付宝H5，iOS内购等支付方式，使用策略模式接触大量 if else 判断逻辑
3. 经常模版和策略组合使用

### 第十二课

#### 电商交易表结构 DDL SQL

```sql
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
	id BIGINT PRIMARY KEY auto_increment,
	create_time TIMESTAMP NULL COMMENT '创建时间',
	update_time TIMESTAMP NULL COMMENT '更新时间',
	nickname VARCHAR(20) CHARSET utf8mb4 COMMENT '用户昵称',
	age INT(3) COMMENT '用户年龄',
	phone VARCHAR(11) COMMENT '电话号码',
	qq VARCHAR(15) COMMENT 'QQ号码',
	wechat VARCHAR(15) COMMENT '微信号码',
	email VARCHAR(20) COMMENT '电子邮箱',
	password VARCHAR(128) COMMENT '登录密码'
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '用户表';


DROP TABLE IF EXISTS `goods`;

CREATE TABLE `goods`(
	id BIGINT PRIMARY KEY auto_increment,
	create_time TIMESTAMP NULL COMMENT '创建时间',
	update_time TIMESTAMP NULL COMMENT '更新时间',
	deleted INT(2) DEFAULT 0 COMMENT '是否已删除 0 false 1 true',
	sale_status INT(2) DEFAULT 0 COMMENT '是否已上架 0 false 1 true',
	price DECIMAL COMMENT '商品价格',
	url VARCHAR(128) COMMENT '商品图片',
	good_desc VARCHAR(512) COMMENT '详情描述'
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '商品表';


DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
	id BIGINT PRIMARY KEY auto_increment,
	create_time TIMESTAMP NULL COMMENT '创建时间',
	update_time TIMESTAMP NULL COMMENT '更新时间',
	order_time TIMESTAMP NULL COMMENT '下单时间',
	send_time TIMESTAMP NULL COMMENT '发货时间',
	goods_id BIGINT COMMENT '商品id',
	user_id BIGINT NOT NULL COMMENT '购买者用户id',
	phone VARCHAR(11) COMMENT '电话号码',
	country_id INT(5) COMMENT '国家代码',
	city_id INT(8) COMMENT '城市代码',
	address VARCHAR(128) CHARSET utf8mb4 COMMENT '详细地址',
	amount DECIMAL COMMENT '订单金额',
	discount DECIMAL COMMENT '优惠价格',
	coupon_id BIGINT COMMENT '优惠券id',
	express_aount DECIMAL COMMENT '物流价格',
	express_id DECIMAL COMMENT '物流id',
	order_status INT(2) COMMENT '订单状态',
	express_status INT(2) COMMENT '物流状态',
	remark VARCHAR(256) CHARSET utf8mb4 COMMENT '备注',
	deleted INT(2) DEFAULT 0 COMMENT '是否已删除 0 false 1 true'
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '订单表';
```