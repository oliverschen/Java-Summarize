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