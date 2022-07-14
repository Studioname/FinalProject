-- MySQL Script generated by MySQL Workbench
-- Mon Jul 11 11:56:02 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema FinalProject
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `FinalProject` ;

-- -----------------------------------------------------
-- Schema FinalProject
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `FinalProject` DEFAULT CHARACTER SET utf8 ;
USE `FinalProject` ;

-- -----------------------------------------------------
-- Table `FinalProject`.`Play`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `FinalProject`.`Play` ;

CREATE TABLE IF NOT EXISTS `FinalProject`.`Play` (
  `PlayId` INT NOT NULL AUTO_INCREMENT,
  `PlayTitle` VARCHAR(255) NOT NULL,
  `PlayType` INT NOT NULL,
  `PlayDescription` VARCHAR(1000) NOT NULL,
  `PlayTime` TIME NOT NULL,
  `PlayDate` DATE NOT NULL,
  `PlayDuration` TIME NOT NULL,
  `PlayCirclePrice` INT NOT NULL,
  `PlayStallsPrice` INT NOT NULL,
  `PlayLanguage` VARCHAR(255) NULL,
  `PlayMusicalAccompaniment` INT NOT NULL,
  PRIMARY KEY (`PlayId`))
  AUTO_INCREMENT = 1
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FinalProject`.`MainPerformer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `FinalProject`.`MainPerformer` ;

CREATE TABLE IF NOT EXISTS `FinalProject`.`MainPerformer` (
  `MainPerformer` VARCHAR(255) NOT NULL,
  `PlayId` INT NOT NULL,
  INDEX `fk_MainPerformer_Play1_idx` (`PlayId` ASC) VISIBLE,
  CONSTRAINT `fk_MainPerformer_Play1`
    FOREIGN KEY (`PlayId`)
    REFERENCES `FinalProject`.`Play` (`PlayId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FinalProject`.`Customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `FinalProject`.`Customer` ;

CREATE TABLE IF NOT EXISTS `FinalProject`.`Customer` (
  `CustomerId` INT NOT NULL AUTO_INCREMENT,
  `CustomerUsername` VARCHAR(255) NOT NULL,
  `CustomerPassword` VARCHAR(255) NOT NULL,
  `CustomerForename` VARCHAR(255) NOT NULL,
  `CustomerSurname` VARCHAR(255) NOT NULL,
  `CustomerAddress` VARCHAR(255) NOT NULL,
  `CustomerTelephone` VARCHAR(255) NOT NULL,
  `CustomerEmail` VARCHAR(255) NOT NULL,
  `CustomerPaymentDetails` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`CustomerId`))
  AUTO_INCREMENT = 1
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FinalProject`.`Booking`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `FinalProject`.`Booking` ;

CREATE TABLE IF NOT EXISTS `FinalProject`.`Booking` (
  `BookingId` INT NOT NULL AUTO_INCREMENT,
  `PlayId` INT NOT NULL,
  `CustomerId` INT NOT NULL,
  `SeatType` INT NOT NULL,
  `SeatNumber` INT NOT NULL,
  `Concession` INT NOT NULL,
  `IsPostal` INT NOT NULL,
  PRIMARY KEY (`BookingId`),
  INDEX `fk_Booking_Customer1_idx` (`CustomerId` ASC) VISIBLE,
  INDEX `fk_Booking_Play1_idx` (`PlayId` ASC) VISIBLE,
  CONSTRAINT `fk_Booking_Customer1`
    FOREIGN KEY (`CustomerId`)
    REFERENCES `FinalProject`.`Customer` (`CustomerId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Booking_Play1`
    FOREIGN KEY (`PlayId`)
    REFERENCES `FinalProject`.`Play` (`PlayId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    AUTO_INCREMENT = 1
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FinalProject`.`Employee`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `FinalProject`.`Employee` ;

CREATE TABLE IF NOT EXISTS `FinalProject`.`Employee` (
  `EmployeeId` INT NOT NULL AUTO_INCREMENT,
  `EmployeeUsername` VARCHAR(255) NOT NULL,
  `EmployeePassword` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`EmployeeId`))
  AUTO_INCREMENT = 1
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;