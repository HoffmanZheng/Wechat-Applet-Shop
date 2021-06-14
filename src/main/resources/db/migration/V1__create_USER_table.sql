create table tb_user(
id int primary key auto_increment,
name varchar(20),
tel varchar(20) unique,
avatar_url varchar(100),
address varchar(1024),
created_at timestamp default current_timestamp,
modified_at timestamp default current_timestamp
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;