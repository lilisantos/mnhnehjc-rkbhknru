CREATE TABLE IF NOT EXISTS sensor(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS metrics(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `sensor_id` bigint(20) NOT NULL,
    `date` DATE NOT NULL,
    `temperature` DOUBLE,
    `humidity` DOUBLE,
    `wind_speed` DOUBLE,
    `precipitation` DOUBLE,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`sensor_id`) REFERENCES sensor(id)
);