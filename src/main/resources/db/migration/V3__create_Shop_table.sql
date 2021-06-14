create table tb_shop(
id int primary key auto_increment,
`name` varchar(100),
description varchar(1024),
img_url varchar(1024),
owner_user_id int,
status varchar(16),
created_at timestamp not null default current_timestamp,
modified_at timestamp not null default current_timestamp
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;