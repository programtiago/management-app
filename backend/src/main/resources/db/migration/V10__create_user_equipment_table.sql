CREATE TABLE TB_USER_EQUIPMENT (
    id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    user_id BIGINT NOT NULL,
    equipment_id BIGINT NOT NULL,
    assignment_date_time TIMESTAMP NOT NULL,
    return_date_time TIMESTAMP,
    comments VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES TB_USER(id),
    FOREIGN KEY (equipment_id) REFERENCES TB_EQUIPMENT(id)
)