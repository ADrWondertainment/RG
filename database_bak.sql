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
  `fid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `alldept_companies_id_fk` (`cid`),
  CONSTRAINT `alldept_companies_id_fk` FOREIGN KEY (`cid`) REFERENCES `companies` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alldept`
--

LOCK TABLES `alldept` WRITE;
/*!40000 ALTER TABLE `alldept` DISABLE KEYS */;
INSERT INTO `alldept` VALUES (1,2,0,'444',0),(4,2,NULL,'123',0),(5,2,NULL,'12',0),(6,2,NULL,'12',0),(7,2,NULL,'12',0),(8,2,NULL,'124',0),(9,2,NULL,'124',0),(10,2,NULL,'234',0),(11,2,NULL,'3443',0);
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
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `allrole_companies_id_fk` (`cid`),
  CONSTRAINT `allrole_companies_id_fk` FOREIGN KEY (`cid`) REFERENCES `companies` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allrole`
--

LOCK TABLES `allrole` WRITE;
/*!40000 ALTER TABLE `allrole` DISABLE KEYS */;
INSERT INTO `allrole` VALUES (1,1,'经理');
/*!40000 ALTER TABLE `allrole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `answer_view`
--

DROP TABLE IF EXISTS `answer_view`;
/*!50001 DROP VIEW IF EXISTS `answer_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `answer_view` AS SELECT 
 1 AS `id`,
 1 AS `sid`,
 1 AS `data`,
 1 AS `done`,
 1 AS `pass`,
 1 AS `uid`,
 1 AS `flow`*/;
SET character_set_client = @saved_cs_client;

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
  `pass` int DEFAULT '0',
  `uid` int DEFAULT NULL,
  `flow` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `answers_sheets_id_fk` (`sid`),
  KEY `answers_users_id_fk` (`uid`),
  CONSTRAINT `answers_sheets_id_fk` FOREIGN KEY (`sid`) REFERENCES `sheets` (`id`),
  CONSTRAINT `answers_users_id_fk` FOREIGN KEY (`uid`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answers`
--

LOCK TABLES `answers` WRITE;
/*!40000 ALTER TABLE `answers` DISABLE KEYS */;
INSERT INTO `answers` VALUES (17,18,'[{\"id\": 0, \"value\": [\"测试\"]}]',1,0,7,'1'),(22,19,'[{\"id\": 0, \"value\": [\"答案1\"]}, {\"id\": 1, \"value\": [\"选项1\"]}, {\"id\": 2, \"value\": [\"选项1\"]}, {\"id\": 3, \"value\": [\"2022.05.28\"]}, {\"id\": 4, \"value\": [\"表项1\"]}, {\"id\": 5, \"value\": [\"2\"]}]',1,0,10,'1'),(23,20,'[{\"id\": 0, \"value\": [\"答案2\"]}, {\"id\": 1, \"value\": [\"选项2\"]}, {\"id\": 2, \"value\": []}, {\"id\": 3}, {\"id\": 4, \"value\": [\"表项2\"]}, {\"id\": 5, \"value\": [\"0\"]}]',0,0,10,'1'),(24,19,'[{\"id\": 0, \"value\": [\"pxb的答案\"]}, {\"id\": 1, \"value\": [\"选项2\"]}, {\"id\": 2, \"value\": [\"选项1\", \"选项2\"]}, {\"id\": 3, \"value\": [\"2022.05.11\"]}, {\"id\": 4, \"value\": [\"表项2\"]}, {\"id\": 5, \"value\": [\"3\"]}]',1,0,7,'1'),(25,19,'[{\"id\": 0, \"value\": [\"pxb的答案\"]}, {\"id\": 1, \"value\": [\"选项2\"]}, {\"id\": 2, \"value\": [\"选项1\", \"选项2\"]}, {\"id\": 3, \"value\": [\"2022.05.11\"]}, {\"id\": 4, \"value\": [\"表项2\"]}, {\"id\": 5, \"value\": [\"3\"]}]',1,1,15,'[2,3]');
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
  `cgroups` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `companies`
--

LOCK TABLES `companies` WRITE;
/*!40000 ALTER TABLE `companies` DISABLE KEYS */;
INSERT INTO `companies` VALUES (1,'tencent','123456',NULL),(2,'test_company','123','[2,3]');
/*!40000 ALTER TABLE `companies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `cuser_view`
--

DROP TABLE IF EXISTS `cuser_view`;
/*!50001 DROP VIEW IF EXISTS `cuser_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `cuser_view` AS SELECT 
 1 AS `id`,
 1 AS `uid`,
 1 AS `cid`,
 1 AS `level`,
 1 AS `group_id`,
 1 AS `email`,
 1 AS `role`,
 1 AS `dept`*/;
SET character_set_client = @saved_cs_client;

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
  `cgroup` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `cusers_users_id_fk` (`uid`),
  KEY `cusers_companies_id_fk` (`cid`),
  CONSTRAINT `cusers_companies_id_fk` FOREIGN KEY (`cid`) REFERENCES `companies` (`id`),
  CONSTRAINT `cusers_users_id_fk` FOREIGN KEY (`uid`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cusers`
--

LOCK TABLES `cusers` WRITE;
/*!40000 ALTER TABLE `cusers` DISABLE KEYS */;
INSERT INTO `cusers` VALUES (1,8,2,1,1,1,1),(2,11,2,1,1,1,2),(3,12,2,1,1,1,2),(4,13,2,1,1,1,3),(5,14,2,1,1,1,3),(6,15,2,1,1,1,0),(7,16,2,0,0,0,0);
/*!40000 ALTER TABLE `cusers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_details`
--

DROP TABLE IF EXISTS `group_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `level` int DEFAULT NULL,
  `member` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_details`
--

LOCK TABLES `group_details` WRITE;
/*!40000 ALTER TABLE `group_details` DISABLE KEYS */;
INSERT INTO `group_details` VALUES (2,2,'[1]'),(3,2,'[2,3]'),(4,2,'[4,5]');
/*!40000 ALTER TABLE `group_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `group_view`
--

DROP TABLE IF EXISTS `group_view`;
/*!50001 DROP VIEW IF EXISTS `group_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `group_view` AS SELECT 
 1 AS `id`,
 1 AS `cid`,
 1 AS `level`,
 1 AS `member`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `groups`
--

DROP TABLE IF EXISTS `groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `groups` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cid` int DEFAULT NULL,
  `gid` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups`
--

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
INSERT INTO `groups` VALUES (1,1,2),(2,2,3),(3,2,4);
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;
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
  `cid` int DEFAULT NULL,
  `did` int DEFAULT NULL,
  `name` varchar(45) DEFAULT 'anonymous',
  `description` varchar(45) DEFAULT 'no description',
  `start` datetime DEFAULT NULL,
  `end` datetime DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `type` int DEFAULT NULL,
  `length` int DEFAULT NULL,
  `order` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sheets_templates_id_fk` (`tid`),
  CONSTRAINT `sheets_templates_id_fk` FOREIGN KEY (`tid`) REFERENCES `templates` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sheets`
--

LOCK TABLES `sheets` WRITE;
/*!40000 ALTER TABLE `sheets` DISABLE KEYS */;
INSERT INTO `sheets` VALUES (1,NULL,NULL,NULL,NULL,'test','no description',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,NULL,NULL,NULL,NULL,'test','no description',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,NULL,NULL,NULL,NULL,'test','no description',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(4,NULL,NULL,NULL,NULL,'test','no description',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(5,NULL,NULL,NULL,NULL,'test','no description',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(6,NULL,NULL,NULL,NULL,'test','no description',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(7,4,7,0,0,'test','sample sheet','2022-05-15 08:00:00','2022-05-18 08:00:00',NULL,'0a8145e68f9e408cb0b384473f87620a',0,6,NULL),(16,20,9,NULL,0,'812喜欢的水果','寝室成员喜欢水果收集','2022-05-25 00:00:00','2022-05-31 00:00:00','http://localhost:9090/#/justFillForm/16','d788955c3ff14eaeae370e4c8d4c9ad8',0,2,NULL),(17,21,9,NULL,0,'请假','无','2022-05-25 00:00:00','2022-05-31 00:00:00','http://localhost:9090/#/justFillForm/17','15d6be3a47704faba3acab0118140f65',1,3,NULL),(18,22,9,NULL,0,'审批表','审批测试实例','2022-05-25 00:00:00','2022-05-28 00:00:00','http://localhost:9090/#/justFillForm/18','184b148346f84d03911d818f30171565',1,1,'[1]'),(19,23,10,NULL,0,'统计表单1','统计表单1','2022-05-28 22:27:03','2022-05-31 00:00:00','http://localhost:9090/#/justFillForm/19','0aa040be4bd24ac8a73598c6d87102bd',0,6,NULL),(20,23,10,NULL,0,'统计表单2','统计表单2','2022-05-28 00:00:00','2022-06-04 00:00:00','http://localhost:9090/#/justFillForm/20','7bce362e45ba456782ecc6d59ac26bb3',0,6,NULL),(21,24,10,NULL,0,'审批表单1','审批表单1','2022-05-28 00:00:00','2022-05-31 00:00:00','http://localhost:9090/#/justFillForm/21','4cc0eb5e66ba496aa4371845dfec2905',1,3,NULL),(22,24,10,NULL,0,'审批表单2','审批表单2','2022-05-28 22:30:34','2022-06-04 00:00:00','http://localhost:9090/#/justFillForm/22','e73919a023d64f0287476ec67ca000e8',1,3,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3 COMMENT='store users'' templates';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `templates`
--

LOCK TABLES `templates` WRITE;
/*!40000 ALTER TABLE `templates` DISABLE KEYS */;
INSERT INTO `templates` VALUES (4,1,'No Description','1234','2022-05-11','{\"uid\": 1, \"name\": \"1234\", \"type\": 0, \"content\": [{\"id\": \"0\", \"type\": \"input\"}, {\"id\": \"1\", \"type\": \"sigle-check\", \"value\": {\"111\": 0, \"222\": 0}}, {\"id\": \"2\", \"type\": \"date-selector\"}, {\"id\": \"3\", \"type\": \"multi-check\", \"value\": {\"111\": 0, \"222\": 0}}, {\"id\": \"4\", \"type\": \"pull-selector\", \"value\": {\"1111\": 0, \"2222\": 0}}, {\"id\": \"5\", \"type\": \"num-input\"}], \"contentLength\": 6}',6),(5,1,'No Description','ppp','2022-05-14','{\"uid\": 1, \"name\": \"ppp\", \"type\": 0, \"content\": [{\"id\": \"0\", \"type\": \"input\"}, {\"id\": \"1\", \"type\": \"sigle-check\", \"value\": {\"123\": 0, \"234\": 0}}], \"contentLength\": 2}',2),(6,7,'No Description','111','2022-05-14','{\"uid\": 1, \"name\": \"111\", \"type\": 1, \"content\": [{\"id\": \"0\", \"type\": \"input\"}, {\"id\": \"1\", \"type\": \"sigle-check\", \"value\": {\"56565\": 0}}], \"contentLength\": 2}',2),(7,7,'No Description','uuu','2022-05-14','{\"uid\": 1, \"name\": \"uuu\", \"type\": 0, \"content\": [{\"id\": \"0\", \"type\": \"input\"}], \"contentLength\": 1}',1),(9,7,'No Description','123123','2022-05-17','{\"uid\": 7, \"name\": \"123123\", \"type\": 0, \"content\": [{\"id\": \"0\", \"type\": \"sigle-check\", \"value\": {\"345345345 \": 0}}], \"contentLength\": 1}',1),(10,7,'暂时没有','123','2022-05-17','{\"uid\": 7, \"name\": \"123\", \"type\": \"0\", \"content\": [{\"id\": \"0\", \"type\": \"single-check\", \"value\": {\"123\": 0}, \"description\": \"123\"}], \"endTime\": \"2022-05-06T16:00:00.000Z\", \"startTime\": \"2022-04-30T16:00:00.000Z\", \"description\": \"暂时没有\", \"contentLength\": 1, \"originContent\": \"[{\\\"type\\\":\\\"single-check\\\",\\\"isRequired\\\":false,\\\"description\\\":\\\"123\\\",\\\"value\\\":[\\\"123\\\"]}]\"}',1),(11,7,'暂时没有','test1','2022-05-17','{\"uid\": 7, \"name\": \"test1\", \"type\": \"0\", \"content\": [{\"id\": \"0\", \"type\": \"input\", \"value\": {}, \"description\": \"test\"}], \"endTime\": \"2022-05-26T16:00:00.000Z\", \"startTime\": \"2022-05-02T16:00:00.000Z\", \"description\": \"暂时没有\", \"contentLength\": 1, \"originContent\": \"[{\\\"type\\\":\\\"input\\\",\\\"isRequired\\\":false,\\\"description\\\":\\\"test\\\",\\\"value\\\":[]}]\"}',1),(20,9,'你喜欢的水果','水果','2022-05-25','{\"uid\": 9, \"name\": \"水果\", \"type\": \"0\", \"content\": [{\"id\": \"0\", \"type\": \"multi-check\", \"value\": {\"梨子\": 0, \"苹果\": 0, \"草莓\": 0, \"香蕉\": 0}, \"description\": \"水果们\"}, {\"id\": \"1\", \"type\": \"input\", \"value\": {}, \"description\": \"其他的水果\"}], \"endTime\": \"2022-05-30T16:00:00.000Z\", \"startTime\": \"2022-05-24T16:00:00.000Z\", \"description\": \"你喜欢的水果\", \"contentLength\": 2, \"originContent\": \"[{\\\"type\\\":\\\"multi-check\\\",\\\"isRequired\\\":false,\\\"description\\\":\\\"水果们\\\",\\\"value\\\":[\\\"苹果\\\",\\\"梨子\\\",\\\"香蕉\\\",\\\"草莓\\\"]},{\\\"type\\\":\\\"input\\\",\\\"isRequired\\\":false,\\\"description\\\":\\\"其他的水果\\\",\\\"value\\\":[]}]\"}',2),(21,9,'请假示例','员工请假','2022-05-25','{\"uid\": 9, \"name\": \"员工请假\", \"type\": \"1\", \"content\": [{\"id\": \"0\", \"type\": \"input\", \"value\": {}, \"description\": \"请假原因\"}, {\"id\": \"1\", \"type\": \"date-selector\", \"value\": {}, \"description\": \"请假起始时间\"}, {\"id\": \"2\", \"type\": \"date-selector\", \"value\": {}, \"description\": \"请假结束时间\"}], \"endTime\": \"\", \"startTime\": \"\", \"description\": \"请假示例\", \"contentLength\": 3, \"originContent\": \"[{\\\"type\\\":\\\"input\\\",\\\"isRequired\\\":true,\\\"description\\\":\\\"请假原因\\\",\\\"value\\\":[]},{\\\"type\\\":\\\"date-selector\\\",\\\"isRequired\\\":true,\\\"description\\\":\\\"请假起始时间\\\",\\\"value\\\":[]},{\\\"type\\\":\\\"date-selector\\\",\\\"isRequired\\\":true,\\\"description\\\":\\\"请假结束时间\\\",\\\"value\\\":[]}]\"}',3),(22,9,'审批表测试','测试','2022-05-25','{\"uid\": 9, \"name\": \"测试\", \"type\": \"1\", \"content\": [{\"id\": \"0\", \"type\": \"input\", \"value\": {}, \"description\": \"审批条目1\"}], \"endTime\": \"\", \"startTime\": \"\", \"description\": \"审批表测试\", \"contentLength\": 1, \"originContent\": \"[{\\\"type\\\":\\\"input\\\",\\\"isRequired\\\":true,\\\"description\\\":\\\"审批条目1\\\",\\\"value\\\":[]}]\"}',1),(23,10,'统计表模板1','统计表模板1','2022-05-28','{\"uid\": 10, \"name\": \"统计表模板1\", \"type\": \"0\", \"content\": [{\"id\": \"0\", \"type\": \"input\", \"value\": {}, \"description\": \"这是输入框\"}, {\"id\": \"1\", \"type\": \"single-check\", \"value\": {\"选项1\": 0, \"选项2\": 0}, \"description\": \"这是单选题\"}, {\"id\": \"2\", \"type\": \"multi-check\", \"value\": {\"选项1\": 0, \"选项2\": 0}, \"description\": \"这是多选题\"}, {\"id\": \"3\", \"type\": \"date-selector\", \"value\": {}, \"description\": \"这是日期选择\"}, {\"id\": \"4\", \"type\": \"pull-selector\", \"value\": {\"表项1\": 0, \"表项2\": 0}, \"description\": \"这是下拉列表\"}, {\"id\": \"5\", \"type\": \"num-input\", \"value\": {}, \"description\": \"这是数字输入框\"}], \"endTime\": \"2022-05-30T16:00:00.000Z\", \"startTime\": \"2022-05-27T16:00:00.000Z\", \"description\": \"统计表模板1\", \"contentLength\": 6, \"originContent\": \"[{\\\"type\\\":\\\"input\\\",\\\"isRequired\\\":false,\\\"description\\\":\\\"这是输入框\\\",\\\"value\\\":[]},{\\\"type\\\":\\\"single-check\\\",\\\"isRequired\\\":false,\\\"description\\\":\\\"这是单选题\\\",\\\"value\\\":[\\\"选项1\\\",\\\"选项2\\\"]},{\\\"type\\\":\\\"multi-check\\\",\\\"isRequired\\\":false,\\\"description\\\":\\\"这是多选题\\\",\\\"value\\\":[\\\"选项1\\\",\\\"选项2\\\"]},{\\\"type\\\":\\\"date-selector\\\",\\\"isRequired\\\":false,\\\"description\\\":\\\"这是日期选择\\\",\\\"value\\\":[]},{\\\"type\\\":\\\"pull-selector\\\",\\\"isRequired\\\":false,\\\"description\\\":\\\"这是下拉列表\\\",\\\"value\\\":[\\\"表项1\\\",\\\"表项2\\\"]},{\\\"type\\\":\\\"num-input\\\",\\\"isRequired\\\":false,\\\"description\\\":\\\"这是数字输入框\\\",\\\"value\\\":[]}]\"}',6),(24,10,'审批表模板1','审批表模板1','2022-05-28','{\"uid\": 10, \"name\": \"审批表模板1\", \"type\": \"1\", \"content\": [{\"id\": \"0\", \"type\": \"single-check\", \"value\": {\"A\": 0, \"B\": 0}, \"description\": \"单选题\"}, {\"id\": \"1\", \"type\": \"input\", \"value\": {}, \"description\": \"输入框\"}, {\"id\": \"2\", \"type\": \"multi-check\", \"value\": {\"A\": 0, \"B\": 0}, \"description\": \"多选题\"}], \"endTime\": \"2022-05-30T16:00:00.000Z\", \"startTime\": \"2022-05-27T16:00:00.000Z\", \"description\": \"审批表模板1\", \"contentLength\": 3, \"originContent\": \"[{\\\"type\\\":\\\"single-check\\\",\\\"isRequired\\\":false,\\\"description\\\":\\\"单选题\\\",\\\"value\\\":[\\\"A\\\",\\\"B\\\"]},{\\\"type\\\":\\\"input\\\",\\\"isRequired\\\":false,\\\"description\\\":\\\"输入框\\\",\\\"value\\\":[]},{\\\"type\\\":\\\"multi-check\\\",\\\"isRequired\\\":false,\\\"description\\\":\\\"多选题\\\",\\\"value\\\":[\\\"A\\\",\\\"B\\\"]}]\"}',3);
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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3 COMMENT='store normal users'' and admins'' information';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'pangxiaobo','123',NULL,NULL,NULL),(2,'123','123',NULL,NULL,NULL),(3,'1234','1234',NULL,NULL,NULL),(4,'12345','12345',NULL,NULL,NULL),(5,'666','666',NULL,NULL,NULL),(6,'admin','1234',NULL,NULL,NULL),(7,'pxb','pxb',NULL,'[\"17\", \"17\", \"17\", \"17\", \"24\", \"24\"]',NULL),(8,'company_user','123',NULL,NULL,1),(9,'test','test',NULL,'[\"16\"]',NULL),(10,'test2','test2',NULL,'[\"22\", \"22\", \"23\"]',NULL),(11,'cuser1','test',NULL,NULL,2),(12,'cuser2','test',NULL,NULL,3),(13,'cuser3','test',NULL,NULL,4),(14,'cuser4','test',NULL,NULL,5),(15,'cuser5','test',NULL,NULL,6),(16,'test_company_root','test',NULL,NULL,7);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'ruangong'
--

--
-- Dumping routines for database 'ruangong'
--

--
-- Final view structure for view `answer_view`
--

/*!50001 DROP VIEW IF EXISTS `answer_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `answer_view` AS select `a`.`id` AS `id`,`a`.`sid` AS `sid`,`a`.`data` AS `data`,`a`.`done` AS `done`,`a`.`pass` AS `pass`,`a`.`uid` AS `uid`,`s`.`order` AS `flow` from (`answers` `a` join `sheets` `s` on((`s`.`id` = `a`.`sid`))) where (`s`.`type` = 1) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `cuser_view`
--

/*!50001 DROP VIEW IF EXISTS `cuser_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `cuser_view` AS select `cusers`.`id` AS `id`,`cusers`.`uid` AS `uid`,`cusers`.`cid` AS `cid`,`cusers`.`level` AS `level`,`cusers`.`cgroup` AS `group_id`,`u`.`email` AS `email`,`r`.`name` AS `role`,`d`.`name` AS `dept` from (((`cusers` join `users` `u` on(((`u`.`id` = `cusers`.`uid`) and (`cusers`.`id` = `u`.`type`)))) join `allrole` `r` on((`r`.`id` = `cusers`.`rid`))) join `alldept` `d` on((`d`.`id` = `cusers`.`did`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `group_view`
--

/*!50001 DROP VIEW IF EXISTS `group_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `group_view` AS select `g`.`id` AS `id`,`g`.`cid` AS `cid`,`gd`.`level` AS `level`,`gd`.`member` AS `member` from (`groups` `g` join `group_details` `gd` on((`g`.`gid` = `gd`.`id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-19 18:52:21
