
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema FVGames
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema FVGames
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `FVGames` DEFAULT CHARACTER SET utf8 ;
USE `FVGames` ;

-- -----------------------------------------------------
-- Table `FVGames`.`Productos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FVGames`.`Productos` (
  `idProductos` INT NOT NULL AUTO_INCREMENT,
  `Producto` VARCHAR(100) NULL,
  `Cantidad` INT NULL DEFAULT 0,
  `Activo` VARCHAR(45) NULL DEFAULT 'Si',
  `Categoria` VARCHAR(100) NULL,
  `Precio` VARCHAR(45) NULL,
  PRIMARY KEY (`idProductos`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FVGames`.`Paquetes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FVGames`.`Paquetes` (
  `idPaquete` INT NOT NULL AUTO_INCREMENT,
  `NombrePaquete` VARCHAR(45) NULL,
  `Descuento` VARCHAR(45) NULL,
  PRIMARY KEY (`idPaquete`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FVGames`.`Cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FVGames`.`Cliente` (
  `idCliente` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NULL,
  `Apellidos` VARCHAR(45) NULL,
  `Cedula` VARCHAR(45) NULL,
  `Direccion` VARCHAR(45) NULL,
  `Email` VARCHAR(45) NULL,
  `Dinero` VARCHAR(45) NULL,
  `MetodoPago` VARCHAR(45) NULL,
  `Foto` VARCHAR(450) NULL,
  `NumeroTarjeta` VARCHAR(450) NULL,
  `NumeroCuentaBanco` VARCHAR(450) NULL,
  `Contrasena` VARCHAR(45) NULL,
  `TipoCliente` VARCHAR(45) NULL DEFAULT 'Cliente',
  PRIMARY KEY (`idCliente`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FVGames`.`CarritoCompras`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FVGames`.`CarritoCompras` (
  `idCarritoCompras` INT NOT NULL AUTO_INCREMENT,
  `idCliente` INT NULL,
  `idProducto` INT NULL,
  `Estado` VARCHAR(45) NULL,
  PRIMARY KEY (`idCarritoCompras`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
