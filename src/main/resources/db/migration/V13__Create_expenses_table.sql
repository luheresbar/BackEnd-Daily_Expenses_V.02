CREATE TABLE expenses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    account_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    amount DOUBLE NOT NULL,
    description VARCHAR(255),
    expense_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    currency VARCHAR(3) NOT NULL
);
