-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema smart_trade
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema smart_trade
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `smart_trade` DEFAULT CHARACTER SET utf8 ;
USE `smart_trade` ;

-- -----------------------------------------------------
-- Table `smart_trade`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smart_trade`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  `verification` VARCHAR(10) NOT NULL,
  `created_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smart_trade`.`brand`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smart_trade`.`brand` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smart_trade`.`model`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smart_trade`.`model` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `brand_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_model_brand_idx` (`brand_id` ASC) VISIBLE,
  CONSTRAINT `fk_model_brand`
    FOREIGN KEY (`brand_id`)
    REFERENCES `smart_trade`.`brand` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smart_trade`.`color`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smart_trade`.`color` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smart_trade`.`storage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smart_trade`.`storage` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smart_trade`.`quality`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smart_trade`.`quality` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smart_trade`.`status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smart_trade`.`status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smart_trade`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smart_trade`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` TEXT NOT NULL,
  `model_id` INT NOT NULL,
  `description` TEXT NOT NULL,
  `price` DOUBLE NOT NULL,
  `qty` INT NOT NULL,
  `color_id` INT NOT NULL,
  `storage_id` INT NOT NULL,
  `condition_id` INT NOT NULL,
  `status_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `created_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_product_model1_idx` (`model_id` ASC) VISIBLE,
  INDEX `fk_product_color1_idx` (`color_id` ASC) VISIBLE,
  INDEX `fk_product_storage1_idx` (`storage_id` ASC) VISIBLE,
  INDEX `fk_product_condition1_idx` (`condition_id` ASC) VISIBLE,
  INDEX `fk_product_status1_idx` (`status_id` ASC) VISIBLE,
  INDEX `fk_product_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_model1`
    FOREIGN KEY (`model_id`)
    REFERENCES `smart_trade`.`model` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_color1`
    FOREIGN KEY (`color_id`)
    REFERENCES `smart_trade`.`color` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_storage1`
    FOREIGN KEY (`storage_id`)
    REFERENCES `smart_trade`.`storage` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_condition1`
    FOREIGN KEY (`condition_id`)
    REFERENCES `smart_trade`.`quality` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_status1`
    FOREIGN KEY (`status_id`)
    REFERENCES `smart_trade`.`status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `smart_trade`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smart_trade`.`city`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smart_trade`.`city` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smart_trade`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smart_trade`.`address` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `line1` TEXT NOT NULL,
  `line2` TEXT NOT NULL,
  `city_id` INT NOT NULL,
  `postal_code` VARCHAR(5) NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_address_city1_idx` (`city_id` ASC) VISIBLE,
  INDEX `fk_address_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_address_city1`
    FOREIGN KEY (`city_id`)
    REFERENCES `smart_trade`.`city` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_address_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `smart_trade`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smart_trade`.`cart`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smart_trade`.`cart` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `qty` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_cart_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_cart_product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_cart_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `smart_trade`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cart_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `smart_trade`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smart_trade`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smart_trade`.`orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `address_id` INT NOT NULL,
  `created_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_order_address1_idx` (`address_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `smart_trade`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_address1`
    FOREIGN KEY (`address_id`)
    REFERENCES `smart_trade`.`address` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smart_trade`.`order_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smart_trade`.`order_status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smart_trade`.`delivery_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smart_trade`.`delivery_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `price` DOUBLE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smart_trade`.`order_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smart_trade`.`order_item` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `product_id` INT NOT NULL,
  `orders_id` INT NOT NULL,
  `qty` INT NOT NULL,
  `order_status_id` INT NOT NULL,
  `delivery_type_id` INT NOT NULL,
  `rating` INT NOT NULL,
  INDEX `fk_order_item_product1_idx` (`product_id` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  INDEX `fk_order_item_order_status1_idx` (`order_status_id` ASC) VISIBLE,
  INDEX `fk_order_item_delivery_type1_idx` (`delivery_type_id` ASC) VISIBLE,
  INDEX `fk_order_item_orders1_idx` (`orders_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_item_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `smart_trade`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_item_order_status1`
    FOREIGN KEY (`order_status_id`)
    REFERENCES `smart_trade`.`order_status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_item_delivery_type1`
    FOREIGN KEY (`delivery_type_id`)
    REFERENCES `smart_trade`.`delivery_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_item_orders1`
    FOREIGN KEY (`orders_id`)
    REFERENCES `smart_trade`.`orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
