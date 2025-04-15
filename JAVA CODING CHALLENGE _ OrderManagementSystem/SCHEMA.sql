create database orderdb;
use orderdb;
create table users (
    userid int primary key,
    username varchar(100),
    password varchar(100),
    role varchar(50)
);
CREATE TABLE products (
    productId INT PRIMARY KEY,
    productName VARCHAR(100) NOT NULL,
    description TEXT,
    price DOUBLE NOT NULL,
    quantityInStock INT NOT NULL,
    type VARCHAR(20) check (type IN ('Electronics', 'Clothing')) NOT NULL
);
CREATE TABLE electronics (
    productId INT PRIMARY KEY,
    brand VARCHAR(100) NOT NULL,
    warrantyPeriod INT NOT NULL,
    FOREIGN KEY (productId) REFERENCES products(productId) ON DELETE CASCADE
);
CREATE TABLE clothing (
    productId INT PRIMARY KEY,
    size VARCHAR(10) NOT NULL,
    color VARCHAR(50) NOT NULL,
    FOREIGN KEY (productId) REFERENCES products(productId) ON DELETE CASCADE
);
drop table users;
drop table orders;
drop table products;
drop table order_items;
CREATE TABLE orders (
    orderId INT PRIMARY KEY AUTO_INCREMENT,
    userId INT,
    orderDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES users(userId)
);
CREATE TABLE order_items (
    orderItemId INT PRIMARY KEY AUTO_INCREMENT,
    orderId INT,
    productId INT,
    quantity INT NOT NULL DEFAULT 1,
    FOREIGN KEY (orderId) REFERENCES orders(orderId),
    FOREIGN KEY (productId) REFERENCES products(productId)
);
desc order_items;
select * from users;
select * from products;


