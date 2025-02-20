CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name ENUM('ADMIN', 'DEVELOPER', 'INVITED', 'USER') NOT NULL
);
