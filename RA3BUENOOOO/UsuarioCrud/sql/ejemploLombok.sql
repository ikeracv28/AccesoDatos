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


INSERT INTO usuario ( username, password) values ('Iker','$2a$12$CzdU7n41SWsBnlPVerrdu.rAHZGeeYxXqkZYyyraM0YxrT6ry4b.2');
INSERT INTO usuario ( username, password) values ('Rafa','$2a$12$CzdU7n41SWsBnlPVerrdu.rAHZGeeYxXqkZYyyraM0YxrT6ry4b.2');

insert into rol (nombre) values ('admin');
insert into rol (nombre) values ('user');

insert into usuarioRol(id, idRol) values (1, 1);
insert into usuarioRol(id, idRol) values (2, 2);

select * from usuario;



DROP TABLE IF EXISTS SPRING_SESSION_ATTRIBUTES;
DROP TABLE IF EXISTS SPRING_SESSION;


-- 2. Crear tabla principal de sesiones
CREATE TABLE SPRING_SESSION (
                                PRIMARY_ID CHAR(36) NOT NULL,
                                SESSION_ID CHAR(36) NOT NULL,
                                CREATION_TIME BIGINT NOT NULL,
                                LAST_ACCESS_TIME BIGINT NOT NULL,
                                MAX_INACTIVE_INTERVAL INT NOT NULL,
                                EXPIRY_TIME BIGINT NOT NULL,
                                PRINCIPAL_NAME VARCHAR(100),
                                CONSTRAINT PK_SPRING_SESSION PRIMARY KEY (PRIMARY_ID)
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

-- 3. Crear índices para rendimiento
CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

-- 4. Crear tabla de atributos (donde se guardan los datos de sesión)
CREATE TABLE SPRING_SESSION_ATTRIBUTES (
                                           SESSION_PRIMARY_ID CHAR(36) NOT NULL,
                                           ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
                                           ATTRIBUTE_BYTES BLOB NOT NULL,
                                           CONSTRAINT PK_SPRING_SESSION_ATTRIBUTES PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
                                           CONSTRAINT FK_SPRING_SESSION_ATTRIBUTES_SPRING_SESSION FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION (PRIMARY_ID) ON DELETE CASCADE
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

