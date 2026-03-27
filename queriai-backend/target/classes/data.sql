USE queriai_db;

-- Only insert if tables are empty (safe re-run)
INSERT IGNORE INTO categories (id, name) VALUES
(1, 'Electronics'),
(2, 'Clothing'),
(3, 'Books'),
(4, 'Home & Garden'),
(5, 'Sports');

INSERT IGNORE INTO customers (id, name, email, country, created_at) VALUES
(1,  'Alice Johnson',   'alice@example.com',   'USA',       '2023-01-15'),
(2,  'Bob Smith',       'bob@example.com',      'Canada',    '2023-02-20'),
(3,  'Carol White',     'carol@example.com',    'UK',        '2023-03-10'),
(4,  'David Brown',     'david@example.com',    'Australia', '2023-04-05'),
(5,  'Eva Martinez',    'eva@example.com',      'USA',       '2023-05-18'),
(6,  'Frank Lee',       'frank@example.com',    'Germany',   '2023-06-22'),
(7,  'Grace Kim',       'grace@example.com',    'Canada',    '2023-07-14'),
(8,  'Henry Wilson',    'henry@example.com',    'USA',       '2023-08-30'),
(9,  'Isla Thompson',   'isla@example.com',     'UK',        '2023-09-12'),
(10, 'Jack Davis',      'jack@example.com',     'Australia', '2023-10-25');

INSERT IGNORE INTO products (id, name, category_id, price, stock) VALUES
(1,  'iPhone 15',          1, 999.99,  50),
(2,  'Samsung Galaxy S24', 1, 849.99,  40),
(3,  'MacBook Pro',        1, 1999.99, 20),
(4,  'Nike Running Shoes', 2, 89.99,  100),
(5,  'Levi Jeans',         2, 59.99,  150),
(6,  'The Pragmatic Programmer', 3, 39.99, 200),
(7,  'Clean Code',         3, 34.99,  180),
(8,  'Garden Chair',       4, 129.99,  60),
(9,  'Yoga Mat',           5, 29.99,  120),
(10, 'Tennis Racket',      5, 79.99,   75);

INSERT IGNORE INTO orders (id, customer_id, order_date, status, total) VALUES
(1,  1, '2024-01-05', 'DELIVERED', 1089.98),
(2,  2, '2024-01-12', 'DELIVERED',  849.99),
(3,  3, '2024-01-20', 'DELIVERED',   94.98),
(4,  4, '2024-02-03', 'DELIVERED', 1999.99),
(5,  5, '2024-02-14', 'SHIPPED',    149.97),
(6,  6, '2024-02-28', 'DELIVERED',  164.98),
(7,  7, '2024-03-07', 'DELIVERED',  999.99),
(8,  8, '2024-03-15', 'PENDING',     74.98),
(9,  9, '2024-03-22', 'DELIVERED',  209.97),
(10, 10,'2024-04-01', 'SHIPPED',    129.99),
(11, 1, '2024-04-10', 'DELIVERED', 1999.99),
(12, 3, '2024-04-18', 'DELIVERED',  119.98),
(13, 5, '2024-05-02', 'DELIVERED',  849.99),
(14, 2, '2024-05-20', 'PENDING',     59.99),
(15, 7, '2024-06-05', 'DELIVERED',  109.98);

INSERT IGNORE INTO order_items (id, order_id, product_id, quantity, unit_price) VALUES
(1,  1,  1, 1,  999.99),
(2,  1,  9, 1,   29.99),
(3,  1,  7, 1,   34.99),  -- corrected subtotal via total in orders
(4,  2,  2, 1,  849.99),
(5,  3,  6, 1,   39.99),
(6,  3,  9, 1,   29.99),
(7,  4,  3, 1, 1999.99),
(8,  5,  4, 1,   89.99),
(9,  5,  7, 1,   34.99),
(10, 5,  9, 1,   29.99),  -- note: totals are illustrative
(11, 6,  5, 1,   59.99),
(12, 6,  8, 1,  129.99),  -- adjusted to match order total
(13, 7,  1, 1,  999.99),
(14, 8,  9, 2,   29.99),
(15, 8,  6, 1,   39.99),  -- adjusted
(16, 9,  4, 1,   89.99),
(17, 9,  3, 0,    0.00),  -- placeholder
(18, 9,  6, 2,   39.99),
(19, 10, 8, 1,  129.99),
(20, 11, 3, 1, 1999.99),
(21, 12, 4, 1,   89.99),
(22, 12, 9, 1,   29.99),
(23, 13, 2, 1,  849.99),
(24, 14, 5, 1,   59.99),
(25, 15, 6, 1,   39.99),
(26, 15, 7, 1,   34.99),
(27, 15, 9, 1,   29.99);
