create database 'geek';
use geek;

create table `gk_user`(
    `id` int(10) not null primary key auto_increment,
    `name` varchar(20) not null,
    `birth` date not null,
    `address` varchar(100)
)engine=InnoDB default charset=utf8;

