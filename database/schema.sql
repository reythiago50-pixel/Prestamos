-- Sistema de préstamos de herramientas · PostgreSQL
-- Crear primero la base de datos:  CREATE DATABASE prestamos_db;
-- Luego conectarse a ella y ejecutar este script.

DROP TABLE IF EXISTS prestamos;
DROP TABLE IF EXISTS herramientas;

CREATE TABLE herramientas (
    id        BIGSERIAL    PRIMARY KEY,
    nombre    VARCHAR(100) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    cantidad  INTEGER      NOT NULL CHECK (cantidad >= 0)   -- unidades disponibles
);

CREATE TABLE prestamos (
    id              BIGSERIAL    PRIMARY KEY,
    responsable     VARCHAR(120) NOT NULL,
    fecha_prestamo  DATE         NOT NULL,
    cantidad        INTEGER      NOT NULL CHECK (cantidad > 0),
    herramienta_id  BIGINT       NOT NULL,
    CONSTRAINT fk_prestamo_herramienta
        FOREIGN KEY (herramienta_id) REFERENCES herramientas (id)
);

CREATE INDEX idx_prestamos_herramienta ON prestamos (herramienta_id);

-- Datos de ejemplo (mínimo 5 herramientas)
INSERT INTO herramientas (nombre, categoria, cantidad) VALUES
    ('Taladro inalámbrico', 'Eléctricas', 5),
    ('Multímetro digital',  'Medición',   8),
    ('Soldador de estaño',  'Electrónica', 6),
    ('Destornillador set',  'Manuales',  12),
    ('Osciloscopio',        'Medición',   2);
