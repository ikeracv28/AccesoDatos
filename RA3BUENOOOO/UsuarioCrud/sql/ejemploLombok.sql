DROP DATABASE IF EXISTS crudUsuarioIker;
CREATE DATABASE IF NOT EXISTS crudUsuarioIker;
USE crudUsuarioIker;

CREATE TABLE usuario(
    id int auto_increment primary key not null,
    username varchar(100) unique not null,
    password varchar(255) not null,
    activo tinyint(1) default 1,
    fecha_creacion timestamp default current_timestamp,
    fecha_actualizacion timestamp default current_timestamp
);

create table rol(
    idRol int auto_increment primary key not null,
    nombre varchar(100) unique not null
);

create table usuarioRol(
    idusuarioRol int auto_increment primary key not null,
    id int not null,
    idRol int not null,
    FOREIGN KEY (id) references usuario(id),
    FOREIGN KEY (idRol) references rol(idRol)
);

select * from usuario;

INSERT INTO usuario ( username, password) values ('Iker','$2a$12$CzdU7n41SWsBnlPVerrdu.rAHZGeeYxXqkZYyyraM0YxrT6ry4b.2');