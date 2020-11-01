create table GOODS(
id int primary key auto_increment,
shop_id int,
name varchar(100),
description varchar(1024),
details text,
img_url varchar(1024),
price decimal,
stock int not null default 0,
status varchar(16),
created_at timestamp not null default current_timestamp,
modified_at timestamp not null default current_timestamp
)