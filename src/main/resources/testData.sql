-- User 테이블 생성
CREATE TABLE IF NOT EXISTS user (
                                    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR(255) NOT NULL,
                                    points INT NOT NULL,
                                    updated_at TIMESTAMP NOT NULL
);

-- Product 테이블 생성
CREATE TABLE IF NOT EXISTS product (
                                       product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       product_name VARCHAR(255) NOT NULL,
                                       price INT NOT NULL,
                                       product_quantity INT NOT NULL
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