ALTER TABLE wxshop.tb_shopping_cart ADD shop_id INT NULL;

insert into tb_user (id, name, tel, avatar_url, address)
values (1, '初始用户1', 13800000000, 'https://url1', 'address1');

insert into tb_user (id, name, tel, avatar_url, address)
values (2, '初始用户2', 13800000001, 'https://url2', 'address2');

insert into tb_user (id, name, tel, avatar_url, address)
values (3, '初始用户3', 13800000002, 'https://url3', 'address3');

insert into tb_shop (id, name, description, img_url, owner_user_id, status)
values (1, '苹果旗舰店', '官方授权', 'https://img_url1', 1, '已创建');

insert into tb_shop (id, name, description, img_url, owner_user_id, status)
values (2, '华为旗舰店', '官方授权', 'https://img_url2', 2, '已创建');

insert into tb_shop (id, name, description, img_url, owner_user_id, status)
values (3, '小米旗舰店', '官方授权', 'https://img_url3', 1, '已创建');

insert into tb_goods (id, shop_id, name, description, details, img_url, price, stock, status)
values (1, 1, 'IPhone12', '新一代摄像头', 'IP12描述...', 'https://img_url1', 5000, 5, '已创建');

insert into tb_goods (id, shop_id, name, description, details, img_url, price, stock, status)
values (2, 1, 'IPhone13', '新一代摄像头', 'IP13描述...', 'https://img_url2', 5000, 5, '已创建');

insert into tb_goods (id, shop_id, name, description, details, img_url, price, stock, status)
values (3, 1, 'IPhone14', '新一代摄像头', 'IP14描述...', 'https://img_url3', 5000, 5, '已创建');

insert into tb_goods (id, shop_id, name, description, details, img_url, price, stock, status)
values (4, 3, '小米11', '新一代摄像头', '小米11描述...', 'https://img_url4', 5000, 5, '已创建');

insert into tb_goods (id, shop_id, name, description, details, img_url, price, stock, status)
values (5, 3, '小米12', '新一代摄像头', '小米12描述...', 'https://img_url5', 5000, 5, '已创建');

insert into tb_goods (id, shop_id, name, description, details, img_url, price, stock, status)
values (6, 2, 'P30', '新一代摄像头', 'P30描述...', 'https://img_url6', 5000, 5, '已创建');

insert into tb_goods (id, shop_id, name, description, details, img_url, price, stock, status)
values (7, 2, 'P40', '新一代摄像头', 'P40描述...', 'https://img_url7', 5000, 5, '已创建');

insert into tb_goods (id, shop_id, name, description, details, img_url, price, stock, status)
values (8, 2, 'P50', '新一代摄像头', 'P50描述...', 'https://img_url8', 5000, 5, '已创建');

insert into tb_shopping_cart (id, user_id, goods_id, shop_id, number, status)
values (1, 1, 1, 1, 2, '已创建');

insert into tb_shopping_cart (id, user_id, goods_id, shop_id, number, status)
values (2, 1, 2, 1, 1, '已创建');

insert into tb_shopping_cart (id, user_id, goods_id, shop_id, number, status)
values (3, 1, 3, 1, 2, '已创建');

insert into tb_shopping_cart (id, user_id, goods_id, shop_id, number, status)
values (4, 1, 7, 2, 2, '已创建');

insert into tb_shopping_cart (id, user_id, goods_id, shop_id, number, status)
values (5, 1, 8, 2, 1, '已创建');

insert into tb_shopping_cart (id, user_id, goods_id, shop_id, number, status)
values (6, 1, 4, 3, 2, '已创建');

insert into tb_shopping_cart (id, user_id, goods_id, shop_id, number, status)
values (7, 1, 5, 3, 1, '已创建');