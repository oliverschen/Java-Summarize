create table `account`(
    `id` int primary key auto_increment,
    `account_id` int not null comment '账户ID',
    `user_id` int not null comment '用户id',
    `user_name` varchar(10) not null comment '用户姓名',
    `cny` decimal(20,2) not null default '0' comment '人民币账户',
    `freeze_cny` decimal(20,2) not null default '0' comment '人民币冻结账户',
    `usd` decimal(20,2) not null default '0' comment '美元账户',
    `freeze_usd` decimal(20,2) not null default '0' comment '美元冻结账户',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp comment '更新时间'
)engine=InnoDB default charset=utf8mb4 comment='账户表';