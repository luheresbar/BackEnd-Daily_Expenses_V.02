CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(25) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    register_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_enabled BOOLEAN NOT NULL,
    account_No_Expired BOOLEAN NOT NULL,
    account_No_Locked BOOLEAN NOT NULL,
    credential_No_Expired BOOLEAN NOT NULL
);