USE tienda7815;

INSERT INTO usuario (doc, nombre, correo, pass, tipo) VALUES
(123456789, 'Administrador Principal', 'admin@motorparts.com', SHA2('admin123', 256), 'A'),
(987654321, 'Juan Pérez', 'juan.perez@email.com', SHA2('cliente123', 256), 'C'),
(456789123, 'María González', 'maria.gonzalez@email.com', SHA2('vendedor123', 256), 'V')

ON DUPLICATE KEY UPDATE 
    nombre = VALUES(nombre),
    correo = VALUES(correo),
    pass = VALUES(pass),
    tipo = VALUES(tipo);

SELECT id, doc, nombre, correo, tipo, 
       CASE tipo 
           WHEN 'A' THEN 'Administrador'
           WHEN 'C' THEN 'Cliente'
           WHEN 'V' THEN 'Vendedor'
       END as tipo_descripcion
FROM usuario
ORDER BY tipo, nombre;

