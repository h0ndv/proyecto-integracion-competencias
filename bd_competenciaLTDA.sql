-- Crear base de datos
CREATE DATABASE IF NOT EXISTS `bd_competencia_ltda` DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish2_ci;
USE `bd_competencia_ltda`;

-- Tabla de categorias de productos
CREATE TABLE `tb_cat_productos` (
  `id_categoria` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- Tabla de cargos
CREATE TABLE `tb_cargo` (
  `id_cargo` INT NOT NULL AUTO_INCREMENT,
  `cargo` VARCHAR(50) NOT NULL,
  `descripcion` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_cargo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- Tabla de clientes
CREATE TABLE `tb_cliente` (
  `id_cliente` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NOT NULL,
  `rut` VARCHAR(50) NOT NULL,
  `celular` VARCHAR(50) NOT NULL,
  `correo` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_cliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- Tabla de proveedores
CREATE TABLE `tb_proveedor` (
  `id_proveedor` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NOT NULL,
  `empresa` VARCHAR(50) NOT NULL,
  `celular` VARCHAR(50) NOT NULL,
  `correo` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_proveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- Tabla de usuarios
CREATE TABLE `tb_usuarios` (
  `id_usuario` INT NOT NULL AUTO_INCREMENT,
  `id_cargo` INT NOT NULL,
  `nombre` VARCHAR(50) NOT NULL,
  `rut` VARCHAR(50) NOT NULL,
  `correo` VARCHAR(50) NOT NULL,
  `pin` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- Tabla de productos
CREATE TABLE `tb_productos` (
  `id_producto` INT NOT NULL AUTO_INCREMENT,
  `id_categoria` INT NOT NULL,
  `nombre` VARCHAR(50) NOT NULL,
  `cantidad` INT NOT NULL,
  `precio` INT NOT NULL,
  PRIMARY KEY (`id_producto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- Tabla de ingresos de productos
CREATE TABLE `tb_in_producto` (
  `id_in_producto` INT NOT NULL AUTO_INCREMENT,
  `id_proveedor` INT NOT NULL,
  `id_usuario` INT NOT NULL,
  `fecha` VARCHAR(12) NOT NULL,
  PRIMARY KEY (`id_in_producto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- Tabla de detalle de ingresos
CREATE TABLE `tb_det_in_productos` (
  `id_dt_in_productos` INT NOT NULL AUTO_INCREMENT,
  `id_productos` INT NOT NULL,
  `cantidad` INT NOT NULL,
  PRIMARY KEY (`id_dt_in_productos`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- Tabla de ventas
CREATE TABLE `tb_ventas` (
  `id_venta` INT NOT NULL AUTO_INCREMENT,
  `id_usuario` INT NOT NULL,
  `fecha` VARCHAR(12) NOT NULL,
  `total` INT NOT NULL,
  `tipo_compra` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_venta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- Tabla de detalle de ventas
CREATE TABLE `tb_det_ventas` (
  `id_det_venta` INT NOT NULL AUTO_INCREMENT,
  `id_venta` INT NOT NULL,
  `id_productos` INT NOT NULL,
  `cantidad` INT NOT NULL,
  `precio` INT NOT NULL,
  `total` INT NOT NULL,
  PRIMARY KEY (`id_det_venta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- Tabla de control de asistencia
CREATE TABLE `tb_asistencia` (
  `id_asistencia` INT NOT NULL AUTO_INCREMENT,
  `id_usuario` INT NOT NULL,
  `fecha` DATE NOT NULL,
  `hora_entrada` TIME,
  `hora_salida` TIME,
  `estado` ENUM('ENTRADA', 'COMPLETO') NOT NULL,
  PRIMARY KEY (`id_asistencia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- =============================
-- CLAVES FORANEAS
-- =============================

-- tb_productos -> tb_cat_productos
ALTER TABLE tb_productos
ADD CONSTRAINT fk_producto_categoria 
FOREIGN KEY (id_categoria) REFERENCES tb_cat_productos(id_categoria);

-- tb_usuarios -> tb_cargo
ALTER TABLE tb_usuarios
ADD CONSTRAINT fk_usuario_cargo 
FOREIGN KEY (id_cargo) REFERENCES tb_cargo(id_cargo);

-- tb_in_producto -> tb_proveedor
ALTER TABLE tb_in_producto
ADD CONSTRAINT fk_in_producto_proveedor 
FOREIGN KEY (id_proveedor) REFERENCES tb_proveedor(id_proveedor);

-- tb_in_producto -> tb_usuarios
ALTER TABLE tb_in_producto
ADD CONSTRAINT fk_in_producto_usuario 
FOREIGN KEY (id_usuario) REFERENCES tb_usuarios(id_usuario);

-- tb_det_in_productos -> tb_productos
ALTER TABLE tb_det_in_productos
ADD CONSTRAINT fk_det_in_producto_producto 
FOREIGN KEY (id_productos) REFERENCES tb_productos(id_producto);

-- tb_det_ventas -> tb_ventas
ALTER TABLE tb_det_ventas
ADD CONSTRAINT fk_det_venta_venta 
FOREIGN KEY (id_venta) REFERENCES tb_ventas(id_venta);

-- tb_det_ventas -> tb_productos
ALTER TABLE tb_det_ventas
ADD CONSTRAINT fk_det_venta_producto 
FOREIGN KEY (id_productos) REFERENCES tb_productos(id_producto);

-- tb_ventas -> tb_usuarios
ALTER TABLE tb_ventas
ADD CONSTRAINT fk_venta_usuario 
FOREIGN KEY (id_usuario) REFERENCES tb_usuarios(id_usuario);

-- tb_asistencia -> tb_usuarios
ALTER TABLE tb_asistencia
ADD CONSTRAINT fk_asistencia_usuario 
FOREIGN KEY (id_usuario) REFERENCES tb_usuarios(id_usuario);

-- =============================
-- INSERCION DE DATOS
-- =============================

INSERT INTO tb_cargo (cargo, descripcion) VALUES
('Administrador', 'Administrador del sistema'),
('Vendedor', 'Vendedor de productos'),
('Bodega', 'Encargado de bodega');

INSERT INTO tb_usuarios (id_cargo, nombre, rut, correo, pin) VALUES
(1, 'Administrador', '1234', 'aaa@gmail.com', '1234'),
(2, 'Vendedor', '12345', 'vendedor@gmail.com', '12345'),
(3, 'Bodeguero', '123456', 'bodega@gmail.com', '123456');

INSERT INTO tb_cliente (nombre, rut, celular, correo) VALUES
('seba', '111111111', '123456789', 'cliente@email.com');

INSERT INTO tb_proveedor (nombre, empresa, celular, correo) VALUES
('Nelson', 'Nvidia', '123456789', 'asdasd@gmail.com');

INSERT INTO tb_cat_productos (nombre) VALUES
('Procesador'),
('Placa Madre'),
('Memoria RAM'),
('Fuente de Poder'),
('Gabinete');

INSERT INTO tb_productos (id_categoria, nombre, cantidad, precio) VALUES
(1, 'Intel Core i7-12700K', 10, 3500000),
(2, 'ASUS ROG STRIX B550-F', 5, 180000),
(3, 'Corsair Vengeance 16GB DDR4', 20, 7550),
(4, 'EVGA 750W 80 Plus Gold', 0, 12000),
(5, 'NZXT H510', 15, 9999);

-- Datos de prueba ejemplo para tabla de asistencias
INSERT INTO tb_asistencia (id_usuario, fecha, hora_entrada, hora_salida, estado) VALUES
(1, '2024-01-15', '08:30:00', '17:30:00', 'COMPLETO'),
(1, '2024-01-16', '08:45:00', '17:15:00', 'COMPLETO'),
(1, '2024-01-17', '09:00:00', NULL, 'ENTRADA'),
(1, '2024-01-18', '08:15:00', '18:00:00', 'COMPLETO'),
(1, '2024-01-19', '08:30:00', NULL, 'ENTRADA');