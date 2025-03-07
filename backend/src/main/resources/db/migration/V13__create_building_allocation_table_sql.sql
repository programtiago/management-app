CREATE TABLE IF NOT EXISTS TB_BUILDING_ALLOCATION (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    building_id BIGINT,
    location_id BIGINT,
    department_id BIGINT,
    building_allocation_date_time VARCHAR(19) NOT NULL,
    FOREIGN KEY (building_id) REFERENCES TB_BUILDING(id),
    FOREIGN KEY (location_id) REFERENCES TB_LOCATION(id),
    FOREIGN KEY (department_id) REFERENCES TB_DEPARTMENT(id)
)