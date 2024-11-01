drop table user;
drop table product;
drop table orders;
drop table order_item;
drop table cart;
-- User 테이블 생성
CREATE TABLE IF NOT EXISTS user (
                                    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR(255) NOT NULL,
                                    points INT NOT NULL,
                                    updated_at TIMESTAMP NOT NULL,
                                    version INT NOT NULL DEFAULT 0
);

-- Product 테이블 생성
CREATE TABLE IF NOT EXISTS product (
                                       product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       product_name VARCHAR(255) NOT NULL,
                                       price INT NOT NULL,
                                       product_quantity INT NOT NULL,
                                       version INT NOT NULL DEFAULT 0
);

-- Order 테이블 생성
CREATE TABLE IF NOT EXISTS orders (
                                      order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      user_id BIGINT NOT NULL,
                                      order_date TIMESTAMP NOT NULL,
                                      total_price INT NOT NULL
);

-- OrderItem 테이블 생성
CREATE TABLE IF NOT EXISTS order_item (
                                          order_item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          order_id BIGINT NOT NULL,
                                          product_id BIGINT NOT NULL,
                                          quantity INT NOT NULL,
                                          order_date TIMESTAMP NOT NULL
);

-- Cart 테이블 생성
CREATE TABLE IF NOT EXISTS cart (
                                    cart_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    user_id BIGINT NOT NULL,
                                    product_id BIGINT NOT NULL,
                                    quantity INT NOT NULL
);

-- Reset AUTO_INCREMENT value for user table
ALTER TABLE user AUTO_INCREMENT = 1;

-- Reset AUTO_INCREMENT value for other tables if needed
ALTER TABLE product AUTO_INCREMENT = 1;
ALTER TABLE orders AUTO_INCREMENT = 1;
ALTER TABLE order_item AUTO_INCREMENT = 1;
ALTER TABLE cart AUTO_INCREMENT = 1;

-- User 데이터 삽입
INSERT INTO user (name, points, updated_at) VALUES ('테스터1', 500000000, NOW());
INSERT INTO user (name, points, updated_at) VALUES ('테스터2', 500000000, NOW());
INSERT INTO user (name, points, updated_at) VALUES ('테스터3', 500000000, NOW());
INSERT INTO user (name, points, updated_at) VALUES ('테스터4', 500000000, NOW());
INSERT INTO user (name, points, updated_at) VALUES ('테스터5', 500000000, NOW());
INSERT INTO user (name, points, updated_at) VALUES ('테스터6', 500000000, NOW());
INSERT INTO user (name, points, updated_at) VALUES ('테스터7', 500000000, NOW());
INSERT INTO user (name, points, updated_at) VALUES ('테스터8', 500000000, NOW());
INSERT INTO user (name, points, updated_at) VALUES ('테스터9', 500000000, NOW());
INSERT INTO user (name, points, updated_at) VALUES ('테스터10', 500000000, NOW());

-- Product 데이터 삽입
INSERT INTO product (product_name, price, product_quantity) VALUES ('갤럭시s24', 5000, 500);
INSERT INTO product (product_name, price, product_quantity) VALUES ('아이폰17', 100000, 200);
INSERT INTO product (product_name, price, product_quantity) VALUES ('아이패드', 50000, 100);
INSERT INTO product (product_name, price, product_quantity) VALUES ('갤럭시탭', 800000, 3000);