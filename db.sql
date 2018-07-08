-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema trade_games
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema trade_games
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `trade_games` DEFAULT CHARACTER SET utf8 ;
USE `trade_games` ;

-- -----------------------------------------------------
-- Table `trade_games`.`games`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trade_games`.`games` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` TINYTEXT NOT NULL,
  `image` VARCHAR(100) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `trade_games`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trade_games`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `full_name` VARCHAR(255) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `trade_games`.`ads`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trade_games`.`ads` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` TINYTEXT NOT NULL,
  `type` TINYINT(1) NOT NULL COMMENT '1 = Procura\n2 = Oferta',
  `price` DECIMAL NOT NULL,
  `created_at` DATETIME NOT NULL,
  `games_id` INT NOT NULL,
  `users_id` INT NOT NULL,
  PRIMARY KEY (`id`, `games_id`, `users_id`),
  INDEX `fk_ads_games1_idx` (`games_id` ASC),
  INDEX `fk_ads_users1_idx` (`users_id` ASC),
  CONSTRAINT `fk_ads_games1`
    FOREIGN KEY (`games_id`)
    REFERENCES `trade_games`.`games` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ads_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `trade_games`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `trade_games`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trade_games`.`roles` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `trade_games`.`users_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trade_games`.`users_roles` (
  `users_id` INT NOT NULL,
  `roles_id` INT NOT NULL,
  PRIMARY KEY (`users_id`, `roles_id`),
  INDEX `fk_users_has_roles_roles1_idx` (`roles_id` ASC),
  INDEX `fk_users_has_roles_users1_idx` (`users_id` ASC),
  CONSTRAINT `fk_users_has_roles_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `trade_games`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_roles_roles1`
    FOREIGN KEY (`roles_id`)
    REFERENCES `trade_games`.`roles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `trade_games`.`comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trade_games`.`comments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `comment` TEXT NOT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NULL,
  `ads_id` INT NOT NULL,
  `users_id` INT NOT NULL,
  PRIMARY KEY (`id`, `ads_id`, `users_id`),
  INDEX `fk_comments_ads1_idx` (`ads_id` ASC),
  INDEX `fk_comments_users1_idx` (`users_id` ASC),
  CONSTRAINT `fk_comments_ads1`
    FOREIGN KEY (`ads_id`)
    REFERENCES `trade_games`.`ads` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comments_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `trade_games`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `trade_games`.`interested`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trade_games`.`interested` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ads_id` INT NOT NULL,
  `users_id` INT NOT NULL,
  PRIMARY KEY (`id`, `ads_id`, `users_id`),
  INDEX `fk_interested_ads1_idx` (`ads_id` ASC),
  INDEX `fk_interested_users1_idx` (`users_id` ASC),
  CONSTRAINT `fk_interested_ads1`
    FOREIGN KEY (`ads_id`)
    REFERENCES `trade_games`.`ads` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_interested_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `trade_games`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
