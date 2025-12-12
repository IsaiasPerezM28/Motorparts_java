# Scripts de Base de Datos para MotorParts

## Instrucciones de Instalación

### 1. Crear la Base de Datos y Tablas

Ejecuta el script principal en MySQL:

```bash
mysql -u root -p < tienda7815.sql
```

O desde MySQL Workbench/HeidiSQL:
- Abre el archivo `tienda7815.sql`
- Ejecuta todo el script

### 2. Insertar Usuarios de Ejemplo

Ejecuta el script de usuarios:

```bash
mysql -u root -p tienda7815 < insertar_usuarios_ejemplo.sql
```

## Usuarios de Ejemplo

Después de ejecutar los scripts, tendrás estos usuarios disponibles:

| Documento | Nombre | Contraseña | Tipo | Descripción |
|-----------|--------|------------|------|-------------|
| 123456789 | Administrador Principal | admin123 | A | Administrador del sistema |
| 987654321 | Juan Pérez | cliente123 | C | Cliente |
| 456789123 | María González | vendedor123 | V | Vendedor |

## Estructura de Tablas

### Tabla: usuario
- `id`: INT AUTO_INCREMENT PRIMARY KEY
- `doc`: INT NOT NULL UNIQUE (Documento de identidad)
- `nombre`: VARCHAR(100) NOT NULL
- `correo`: VARCHAR(100)
- `pass`: VARCHAR(255) NOT NULL (Contraseña encriptada SHA-256)
- `tipo`: CHAR(1) NOT NULL (A=Administrador, C=Cliente, V=Vendedor)

### Tabla: proveedor
- `id`: INT AUTO_INCREMENT PRIMARY KEY
- `nit`: VARCHAR(50) NOT NULL UNIQUE
- `nombre`: VARCHAR(100) NOT NULL
- `tel`: VARCHAR(20)

### Tabla: producto
- `id`: INT AUTO_INCREMENT PRIMARY KEY
- `cod`: VARCHAR(50) NOT NULL UNIQUE
- `nombre`: VARCHAR(100) NOT NULL
- `descr`: TEXT
- `precio`: DECIMAL(10,2) NOT NULL
- `exist`: INT NOT NULL (Existencias)
- `fven`: DATE (Fecha de vencimiento)
- `foto`: VARCHAR(255) (Ruta de la imagen)
- `id_prov`: INT NOT NULL (Foreign Key a proveedor)

## Notas Importantes

1. **Contraseñas**: Las contraseñas en la base de datos están encriptadas con SHA-256. El sistema las encripta automáticamente al guardar.

2. **Crear tu propio usuario**: Si quieres crear un usuario manualmente, primero encripta la contraseña usando SHA-256, o déjala en texto plano y el sistema la encriptará al guardar.

3. **Base de datos**: Asegúrate de que la base de datos `tienda7815` exista antes de ejecutar los scripts.

## Verificación

Después de ejecutar los scripts, verifica que todo esté correcto:

```sql
USE tienda7815;

SHOW TABLES;

SELECT 'Usuarios' as tabla, COUNT(*) as total FROM usuario
UNION ALL
SELECT 'Proveedores', COUNT(*) FROM proveedor
UNION ALL
SELECT 'Productos', COUNT(*) FROM producto;

SELECT id, doc, nombre, correo, tipo FROM usuario;
```


