-- ==========================================
-- SCRIPT: techdam_completo.sql (VERSIÓN SIMPLE)
-- ==========================================

DROP DATABASE IF EXISTS techdam;
CREATE DATABASE techdam;

USE techdam;

-- ==========================================
-- TABLA EMPLEADOS
-- Necesario para:
--  - CRUD Empleados
--  - actualizar_salario_departamento
--  - calcular_coste_proyecto (salario)
-- ==========================================
CREATE TABLE empleados (
    id_empleado   INT AUTO_INCREMENT PRIMARY KEY,
    nombre        VARCHAR(100)   NOT NULL,
    departamento  VARCHAR(50)    NOT NULL,
    salario       DECIMAL(10,2)  NOT NULL,
    activo        BOOLEAN        NOT NULL DEFAULT TRUE
);

CREATE INDEX idx_empleados_departamento
    ON empleados (departamento);
CREATE INDEX idx_empleados_activo
    ON empleados (activo);

-- ==========================================
-- TABLA PROYECTOS
-- Necesario para:
--  - CRUD Proyectos
--  - transacciones sobre presupuesto
--  - asignaciones (relación con empleados)
-- ==========================================
CREATE TABLE proyectos (
    id_proyecto   INT AUTO_INCREMENT PRIMARY KEY,
    nombre        VARCHAR(100)   NOT NULL,
    presupuesto   DECIMAL(12,2)  NOT NULL
);

-- ==========================================
-- TABLA ASIGNACIONES
-- Necesario para:
--  - Relación N:M Empleados–Proyectos
--  - calcular_coste_proyecto (horas_asignadas)
-- ==========================================
CREATE TABLE asignaciones (
    id_asignacion   INT AUTO_INCREMENT PRIMARY KEY,
    id_empleado     INT           NOT NULL,
    id_proyecto     INT           NOT NULL,
    horas_asignadas DECIMAL(8,2)  NOT NULL DEFAULT 0,

    CONSTRAINT fk_asig_empleado
        FOREIGN KEY (id_empleado) REFERENCES empleados(id_empleado)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    CONSTRAINT fk_asig_proyecto
        FOREIGN KEY (id_proyecto) REFERENCES proyectos(id_proyecto)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE INDEX idx_asig_empleado
    ON asignaciones (id_empleado);
CREATE INDEX idx_asig_proyecto
    ON asignaciones (id_proyecto);

-- ==========================================
-- DATOS DE PRUEBA (mínimo 5 por tabla)
-- ==========================================

INSERT INTO empleados (nombre, departamento, salario, activo) VALUES
('Ana',   'Desarrollo', 24000.00, TRUE),
('Luis',  'Desarrollo', 32000.00, TRUE),
('María', 'Marketing',  28000.00, TRUE),
('Javi',  'IT',         30000.00, TRUE),
('Laura', 'RRHH',       23000.00, TRUE);

INSERT INTO proyectos (nombre, presupuesto) VALUES
('Intranet TechDAM',     50000.00),
('App Móvil Clientes',   80000.00),
('Campaña Marketing Q1', 30000.00),
('Migración Servidores', 60000.00),
('Portal Empleado',      45000.00);

INSERT INTO asignaciones (id_empleado, id_proyecto, horas_asignadas) VALUES
(1, 1, 120.0),
(2, 1,  80.0),
(2, 2, 150.0),
(3, 3,  90.0),
(4, 4, 110.0),
(1, 5,  60.0),
(5, 5,  40.0);

-- ==========================================
-- PROCEDIMIENTOS ALMACENADOS
-- 1) actualizar_salario_departamento (ejemplo del enunciado)
-- 2) transferir_presupuesto (para usar en transacciones)
-- ==========================================

DELIMITER $$

-- 1) Actualizar salario por departamento
CREATE PROCEDURE actualizar_salario_departamento(
    IN  p_departamento   VARCHAR(50),
    IN  p_porcentaje     DECIMAL(5,2),
    OUT p_empleados_actualizados INT
)
BEGIN
    UPDATE empleados
    SET salario = salario * (1 + p_porcentaje / 100)
    WHERE departamento = p_departamento
      AND activo = TRUE;

    SET p_empleados_actualizados = ROW_COUNT();
END$$

-- 2) Transferir presupuesto entre proyectos
--    (por ejemplo, para la parte de transacciones)
CREATE PROCEDURE transferir_presupuesto(
    IN  p_proyecto_origen   INT,
    IN  p_proyecto_destino  INT,
    IN  p_monto             DECIMAL(12,2),
    OUT p_exito             BOOLEAN
)
BEGIN
    DECLARE v_presupuesto_origen DECIMAL(12,2);

    -- Comprobar que hay suficiente presupuesto en origen
    SELECT presupuesto
    INTO v_presupuesto_origen
    FROM proyectos
    WHERE id_proyecto = p_proyecto_origen;

    IF v_presupuesto_origen IS NULL OR v_presupuesto_origen < p_monto THEN
        SET p_exito = FALSE;
    ELSE
        UPDATE proyectos
        SET presupuesto = presupuesto - p_monto
        WHERE id_proyecto = p_proyecto_origen;

        UPDATE proyectos
        SET presupuesto = presupuesto + p_monto
        WHERE id_proyecto = p_proyecto_destino;

        SET p_exito = TRUE;
    END IF;
END$$

-- ==========================================
-- FUNCIÓN ALMACENADA
-- calcular_coste_proyecto:
--   SUM( horas_asignadas * (salario/1600) )
-- ==========================================

CREATE FUNCTION calcular_coste_proyecto(p_id_proyecto INT)
RETURNS DECIMAL(14,2)
DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE v_coste_total DECIMAL(14,2);

    SELECT IFNULL(SUM(a.horas_asignadas * (e.salario / 1600)), 0)
    INTO v_coste_total
    FROM asignaciones a
    JOIN empleados e ON a.id_empleado = e.id_empleado
    WHERE a.id_proyecto = p_id_proyecto;

    RETURN v_coste_total;
END$$

DELIMITER ;

-- ==========================================
-- CONSULTAS DE PRUEBA (opcional)
-- ==========================================

-- SELECT * FROM empleados;
SELECT * FROM proyectos;
-- SELECT * FROM asignaciones;
-- SET @n = 0; CALL actualizar_salario_departamento('Desarrollo', 5.0, @n); SELECT @n;
-- SET @ok = FALSE; CALL transferir_presupuesto(1, 2, 1000.00, @ok); SELECT @ok;
-- SELECT calcular_coste_proyecto(1);

-- FIN DEL SCRIPT
