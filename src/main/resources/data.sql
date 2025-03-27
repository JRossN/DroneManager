-- Insert Lightweight Drones
INSERT INTO drones (serial_number, model, weight_limit, battery_capacity, state, last_state_change)
VALUES 
('DRONE001', 'LIGHTWEIGHT', 500, 100, 'IDLE', CURRENT_TIMESTAMP),
('DRONE002', 'LIGHTWEIGHT', 500, 95, 'IDLE', CURRENT_TIMESTAMP),
('DRONE003', 'LIGHTWEIGHT', 500, 90, 'IDLE', CURRENT_TIMESTAMP);

-- Insert Middleweight Drones
INSERT INTO drones (serial_number, model, weight_limit, battery_capacity, state, last_state_change)
VALUES 
('DRONE004', 'MIDDLEWEIGHT', 650, 100, 'IDLE', CURRENT_TIMESTAMP),
('DRONE005', 'MIDDLEWEIGHT', 650, 95, 'IDLE', CURRENT_TIMESTAMP),
('DRONE006', 'MIDDLEWEIGHT', 650, 90, 'IDLE', CURRENT_TIMESTAMP);

-- Insert Cruiserweight Drones
INSERT INTO drones (serial_number, model, weight_limit, battery_capacity, state, last_state_change)
VALUES 
('DRONE007', 'CRUISERWEIGHT', 800, 100, 'IDLE', CURRENT_TIMESTAMP),
('DRONE008', 'CRUISERWEIGHT', 800, 95, 'IDLE', CURRENT_TIMESTAMP);

-- Insert Heavyweight Drones
INSERT INTO drones (serial_number, model, weight_limit, battery_capacity, state, last_state_change)
VALUES 
('DRONE009', 'HEAVYWEIGHT', 1000, 100, 'IDLE', CURRENT_TIMESTAMP),
('DRONE010', 'HEAVYWEIGHT', 1000, 95, 'IDLE', CURRENT_TIMESTAMP); 