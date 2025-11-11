drop database if exists empresaMaven;
CREATE DATABASE IF NOT EXISTS empresaMaven;
USE empresaMaven;

CREATE TABLE empleados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    salario DOUBLE
);

INSERT INTO empleados (nombre, salario) VALUES 
('Ana', 1500.00), 
('Luis', 1800.00), 
('Marta', 2000.00), 
('Pedro', 1750.00); 

create table proyectos ( 
	id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    presupuesto decimal(10,2)
);

SELECT * FROM proyectos;


DELIMITER //
CREATE PROCEDURE obtener_empleado(IN empId INT)
BEGIN
    SELECT id, nombre FROM empleados WHERE id = empId;
END //
DELIMITER ;