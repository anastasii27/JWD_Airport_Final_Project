-- -----------------------------------------------------
-- Schema airport
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `airport`;
USE `airport` ;

-- -----------------------------------------------------
-- Table `airport`.`countries`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airport`.`countries` (
  `id` INT(11) AUTO_INCREMENT NOT NULL PRIMARY KEY,
  `name` VARCHAR(45) NOT NULL);

-- -----------------------------------------------------
-- Table `airport`.`cities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airport`.`cities` (
  `id` INT(11) AUTO_INCREMENT NOT NULL PRIMARY KEY ,
  `name` VARCHAR(45) NOT NULL,
  `country-id` INT(11) NOT NULL,
  FOREIGN KEY (`country-id`) REFERENCES `countries`(`id`));

-- -----------------------------------------------------
-- Table `airport`.`airports`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airport`.`airports` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(45) NOT NULL,
  `name-abbreviation` VARCHAR(45) NOT NULL,
  `city-id` INT(11) NOT NULL,
   FOREIGN KEY (`city-id`) REFERENCES `cities`(`id`));

-- -----------------------------------------------------
-- Table `airport`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airport`.`roles` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  `title` VARCHAR(45) NOT NULL);

-- -----------------------------------------------------
-- Table `airport`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airport`.`users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `role-id` INT(11) NOT NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` LONGTEXT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `career-start-year` VARCHAR(45) NOT NULL,
  FOREIGN KEY (`role-id`) REFERENCES `roles` (`id`));

-- -----------------------------------------------------
-- Table `airport`.`flight-teams`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airport`.`flight-teams` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `date-of-creating` DATE NOT NULL,
  `short-name` VARCHAR(45) NOT NULL,
  `main-pilot-id` INT(11) NULL DEFAULT NULL,
  FOREIGN KEY (`main-pilot-id`) REFERENCES `users` (`id`)
  ON DELETE SET NULL);

-- -----------------------------------------------------
-- Table `airport`.`flight-teams-m2m-users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airport`.`flight-teams-m2m-users` (
  `flight-team-id` INT(11) NOT NULL,
  `user-id` INT(11) NOT NULL,
  PRIMARY KEY (`flight-team-id`, `user-id`),
  FOREIGN KEY (`flight-team-id`) REFERENCES `airport`.`flight-teams` (`id`),
  FOREIGN KEY (`user-id`) REFERENCES `airport`.`users` (`id`));

-- -----------------------------------------------------
-- Table `airport`.`plane-models`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airport`.`plane-models` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `title` VARCHAR(45) NOT NULL);

-- -----------------------------------------------------
-- Table `airport`.`planes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airport`.`planes` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `number` VARCHAR(45) NOT NULL,
  `model-id` INT(11) NULL DEFAULT NULL,
  FOREIGN KEY (`model-id`) REFERENCES `airport`.`plane-models` (`id`)
  ON DELETE CASCADE);

-- -----------------------------------------------------
-- Table `airport`.`flights`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airport`.`flights` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `flight-number` VARCHAR(45) NOT NULL,
  `plane-id` INT(11) NOT NULL,
  `flight-team-id` INT(11) NULL DEFAULT NULL,
  `departure-airport-id` INT(11) NOT NULL,
  `destination-airport-id` INT(11) NOT NULL,
  `departure-date` DATE NULL DEFAULT NULL,
  `destination-date` DATE NULL DEFAULT NULL,
  `departure-time` TIME NULL DEFAULT NULL,
  `destination-time` TIME NULL DEFAULT NULL,
  `status` ENUM('Arrived', 'Delayed', 'Departed', 'Canceled', 'Scheduled'),
  `dispatcher-id` INT(11) NULL DEFAULT NULL,
  FOREIGN KEY (`plane-id`) REFERENCES `airport`.`planes` (`id`),
  FOREIGN KEY (`departure-airport-id`) REFERENCES `airport`.`airports` (`id`),
  FOREIGN KEY (`destination-airport-id`) REFERENCES `airport`.`airports` (`id`),
  FOREIGN KEY (`flight-team-id`) REFERENCES `airport`.`flight-teams` (`id`),
  FOREIGN KEY (`dispatcher-id`)REFERENCES `airport`.`users` (`id`)
  ON DELETE SET NULL);

