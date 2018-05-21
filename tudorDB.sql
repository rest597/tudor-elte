# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.6.38)
# Database: tudorDB
# Generation Time: 2018-05-21 12:12:37 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table answer
# ------------------------------------------------------------

DROP TABLE IF EXISTS `answer`;

CREATE TABLE `answer` (
  `answer_id` int(11) NOT NULL AUTO_INCREMENT,
  `archived` bit(1) DEFAULT NULL,
  `body` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `rating` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `question_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`answer_id`),
  KEY `FK8frr4bcabmmeyyu60qt7iiblo` (`question_id`),
  KEY `FKet6o2ud595w42a39j0m171i9o` (`user_id`),
  CONSTRAINT `FK8frr4bcabmmeyyu60qt7iiblo` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`),
  CONSTRAINT `FKet6o2ud595w42a39j0m171i9o` FOREIGN KEY (`user_id`) REFERENCES `tudor` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;

INSERT INTO `answer` (`answer_id`, `archived`, `body`, `date`, `rating`, `title`, `question_id`, `user_id`)
VALUES
	(2,b'0','answer jgjkhbjkugkjhkj hliu gulig ilu gliu liu gilug ug ','2018-05-20 17:30:16',4,'the answer',3,8),
	(3,b'0','nkjasbkajbksjabkjsbakjs','2018-05-21 11:45:26',0,'answer',3,8),
	(4,b'1','adjkaksdbkjabsdkjabsd','2018-05-21 11:55:48',5,'answer',5,8),
	(5,b'0','With creativity....','2018-05-21 13:52:11',2,'Simple answer',5,8);

/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table customer
# ------------------------------------------------------------

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `age` int(11) NOT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FKra1cb3fu95r1a0m7aksow0nk4` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;

INSERT INTO `customer` (`age`, `bio`, `name`, `user_id`)
VALUES
	(29,'SW Tester','Bill Daniels',10);

/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table question
# ------------------------------------------------------------

DROP TABLE IF EXISTS `question`;

CREATE TABLE `question` (
  `question_id` int(11) NOT NULL AUTO_INCREMENT,
  `archived` bit(1) DEFAULT NULL,
  `body` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `specialization` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`question_id`),
  KEY `FK1bsxk9y50eku8xtaxw029vdie` (`user_id`),
  CONSTRAINT `FK1bsxk9y50eku8xtaxw029vdie` FOREIGN KEY (`user_id`) REFERENCES `customer` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;

INSERT INTO `question` (`question_id`, `archived`, `body`, `date`, `specialization`, `title`, `user_id`)
VALUES
	(3,b'1','myquestion myquestion myquestion myquestion myquestion myquestion myquestion','2018-05-20 17:30:16','hacking','myquestion',10),
	(5,b'0','How can I hack?','2018-05-21 11:29:02','hacking','New question',10);

/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table tudor
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tudor`;

CREATE TABLE `tudor` (
  `age` int(11) NOT NULL,
  `experience` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `specialization` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FKf11tbnsar9pew316iadvu58ug` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `tudor` WRITE;
/*!40000 ALTER TABLE `tudor` DISABLE KEYS */;

INSERT INTO `tudor` (`age`, `experience`, `name`, `specialization`, `user_id`)
VALUES
	(33,'SW Specialist','dr Anna Grim','hacking',8);

/*!40000 ALTER TABLE `tudor` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table users
# ------------------------------------------------------------

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;

INSERT INTO `users` (`user_id`, `password`, `type`, `username`)
VALUES
	(8,'$2a$10$p5QuJQFLrsxV9N8u7u6z/Or9JhktLDKYfvnKRfI1pIlDRJaoEHFE2','Tudor','tudor'),
	(9,'$2a$10$mEn46hWOSMavozp3cDy6/u9jzoIE.QefufuXxdhTVtvskPzwwpkQa','Admin','admin'),
	(10,'$2a$10$0LUdSPL8ipbtUlSE4sSuNO1wkcCEVqrv7aChPlSyoZmXVFF9blAsm','Customer','customer');

/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
