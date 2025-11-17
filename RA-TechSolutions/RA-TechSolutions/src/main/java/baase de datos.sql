DROP DATABASE IF EXISTS techsolutions_RA;
CREATE DATABASE techsolutions_RA;

USE techsolutions_RA;

-- TABLA Genero
CREATE TABLE Genero (
    id_genero   INT AUTO_INCREMENT PRIMARY KEY,
    nombre        VARCHAR(100)   NOT NULL,
    descripcion varchar(100)

);

-- TABLA Libro
CREATE TABLE Libro (
    id_libro   INT AUTO_INCREMENT PRIMARY KEY,
    titulo        VARCHAR(100)   NOT NULL,
    autor 		VARCHAR(100)   NOT NULL,
    precio  decimal not null,
    stock int not null, 
    id_genero int,
    
    CONSTRAINT fk_id_genero
        FOREIGN KEY (id_genero) REFERENCES Genero(id_genero)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);


-- TABLA pedido
CREATE TABLE Pedido (
    id_pedido   INT AUTO_INCREMENT PRIMARY KEY,
    cantidad int not null,
    id_libro int not null,
    
	
        CONSTRAINT fk_id_libro
        FOREIGN KEY (id_libro) REFERENCES Libro(id_libro)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        
);

-- TABLA cliente
CREATE TABLE Cliente (
    id_cliente   INT AUTO_INCREMENT PRIMARY KEY,
    nombre        VARCHAR(100)   NOT NULL,
    edad  int not null
    

);

CREATE TABLE Venta (
    id_venta   INT AUTO_INCREMENT PRIMARY KEY,
    precio  	double not null,
    id_pedido 	int not null,
    id_cliente int not null,
     CONSTRAINT fk_id_cliente
        FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
   
     CONSTRAINT fk_id_pedido
        FOREIGN KEY (id_pedido) REFERENCES Pedido(id_pedido)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);



INSERT INTO Genero (nombre, descripcion) VALUES
('Novela', 'tipica telenovela'),
('Terror', 'para cagarte de miedo'),
('Fantasia', 'mundo de yupi' ),
('Amor', 'pasteloso que no veas');


INSERT INTO Libro (titulo, autor, precio, stock) VALUES
('Geronimo Stilton', 'Iker Acevedo', 25.80, 15 ),
('Harry Potter', 'Rafael Medina', 55.90, 20 ),
('Lo Imposible', 'Iris Perez', 30.90, 10 ),
('El Quijote', 'Quevedo', 17.90, 30 );

INSERT INTO Cliente (nombre, edad) VALUES
('Alejandro', 22 ),
('Mario', 18 ),
('Marcos', 20 ),
('Ian', 19 );


INSERT INTO Pedido (id_pedido, cantidad,  id_libro) VALUES
(1, 3,  3),
(2, 6,  2),
(3, 5, 4),
(4, 9,  1);

INSERT INTO Venta (id_venta, precio, id_pedido, id_cliente) VALUES
(1, 92.7, 1, 1),
(2,335.4 , 2, 2),
(3, 89.5 , 3, 3),
(4, 232.2, 4, 4);

select * from Libro;








