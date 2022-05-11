SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema buddy
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `buddy` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `buddy` ;

-- -----------------------------------------------------
-- Table `buddy`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `buddy`.`user` (
  `id_user` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `buddy_balance` DECIMAL(19,2) NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `firstname` VARCHAR(255) NULL DEFAULT NULL,
  `lastname` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id_user`))
ENGINE = InnoDB
AUTO_INCREMENT = 13
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `buddy`.`bank_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `buddy`.`bank_account` (
  `id_bank_account` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `bic` VARCHAR(255) NULL DEFAULT NULL,
  `customize_name` VARCHAR(255) NULL DEFAULT NULL,
  `iban` VARCHAR(255) NULL DEFAULT NULL,
  `user_id_user` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id_bank_account`),
  UNIQUE INDEX `UK_2u5scpbwudtqa7wgwfar9mn85` (`user_id_user` ASC) VISIBLE,
  CONSTRAINT `FK5tu08je4w2ewpxwyopj12sbje`
    FOREIGN KEY (`user_id_user`)
    REFERENCES `buddy`.`user` (`id_user`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `buddy`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `buddy`.`role` (
  `id_role` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id_role`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `buddy`.`transfer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `buddy`.`transfer` (
  `id_transfer` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `amount` DECIMAL(19,2) NULL DEFAULT NULL,
  `amount_fee` DECIMAL(19,2) NULL DEFAULT NULL,
  `date_of_transaction` DATETIME(6) NULL DEFAULT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `receiver_id_user` BIGINT(20) NULL DEFAULT NULL,
  `sender_id_user` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id_transfer`),
  INDEX `FKkl4y2f71acxj8t45b2b6d8yj2` (`receiver_id_user` ASC) VISIBLE,
  INDEX `FK520p05kny29xw7jhdx38aoto8` (`sender_id_user` ASC) VISIBLE,
  CONSTRAINT `FK520p05kny29xw7jhdx38aoto8`
    FOREIGN KEY (`sender_id_user`)
    REFERENCES `buddy`.`user` (`id_user`),
  CONSTRAINT `FKkl4y2f71acxj8t45b2b6d8yj2`
    FOREIGN KEY (`receiver_id_user`)
    REFERENCES `buddy`.`user` (`id_user`))
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `buddy`.`user__friend`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `buddy`.`user__friend` (
  `user_id` BIGINT(20) NOT NULL,
  `friend_id` BIGINT(20) NOT NULL,
  INDEX `FK2v0a2djyqwqi8cthajrn2atvg` (`friend_id` ASC) VISIBLE,
  INDEX `FKc683hv7gjdekil7nko8j1u7ns` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK2v0a2djyqwqi8cthajrn2atvg`
    FOREIGN KEY (`friend_id`)
    REFERENCES `buddy`.`user` (`id_user`),
  CONSTRAINT `FKc683hv7gjdekil7nko8j1u7ns`
    FOREIGN KEY (`user_id`)
    REFERENCES `buddy`.`user` (`id_user`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `buddy`.`user__roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `buddy`.`user__roles` (
  `user_id_user` BIGINT(20) NOT NULL,
  `role_list_id_role` BIGINT(20) NOT NULL,
  INDEX `FKji9yijybwjndibn020xmbj2fe` (`role_list_id_role` ASC) VISIBLE,
  INDEX `FK408xg5ifeh4jbs1baecexr95f` (`user_id_user` ASC) VISIBLE,
  CONSTRAINT `FK408xg5ifeh4jbs1baecexr95f`
    FOREIGN KEY (`user_id_user`)
    REFERENCES `buddy`.`user` (`id_user`),
  CONSTRAINT `FKji9yijybwjndibn020xmbj2fe`
    FOREIGN KEY (`role_list_id_role`)
    REFERENCES `buddy`.`role` (`id_role`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Insert role
-- -----------------------------------------------------
SELECT * FROM buddy.role
LIMIT 0, 50

INSERT INTO `` (`id_role`,`name`)
VALUES
(1,'USER'),
(2,'ADMIN');

-- -----------------------------------------------------
-- Insert User
-- -----------------------------------------------------
SELECT * FROM buddy.user
LIMIT 0, 50

INSERT INTO `` (`id_user`,`buddy_balance`,`email`,`firstname`,`lastname`,`password`)
VALUES
(3,94.40,'ap@mail.com','Arthur','Pons','$2a$10$L7ukfxzzYMJiiXhnojjYjeTcwUOWXTYau15uA1FjvXbGksAkf.oJW'),
(4,102.50,'ad@mail.com','Angelina','Dupond','$2a$10$5KxtfkvnRbfwZvZ1/9.Rq.Z7bMICc3WOohU4IB5/VkWgCsMeu7tAK'),
(5,0.00,'gb@mail.com','Gabriel','Berger','$2a$10$iC3dYcFLocLVTfgQwUM8W..wAkz3e4akfc66RLVaL0pCp5k1tybaG'),
(6,0.00,'lm@mail.com','Louis','Morel','$2a$10$l6ZjoGaYISyBcOKcA5rzIurCnGmYb4Ek22fyr/rQUsgboNtWf.8NK'),
(7,0.00,'jc@mail.com','Jules','Collet','$2a$10$3ns3N8X4mO2Bgacylb91..c9SMNEJKhkfIeQrOOTV129D8EnfI4aa'),
(8,0.00,'hl@mail.com','Hugo','Lopez','$2a$10$cGJ1JVDd8xxljiknkUKL3e6q5alSjeiTdN23m0lJ4HAhlqW7Tudv.'),
(9,0.00,'jd@mail.com','Jade','Dupuy','$2a$10$pjWH/7KJjPVoxCA4Y6/IdOvPifkJyuACAah4NzXlhKTOFc21KEJ1S'),
(10,56.00,'lb@mail.com','Louise','Bertin','$2a$10$SsmTVs6QEAb6wrI5VBetzeFfZuDqqzkKYpkl6OjvVOad70syjqYBa'),
(11,0.00,'mp@mail.com','Mia','Petit','$2a$10$t3KdVUWxLlrfAmNwH0cPsOUAO76IwcXB9bIWQ6Tge3el5QFNq8mgu'),
(12,0.00,'admin@mail.com','admin','admin','$2a$10$mVu7pkHcUAfRr.ROhWBe1e9AUYd6tsosdyNXSKq1aO8zgLkT7ssNW'),

-- -----------------------------------------------------
-- Insert user__roles
-- -----------------------------------------------------
SELECT * FROM buddy.user__roles
LIMIT 0, 50

INSERT INTO `` (`user_id_user`,`role_list_id_role`)
VALUES 
(3,1), (4,1), (5,1), (6,1), (7,1),
(8,1), (9,1), (10,1), (11,1), (12,2);

-- -----------------------------------------------------
-- Insert user__friend
-- -----------------------------------------------------
SELECT * FROM buddy.user__friend
LIMIT 0, 50

INSERT INTO `` (`user_id`,`friend_id`)
VALUES
(9,3), (9,4), (10,3), (10,4), (7,5), (7,11), (6,5),
(6,11), (6,8), (8,3), (8,5), (8,6), (8,11), (4,5),
(4,9), (5,4), (5,6), (5,7), (5,8), (3,10), (3,9), 
(3,8), (3,11), (11,6), (11,7), (11,8), (11,3);

-- -----------------------------------------------------
-- Insert bank_account
-- -----------------------------------------------------
SELECT * FROM buddy.bank_account
LIMIT 0, 50

INSERT INTO `` (`id_bank_account`,`bic`,`customize_name`,`iban`,`user_id_user`)
VALUES
(1,'bic','la bank que j aime','FR',3),
(2,'bic','bank','FR',4);

-- -----------------------------------------------------
-- Insert transfer
-- -----------------------------------------------------
SELECT * FROM buddy.transfer
LIMIT 0, 50

INSERT INTO `` (`id_transfer`,`amount`,`amount_fee`,`date_of_transaction`,`description`,`receiver_id_user`,`sender_id_user`)
VALUES
(1,100.00,5.00,'2022-03-02 17:37:36.868303','BANK TRANSFER',3,3),
(2,-23.00,1.15,'2022-03-03 10:32:15.218633','BANK TRANSFER',3,3),
(3,10.00,0.50,'2022-03-03 10:32:45.618616','restau',4,3),
(4,5.00,0.25,'2022-03-03 10:33:28.532352','tel',11,3),
(5,100.25,5.01,'2022-03-03 10:35:01.862404','BANK TRANSFER',4,4),
(6,-55.00,2.75,'2022-03-03 10:35:10.083126','BANK TRANSFER',4,4),
(7,-15.00,-0.75,'2022-03-08 09:57:20.151507','BANK TRANSFER',3,3),
(8,-89.00,-4.45,'2022-03-08 09:57:29.969684','BANK TRANSFER',3,3),
(9,100.00,5.00,'2022-03-08 09:58:39.015256','BANK TRANSFER',3,3),
(10,45.24,-2.26,'2022-03-08 09:58:47.801637','BANK TRANSFER',3,3),
(11,2.00,-0.10,'2022-03-08 09:58:54.421347','BANK TRANSFER',3,3),
(13,5.00,0.25,'2022-03-08 12:51:40.861255','crêche',10,3),
(14,1.00,0.05,'2022-03-08 13:26:01.748987','r',10,3),
(15,50.00,2.50,'2022-03-08 13:48:25.167074','a',10,3),
(16,-50.00,2.50,'2022-03-08 14:24:01.326956','BANK TRANSFER',3,3),
(17,100.00,5.00,'2022-03-08 14:27:57.748624','BANK TRANSFER',3,3);