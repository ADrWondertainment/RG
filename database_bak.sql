CREATE DATABASE  IF NOT EXISTS `ruangong` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ruangong`;
-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: ruangong
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alldept`
--

DROP TABLE IF EXISTS `alldept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alldept` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cid` int DEFAULT NULL,
  `index` int DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `alldept_companies_id_fk` (`cid`),
  CONSTRAINT `alldept_companies_id_fk` FOREIGN KEY (`cid`) REFERENCES `companies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alldept`
--

LOCK TABLES `alldept` WRITE;
/*!40000 ALTER TABLE `alldept` DISABLE KEYS */;
/*!40000 ALTER TABLE `alldept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `allrole`
--

DROP TABLE IF EXISTS `allrole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `allrole` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cid` int DEFAULT NULL,
  `index` int DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `allrole_companies_id_fk` (`cid`),
  CONSTRAINT `allrole_companies_id_fk` FOREIGN KEY (`cid`) REFERENCES `companies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allrole`
--

LOCK TABLES `allrole` WRITE;
/*!40000 ALTER TABLE `allrole` DISABLE KEYS */;
/*!40000 ALTER TABLE `allrole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `answers`
--

DROP TABLE IF EXISTS `answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sid` int DEFAULT NULL,
  `data` json DEFAULT NULL,
  `done` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `answers_sheets_id_fk` (`sid`),
  CONSTRAINT `answers_sheets_id_fk` FOREIGN KEY (`sid`) REFERENCES `sheets` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answers`
--

LOCK TABLES `answers` WRITE;
/*!40000 ALTER TABLE `answers` DISABLE KEYS */;
INSERT INTO `answers` VALUES (1,7,'{\"content\": [{\"id\": \"0\", \"value\": \"A\"}, {\"id\": \"1\", \"value\": [\"5555\"]}]}',0),(2,7,'{\"content\": [{\"id\": \"0\", \"value\": \"A\"}, {\"id\": \"1\", \"value\": [\"5555\"]}]}',0),(3,7,'[{\"id\": 0, \"value\": \"[{\\\"id\\\":\\\"0\\\",\\\"value\\\":\\\"A\\\"},{\\\"id\\\":\\\"1\\\",\\\"value\\\":[\\\"5555\\\"]}]\"}]',0),(4,7,'[{\"id\": 0, \"value\": \"[{\\\"id\\\":\\\"0\\\",\\\"value\\\":\\\"A\\\"},{\\\"id\\\":\\\"1\\\",\\\"value\\\":[\\\"5555\\\"]}]\"}]',0),(5,7,'[{\"id\": 0, \"value\": \"[{\\\"id\\\":\\\"0\\\",\\\"value\\\":\\\"A\\\"},{\\\"id\\\":\\\"1\\\",\\\"value\\\":[\\\"5555\\\"]}]\"}]',0),(6,7,'[{\"id\": 0, \"value\": \"[{\\\"id\\\":\\\"0\\\",\\\"value\\\":\\\"A\\\"},{\\\"id\\\":\\\"1\\\",\\\"value\\\":[\\\"5555\\\"]}]\"}]',0),(7,7,'[{\"id\": 0, \"value\": \"A\"}, {\"id\": 1, \"value\": \"[\\\"5555\\\"]\"}]',0);
/*!40000 ALTER TABLE `answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `companies`
--

DROP TABLE IF EXISTS `companies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `companies` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `invite` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `companies`
--

LOCK TABLES `companies` WRITE;
/*!40000 ALTER TABLE `companies` DISABLE KEYS */;
INSERT INTO `companies` VALUES (1,'tencent','123456');
/*!40000 ALTER TABLE `companies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cusers`
--

DROP TABLE IF EXISTS `cusers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cusers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` int DEFAULT NULL,
  `cid` int DEFAULT NULL,
  `rid` int DEFAULT NULL,
  `did` int DEFAULT NULL,
  `level` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cusers_users_id_fk` (`uid`),
  KEY `cusers_companies_id_fk` (`cid`),
  CONSTRAINT `cusers_companies_id_fk` FOREIGN KEY (`cid`) REFERENCES `companies` (`id`),
  CONSTRAINT `cusers_users_id_fk` FOREIGN KEY (`uid`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cusers`
--

LOCK TABLES `cusers` WRITE;
/*!40000 ALTER TABLE `cusers` DISABLE KEYS */;
INSERT INTO `cusers` VALUES (1,8,1,1,1,1);
/*!40000 ALTER TABLE `cusers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sheets`
--

DROP TABLE IF EXISTS `sheets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sheets` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tid` int DEFAULT NULL,
  `uid` int DEFAULT NULL,
  `name` varchar(45) DEFAULT 'anonymous',
  `description` varchar(45) DEFAULT 'no description',
  `start` datetime DEFAULT NULL,
  `end` datetime DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `type` int DEFAULT NULL,
  `length` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sheets_templates_id_fk` (`tid`),
  CONSTRAINT `sheets_templates_id_fk` FOREIGN KEY (`tid`) REFERENCES `templates` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sheets`
--

LOCK TABLES `sheets` WRITE;
/*!40000 ALTER TABLE `sheets` DISABLE KEYS */;
INSERT INTO `sheets` VALUES (1,NULL,NULL,'test','no description',NULL,NULL,NULL,NULL,NULL,NULL),(2,NULL,NULL,'test','no description',NULL,NULL,NULL,NULL,NULL,NULL),(3,NULL,NULL,'test','no description',NULL,NULL,NULL,NULL,NULL,NULL),(4,NULL,NULL,'test','no description',NULL,NULL,NULL,NULL,NULL,NULL),(5,NULL,NULL,'test','no description',NULL,NULL,NULL,NULL,NULL,NULL),(6,NULL,NULL,'test','no description',NULL,NULL,NULL,NULL,NULL,NULL),(7,4,7,'test','sample sheet','2022-05-15 08:00:00','2022-05-18 08:00:00',NULL,'0a8145e68f9e408cb0b384473f87620a',1,6);
/*!40000 ALTER TABLE `sheets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `templates`
--

DROP TABLE IF EXISTS `templates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `templates` (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` int DEFAULT NULL,
  `description` varchar(45) DEFAULT 'No Description',
  `name` varchar(45) NOT NULL,
  `time` date NOT NULL,
  `data` json NOT NULL,
  `length` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 COMMENT='store users'' templates';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `templates`
--

LOCK TABLES `templates` WRITE;
/*!40000 ALTER TABLE `templates` DISABLE KEYS */;
INSERT INTO `templates` VALUES (4,1,'No Description','1234','2022-05-11','{\"uid\": 1, \"name\": \"1234\", \"type\": 0, \"content\": [{\"id\": \"0\", \"type\": \"input\"}, {\"id\": \"1\", \"type\": \"sigle-check\", \"value\": {\"111\": 0, \"222\": 0}}, {\"id\": \"2\", \"type\": \"date-selector\"}, {\"id\": \"3\", \"type\": \"multi-check\", \"value\": {\"111\": 0, \"222\": 0}}, {\"id\": \"4\", \"type\": \"pull-selector\", \"value\": {\"1111\": 0, \"2222\": 0}}, {\"id\": \"5\", \"type\": \"num-input\"}], \"contentLength\": 6}',6),(5,1,'No Description','ppp','2022-05-14','{\"uid\": 1, \"name\": \"ppp\", \"type\": 0, \"content\": [{\"id\": \"0\", \"type\": \"input\"}, {\"id\": \"1\", \"type\": \"sigle-check\", \"value\": {\"123\": 0, \"234\": 0}}], \"contentLength\": 2}',2),(6,7,'No Description','111','2022-05-14','{\"uid\": 1, \"name\": \"111\", \"type\": 1, \"content\": [{\"id\": \"0\", \"type\": \"input\"}, {\"id\": \"1\", \"type\": \"sigle-check\", \"value\": {\"56565\": 0}}], \"contentLength\": 2}',2),(7,7,'No Description','uuu','2022-05-14','{\"uid\": 1, \"name\": \"uuu\", \"type\": 0, \"content\": [{\"id\": \"0\", \"type\": \"input\"}], \"contentLength\": 1}',1);
/*!40000 ALTER TABLE `templates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `pass` varchar(45) NOT NULL,
  `templates` json DEFAULT NULL,
  `sheets` json DEFAULT NULL,
  `type` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `users_cusers_id_fk` (`type`),
  CONSTRAINT `users_cusers_id_fk` FOREIGN KEY (`type`) REFERENCES `cusers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 COMMENT='store normal users'' and admins'' information';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'pangxiaobo','123',NULL,NULL,NULL),(2,'123','123',NULL,NULL,NULL),(3,'1234','1234',NULL,NULL,NULL),(4,'12345','12345',NULL,NULL,NULL),(5,'666','666',NULL,NULL,NULL),(6,'admin','1234',NULL,NULL,NULL),(7,'pxb','pxb',NULL,NULL,NULL),(8,'company_user','123',NULL,NULL,1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'ruangong'
--

--
-- Dumping routines for database 'ruangong'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-16 17:30:22
