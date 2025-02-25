CREATE TABLE IF NOT EXISTS TB_EQUIPMENT (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(50) NOT NULL,
    serial_number VARCHAR(25) NOT NULL,
    brand VARCHAR(20)  NOT NULL,
    model VARCHAR(25) NOT NULL,
    type VARCHAR(15) NOT NULL,
    location VARCHAR(20),
    workstation VARCHAR(10),
    unity VARCHAR(10),
    registry_date VARCHAR(19),
    is_active BIT,
    status_equipment VARCHAR(20),
    status_physic VARCHAR(20),
    CONSTRAINT chk_status_equipment CHECK (status_equipment IN ('Available', 'Not Available', 'For Warranty', 'In Use'))
);

