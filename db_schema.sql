CREATE TABLE author (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE publisher (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE book (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    isbn CHAR(13) UNIQUE,
    publisher_id INT,
    publication_date DATE,
    language VARCHAR(20),
    pages INT,
    description TEXT,
    price DECIMAL(8, 2),
    image_url VARCHAR(255),
    FOREIGN KEY (publisher_id) REFERENCES publisher(id)
);

CREATE INDEX idx_book_publisher_id ON book (publisher_id);
CREATE INDEX idx_book_title ON book (title);
CREATE INDEX idx_book_isbn ON book (isbn);

CREATE TABLE book_author (
    book_id INT,
    author_id INT,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES book(id),
    FOREIGN KEY (author_id) REFERENCES author(id)
);

CREATE INDEX idx_book_author_book_id ON book_author (book_id);
CREATE INDEX idx_book_author_author_id ON book_author (author_id);

CREATE TABLE category (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    parent_id INT,
    FOREIGN KEY (parent_id) REFERENCES category(id)
);

CREATE INDEX idx_category_parent_id ON category (parent_id);

CREATE TABLE book_category (
    book_id INT,
    category_id INT,
    PRIMARY KEY (book_id, category_id),
    FOREIGN KEY (book_id) REFERENCES book(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE INDEX idx_book_category_book_id ON book_category (book_id);
CREATE INDEX idx_book_category_category_id ON book_category (category_id);

CREATE TABLE inventory (
    book_id INT PRIMARY KEY,
    quantity INT,
    FOREIGN KEY (book_id) REFERENCES book(id)
);

CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('USER', 'SELLER', 'ADMIN') DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_profile (
    user_id INT PRIMARY KEY,
    full_name VARCHAR(100),
    address VARCHAR(200),
    phone VARCHAR(20),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE INDEX idx_user_profile_user_id ON user_profile (user_id);

CREATE TABLE `order` (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    status ENUM('pending', 'processing', 'shipped', 'delivered', 'cancelled') DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    shipping_address VARCHAR(200),
    billing_address VARCHAR(200),
    total_amount DECIMAL(10, 2),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE INDEX idx_order_user_id ON `order` (user_id);
CREATE INDEX idx_order_created_at ON `order` (created_at);
CREATE INDEX idx_order_status ON `order` (status);

CREATE TABLE order_item (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    book_id INT,
    quantity INT,
    price DECIMAL(8, 2),
    FOREIGN KEY (order_id) REFERENCES `order`(id),
    FOREIGN KEY (book_id) REFERENCES book(id)
);

CREATE INDEX idx_order_item_order_id ON order_item (order_id);
CREATE INDEX idx_order_item_book_id ON order_item (book_id);

CREATE TABLE wishlist (
    user_id INT,
    book_id INT,
    PRIMARY KEY (user_id, book_id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (book_id) REFERENCES book(id)
);

CREATE INDEX idx_wishlist_user_id ON wishlist (user_id);
CREATE INDEX idx_wishlist_book_id ON wishlist (book_id);

CREATE TABLE promotion (
    id INT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(20) UNIQUE,
    discount_percentage DECIMAL(5, 2),
    start_date DATE,
    end_date DATE
);

CREATE INDEX idx_promotion_code ON promotion (code);

CREATE TABLE book_promotion (
    book_id INT,
    promotion_id INT,
    PRIMARY KEY (book_id, promotion_id),
    FOREIGN KEY (book_id) REFERENCES book(id),
    FOREIGN KEY (promotion_id) REFERENCES promotion(id)
);

CREATE INDEX idx_book_promotion_book_id ON book_promotion (book_id);
CREATE INDEX idx_book_promotion_promotion_id ON book_promotion (promotion_id);

CREATE TABLE cart (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE cart_item (
    id INT PRIMARY KEY AUTO_INCREMENT,
    cart_id INT,
    book_id INT,
    quantity INT,
    FOREIGN KEY (cart_id) REFERENCES cart(id),
    FOREIGN KEY (book_id) REFERENCES book(id),
    UNIQUE (cart_id, book_id)
);


create database better_read;
use better_read;

-- Insert sample data into the author table
INSERT INTO author (name) VALUES
('John Smith'),
('Emily Johnson'),
('Michael Davis'),
('Sarah Thompson'),
('David Wilson');

-- Insert sample data into the publisher table
INSERT INTO publisher (name) VALUES
('ABC Publishers'),
('XYZ Books'),
('123 Publishing House'),
('QWE Press'),
('RTY Publications');

-- Insert sample data into the book table
INSERT INTO book (title, isbn, publisher_id, publication_date, language, pages, description, price, image_url) VALUES
('Book 1', '1234567892123', 1, '2022-01-01', 'English', 250, 'Description of Book 1', 19.99, 'https://example.com/book1.jpg'),
('Book 2', '2345678901234', 2, '2021-05-15', 'Spanish', 320, 'Description of Book 2', 24.99, 'https://example.com/book2.jpg'),
('Book 3', '3456789012345', 3, '2023-03-10', 'French', 180, 'Description of Book 3', 14.99, 'https://example.com/book3.jpg'),
('Book 4', '4567890123456', 4, '2022-11-20', 'German', 400, 'Description of Book 4', 29.99, 'https://example.com/book4.jpg'),
('Book 5', '5678901234567', 5, '2023-02-28', 'Italian', 220, 'Description of Book 5', 17.99, 'https://example.com/book5.jpg');

-- Insert sample data into the book_author table
INSERT INTO book_author (book_id, author_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 4),
(4, 5),
(5, 1);

-- Insert sample data into the category table
INSERT INTO category (name, parent_id) VALUES
('Fiction', NULL),
('Non-Fiction', NULL),
('Mystery', 1),
('Science Fiction', 1),
('Biography', 2),
('History', 2);

-- Insert sample data into the book_category table
INSERT INTO book_category (book_id, category_id) VALUES
(1, 1),
(1, 3),
(2, 1),
(2, 4),
(3, 2),
(3, 5),
(4, 2),
(4, 6),
(5, 1),
(5, 3);

-- Insert sample data into the inventory table
INSERT INTO inventory (book_id, quantity) VALUES
(1, 100),
(2, 50),
(3, 75),
(4, 30),
(5, 60);

-- Insert sample data into the user table
INSERT INTO user (username, email, password, role) VALUES
('abc123', 'user1@example.com', 'password1', 'USER'),
('user2', 'user2@example.com', 'password2', 'USER'),
('seller1', 'seller1@example.com', 'password3', 'SELLER'),
('admin1', 'admin1@example.com', 'password4', 'ADMIN');

-- Insert sample data into the user_profile table
INSERT INTO user_profile (user_id, full_name, address, phone) VALUES
(1, 'User One', '123 Street, City', '1234567890'),
(2, 'User Two', '456 Avenue, Town', '9876543210'),
(3, 'Seller One', '789 Road, Village', '5555555555'),
(4, 'Admin One', '321 Lane, City', '1111111111');

-- Insert sample data into the order table
INSERT INTO `order` (user_id, status, shipping_address, billing_address, total_amount) VALUES
(1, 'pending', '123 Street, City', '123 Street, City', 49.98),
(2, 'processing', '456 Avenue, Town', '456 Avenue, Town', 39.98),
(3, 'shipped', '789 Road, Village', '789 Road, Village', 29.99),
(4, 'delivered', '321 Lane, City', '321 Lane, City', 59.98);

-- Insert sample data into the order_item table
INSERT INTO order_item (order_id, book_id, quantity, price) VALUES
(1, 1, 2, 19.99),
(1, 2, 1, 24.99),
(2, 3, 1, 14.99),
(3, 4, 1, 29.99),
(4, 5, 3, 17.99);

-- Insert sample data into the wishlist table
INSERT INTO wishlist (user_id, book_id) VALUES
(1, 2),
(1, 4),
(2, 1),
(2, 3),
(3, 5);

-- Insert sample data into the promotion table
INSERT INTO promotion (code, discount_percentage, start_date, end_date) VALUES
('SUMMER10', 10.00, '2023-06-01', '2023-08-31'),
('NEWYEAR20', 20.00, '2024-01-01', '2024-01-31');

-- Insert sample data into the book_promotion table
INSERT INTO book_promotion (book_id, promotion_id) VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 2),
(5, 1),
(5, 2);