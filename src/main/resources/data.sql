-- ================================================================
-- RAILGATE - Seed Data
-- Run this manually in DBeaver after first app startup
-- ================================================================

-- Sample Trains
INSERT IGNORE INTO trains (train_number, train_name, from_station, to_station, is_active, created_at) VALUES
('12301', 'Howrah Rajdhani Express', 'New Delhi', 'Howrah', true, NOW()),
('12951', 'Mumbai Rajdhani Express', 'New Delhi', 'Mumbai Central', true, NOW()),
('12621', 'Tamil Nadu Express', 'New Delhi', 'Chennai Central', true, NOW()),
('12423', 'Dibrugarh Rajdhani', 'New Delhi', 'Dibrugarh', true, NOW()),
('11057', 'Amritsar Express', 'Mumbai CST', 'Amritsar', true, NOW());

-- Sample Crossings (real Indian locations)
INSERT IGNORE INTO crossings (name, code, city, state, highway, latitude, longitude, status, avg_closure_duration_mins, last_status_change, is_active, created_at) VALUES
('Noida Sector 18 Level Crossing', 'NDA-18', 'Noida', 'Uttar Pradesh', 'NH-24', 28.5672, 77.3215, 'OPEN', 8, NOW(), true, NOW()),
('Gurgaon Golf Course Crossing', 'GGN-GC', 'Gurugram', 'Haryana', 'NH-48', 28.4595, 77.0266, 'OPEN', 10, NOW(), true, NOW()),
('Andheri East Level Crossing', 'MUM-AE', 'Mumbai', 'Maharashtra', NULL, 19.1136, 72.8697, 'OPEN', 7, NOW(), true, NOW()),
('Howrah Bridge Approach Crossing', 'KOL-HW', 'Kolkata', 'West Bengal', 'NH-12', 22.5851, 88.3468, 'OPEN', 12, NOW(), true, NOW()),
('Guindy Industrial Crossing', 'CHN-GI', 'Chennai', 'Tamil Nadu', NULL, 13.0067, 80.2206, 'OPEN', 9, NOW(), true, NOW()),
('Kanpur Central Crossing', 'KNP-CN', 'Kanpur', 'Uttar Pradesh', 'NH-19', 26.4499, 80.3319, 'OPEN', 8, NOW(), true, NOW()),
('Pune Station East Gate', 'PNE-EG', 'Pune', 'Maharashtra', 'NH-65', 18.5204, 73.8567, 'OPEN', 11, NOW(), true, NOW()),
('Ahmedabad Kalupur Crossing', 'AMD-KP', 'Ahmedabad', 'Gujarat', 'NH-47', 23.0225, 72.5714, 'OPEN', 8, NOW(), true, NOW());

-- Admin user (password: Admin@123)
-- Password hash for "Admin@123" with BCrypt
INSERT IGNORE INTO users (name, email, password, role, is_active, created_at) VALUES
('Admin', 'admin@railgate.in', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TiGniMm.8G8y.7MzMT9KANLmF1Ry', 'ADMIN', true, NOW());
