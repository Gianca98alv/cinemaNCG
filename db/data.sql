USE cinedb;

INSERT INTO usuario (idUsuario, contrasena, nombre, tipo) VALUES ('123', 'abc', 'Davide Castrovilli', 1);
INSERT INTO usuario (idUsuario, contrasena, nombre, tipo) VALUES ('456', 'def', 'Giacomo Bonaventura', 0);

INSERT INTO sala (fila, columna) VALUES (20, 20);
INSERT INTO sala (fila, columna) VALUES (15, 23);
INSERT INTO sala (fila, columna) VALUES (12, 15);

INSERT INTO pelicula (idPelicula, poster, duracion, clasificacion, estreno) VALUES ("It", LOAD_FILE('E:/it.jpg'), '02:15', 'R', 0);