create table USER(
id int primary key auto_increment,
name varchar(20),
tel varchar(20) unique,
avatar_url varchar(100),
created_at timestamp default current_timestamp,
modified_at timestamp default current_timestamp
)