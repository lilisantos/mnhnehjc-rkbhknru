INSERT INTO sensor (name) VALUES
('sensor1'),
('sensor2');

INSERT INTO metrics (sensor_id, date, temperature, humidity, wind_speed, precipitation) VALUES
(1, '2025-04-02', 13, 82, 10, 0),
(1, '2025-04-03', 10, 74, 21, 0),
(1, '2025-04-04', 12, 82, 12, 0),
(2, '2025-04-05', 16, 80, 22, 0),
(2, '2025-04-06', 16, 70, 15, 0),
(2, '2025-04-09', 8, 75, 25, 20);