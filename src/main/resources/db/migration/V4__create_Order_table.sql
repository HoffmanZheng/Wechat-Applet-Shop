create table tb_order(
id int primary key auto_increment,
user_id int,
total_price decimal,
address varchar(1024),
express_company varchar(16),
express_id varchar(128),
status varchar(16),
created_at timestamp not null default current_timestamp,
modified_at timestamp not null default current_timestamp
);

create table tb_order_goods
(
id int primary key auto_increment,
goods_id int,
number decimal
)