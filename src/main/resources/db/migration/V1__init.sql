CREATE SCHEMA IF NOT EXISTS wego_assignment;

CREATE TABLE IF NOT EXISTS wego_assignment.parking_lot
(
    car_park_no   VARCHAR(100) PRIMARY KEY,
    address       VARCHAR(100) NOT NULL,
    x_coord       NUMERIC NOT NULL,
    y_coord       NUMERIC NOT NULL,
    car_park_type VARCHAR(100) NOT NULL,
    type_of_parking_system VARCHAR(100) NOT NULL,
    short_term_parking VARCHAR(100) NOT NULL,
    free_parking VARCHAR(100) NOT NULL,
    night_parking VARCHAR(100) NOT NULL,
    car_park_decks VARCHAR(100) NOT NULL,
    gantry_height NUMERIC NOT NULL,
    car_park_basement VARCHAR(100) NOT NULL,
    total_lots INTEGER,
    lot_type VARCHAR(10),
    lots_available INTEGER,
    update_datetime TIMESTAMP
);