CREATE DATABASE IF NOT EXISTS tienda7815;
USE tienda7815;

CREATE TABLE IF NOT EXISTS proveedor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nit VARCHAR(50) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    tel VARCHAR(20),
    INDEX idx_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    doc INT NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100),
    pass VARCHAR(255) NOT NULL,
    tipo CHAR(1) NOT NULL DEFAULT 'C',
    INDEX idx_doc (doc),
    INDEX idx_tipo (tipo),
    CHECK (tipo IN ('A', 'C', 'V'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cod VARCHAR(50) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    descr TEXT,
    precio DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    exist INT NOT NULL DEFAULT 0,
    fven DATE,
    foto VARCHAR(255),
    id_prov INT NOT NULL,
    INDEX idx_cod (cod),
    INDEX idx_nombre (nombre),
    INDEX idx_precio (precio),
    INDEX idx_id_prov (id_prov),
    FOREIGN KEY (id_prov) REFERENCES proveedor(id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO proveedor (nit, nombre, tel) VALUES
('900123456-1', 'Autopartes del Norte S.A.', '3001234567'),
('900234567-2', 'Repuestos Premium Ltda.', '3002345678'),
('900345678-3', 'Distribuidora de Partes S.A.S.', '3003456789'),
('900456789-4', 'Motor Parts Colombia', '3004567890')
ON DUPLICATE KEY UPDATE nombre=nombre;

INSERT INTO usuario (doc, nombre, correo, pass, tipo) VALUES
(123456789, 'Administrador Principal', 'admin@motorparts.com', SHA2('admin123', 256), 'A'),
(987654321, 'Juan Pérez', 'juan.perez@email.com', SHA2('cliente123', 256), 'C'),
(456789123, 'María González', 'maria.gonzalez@email.com', SHA2('vendedor123', 256), 'V')
ON DUPLICATE KEY UPDATE nombre=nombre;

INSERT INTO producto (cod, nombre, descr, precio, exist, fven, foto, id_prov) VALUES
('PROD001', 'Filtro de Aceite Original', 'Filtro de aceite de alta calidad para motores de 4 cilindros', 25000.00, 50, '2025-12-31', 'images/filtro_aceite.jpg', 1),
('PROD002', 'Pastillas de Freno Delanteras', 'Juego de pastillas de freno delanteras para vehículos medianos', 85000.00, 30, '2026-06-30', 'images/pastillas_freno.jpg', 2),
('PROD003', 'Batería 12V 60Ah', 'Batería de plomo-ácido de 12 voltios y 60 amperios hora', 320000.00, 15, '2027-12-31', 'images/bateria_12v.jpg', 1),
('PROD004', 'Aceite Motor 5W-30', 'Aceite sintético para motor, grado 5W-30, 4 litros', 95000.00, 40, '2026-12-31', 'images/aceite_motor.jpg', 3),
('PROD005', 'Bujías Iridio', 'Juego de 4 bujías de iridio de alto rendimiento', 120000.00, 25, '2028-12-31', 'images/bujias_iridio.jpg', 2),
('PROD006', 'Radiador Aluminio', 'Radiador de aluminio para sistema de refrigeración', 450000.00, 10, '2026-12-31', 'images/radiador.jpg', 4),
('PROD007', 'Amortiguadores Delanteros', 'Par de amortiguadores delanteros hidráulicos', 380000.00, 12, '2027-06-30', 'images/amortiguadores.jpg', 1),
('PROD008', 'Correa de Distribución', 'Correa de distribución de caucho reforzado', 180000.00, 20, '2026-12-31', 'images/correa_distribucion.jpg', 3)
ON DUPLICATE KEY UPDATE nombre=nombre;

SHOW TABLES;

DESCRIBE usuario;
DESCRIBE proveedor;
DESCRIBE producto;

SELECT COUNT(*) as total_usuarios FROM usuario;
SELECT COUNT(*) as total_proveedores FROM proveedor;
SELECT COUNT(*) as total_productos FROM producto;

