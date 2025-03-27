-- Insert Lightweight Drones
INSERT INTO drones (serial_number, model, weight_limit, battery_capacity, state, last_state_change)
VALUES 
('DRONE001', 'LIGHTWEIGHT', 300, 100, 'IDLE', CURRENT_TIMESTAMP),
('DRONE002', 'LIGHTWEIGHT', 300, 95, 'IDLE', CURRENT_TIMESTAMP),
('DRONE003', 'LIGHTWEIGHT', 300, 90, 'IDLE', CURRENT_TIMESTAMP);

-- Insert Middleweight Drones
INSERT INTO drones (serial_number, model, weight_limit, battery_capacity, state, last_state_change)
VALUES 
('DRONE004', 'MIDDLEWEIGHT', 400, 100, 'IDLE', CURRENT_TIMESTAMP),
('DRONE005', 'MIDDLEWEIGHT', 400, 95, 'IDLE', CURRENT_TIMESTAMP),
('DRONE006', 'MIDDLEWEIGHT', 400, 90, 'IDLE', CURRENT_TIMESTAMP);

-- Insert Cruiserweight Drones
INSERT INTO drones (serial_number, model, weight_limit, battery_capacity, state, last_state_change)
VALUES 
('DRONE007', 'CRUISERWEIGHT', 450, 100, 'IDLE', CURRENT_TIMESTAMP),
('DRONE008', 'CRUISERWEIGHT', 450, 95, 'IDLE', CURRENT_TIMESTAMP);

-- Insert Heavyweight Drones
INSERT INTO drones (serial_number, model, weight_limit, battery_capacity, state, last_state_change)
VALUES 
('DRONE009', 'HEAVYWEIGHT', 500, 100, 'IDLE', CURRENT_TIMESTAMP),
('DRONE010', 'HEAVYWEIGHT', 500, 95, 'IDLE', CURRENT_TIMESTAMP); 