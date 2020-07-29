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

  INSERT INTO `airport`.`countries`
  VALUES (1, 'Belarus');

  INSERT INTO `airport`.`cities`
  VALUES (1, 'Minsk', 1);

  INSERT INTO `airport`.`airports`
  VALUES (1, 'Minsk-1', 'MSQ1', 1),
         (2, 'Minsk-2', 'MSQ2', 1);

  INSERT INTO `airport`.`roles`
  VALUES (1, 'steward'),(2, 'dispatcher'),(3, 'pilot'),(4, 'admin');

  INSERT INTO `airport`.`users`
  VALUES (1, 1, 'masha17', '12345', 'Мария', 'Аленская', 'alienskaya17@gmail.com', 2017),
         (2, 2, 'anya333', '12345', 'Аня', 'Корытько', 'anni111@gmail.com',2003),
         (3, 3, 'vladvlad', '12345', 'Владислав', 'Ясницкий', 'vlad-yas@gmail.com', 2005),
         (4, 4, 'nastya1', '12345', 'Анастасия', 'Роднова', 'rodnovanastya@gmail.com', 2017);

  INSERT INTO `airport`.`flight-teams`
  VALUES (1, '2020-07-12', 'A1', 3),
         (2, '2020-07-15', 'A2', 3),
         (3, '2020-07-15', 'N1', null);

  INSERT INTO `airport`.`flight-teams-m2m-users`
  VALUES (1,1),
         (1,3),
         (2,1),
         (2,3);

  INSERT INTO `airport`.`plane-models`
  VALUES (1,'Airbus 123'),
         (2,'Airbus 828');

  INSERT INTO `airport`.`planes`
  VALUES (1,'A-2772',1),
         (2,'A-8281',2);

  INSERT INTO `airport`.`flights`
  VALUES (1, 'KL 2112', 1, 1, 1, 2, '2020-07-26', '2020-07-26', '19:53:00', '20:53:00', 'Scheduled', 2),
         (2, 'TR 1718', 2, null, 2, 1, '2020-07-27', '2020-07-27', '13:53:00', '17:53:00', 'Scheduled', 2);