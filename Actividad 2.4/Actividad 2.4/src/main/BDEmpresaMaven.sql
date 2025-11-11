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
SELECT * FROM empleados;

SHOW CREATE PROCEDURE incrementar_salario\G
DELIMITER //
CREATE PROCEDURE incrementar_salario(IN empId INT, IN incremento DOUBLE, OUT nuevoSalario DOUBLE)
BEGIN
	-- Esto es para actualizarlo
    update empleados set salario = salario + incremento where id =  empId;
    
    -- Y esto para mostrarlo ya actualizado
    select salario into nuevoSalario from empleados where id = empId;
END //
DELIMITER ;

SHOW CREATE PROCEDURE incrementar_salario\G



DELIMITER //
CREATE PROCEDURE obtener_empleado(IN empId INT)
BEGIN
    SELECT id, nombre FROM empleados WHERE id = empId;
END //
DELIMITER ;