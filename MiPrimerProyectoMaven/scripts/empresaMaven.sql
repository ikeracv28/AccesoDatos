CREATE DATABASE IF NOT EXISTS empresaMaven;
USE empresaMaven;

CREATE TABLE empleados (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           nombre VARCHAR(50),
                           salario DOUBLE
);

INSERT INTO empleados (nombre, salario)
VALUES ('Ana', 25000), ('Luis', 28000), ('Marta', 32000);