drop database if exists empresa;
CREATE DATABASE empresa;
USE empresa;

CREATE TABLE empleados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    salario DECIMAL(10,2) NOT NULL
);

CREATE TABLE proyectos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    presupuesto DECIMAL(10,2) NOT NULL
);

CREATE TABLE asignaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_empleado INT,
    id_proyecto INT,
    fecha_asignacion DATE,
    FOREIGN KEY (id_empleado) REFERENCES empleados(id),
    FOREIGN KEY (id_proyecto) REFERENCES proyectos(id)
);

-- 3. Procedimiento almacenado para asignar empleados a proyectos
DELIMITER //
CREATE PROCEDURE asignarEmpleadoAProyecto(
    IN p_id_empleado INT,
    IN p_id_proyecto INT,
    IN p_fecha DATE
)
BEGIN
    INSERT INTO asignaciones (id_empleado, id_proyecto, fecha_asignacion)
    VALUES (p_id_empleado, p_id_proyecto, p_fecha);
END //
DELIMITER ;