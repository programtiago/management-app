CREATE TABLE IF NOT EXISTS TB_DEPARTMENT (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code_value VARCHAR(5) NOT NULL,
    description VARCHAR(50) NOT NULL,
    is_active BIT NOT NULL,
    registry_date VARCHAR(19) NOT NULL,
    total_employees INTEGER
);