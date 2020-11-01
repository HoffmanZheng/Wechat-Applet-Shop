CREATE TABLE SHOPPING_CART
(
    id         int PRIMARY KEY AUTO_INCREMENT,
    user_id    int,
    goods_id   int,
    number     int,
    status     VARCHAR(16),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    modified_at TIMESTAMP NOT NULL DEFAULT NOW()
)