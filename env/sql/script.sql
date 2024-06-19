CREATE DATABASE test1;
CREATE DATABASE test;
USE test;

CREATE TABLE `author` (
  `author_id` bigint NOT NULL AUTO_INCREMENT,
  `creation_date` datetime(6) DEFAULT NULL,
  `modification_date` datetime(6) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`author_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `person` (
  `date_of_birth` date DEFAULT NULL,
  `pesel` int DEFAULT NULL,
  `creation_date` datetime(6) DEFAULT NULL,
  `person_id` bigint NOT NULL AUTO_INCREMENT,
  `modification_date` datetime(6) DEFAULT NULL,
  `adress_city` varchar(255) DEFAULT NULL,
  `adress_house_number` varchar(255) DEFAULT NULL,
  `adress_street` varchar(255) DEFAULT NULL,
  `adress_zip_code` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `book` (
  `book_id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT b'0',
  `book_status` enum('AVAILABLE','RESERVED','BORROWED') NOT NULL,
  `category` enum('IT','FANTASY','HISTORY','THRILLER','ROMANCE','HORROR','SCIENCE_FICTION') NOT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `author_book` (
  `author_id` bigint NOT NULL,
  `book_id` bigint NOT NULL,
  PRIMARY KEY (`author_id`,`book_id`),
  KEY `FKn8665s8lv781v4eojs8jo3jao` (`book_id`),
  CONSTRAINT `FKg7j6ud9d32ll232o9mgo90s57` FOREIGN KEY (`author_id`) REFERENCES `author` (`author_id`),
  CONSTRAINT `FKn8665s8lv781v4eojs8jo3jao` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;