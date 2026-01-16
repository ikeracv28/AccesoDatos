create database crudUsuarioIker;

use crudUsuarioIker;

create table usuario(
    id_usuario int primary key not null auto_increment,
    nombre varchar (80) not null,
    apellido varchar(150) not null,
    email varchar(150) unique not null,
    password varchar(200) not null
    );