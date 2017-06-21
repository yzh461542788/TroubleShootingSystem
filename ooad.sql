-- MySQL dump 10.13  Distrib 5.7.15, for osx10.11 (x86_64)
--
-- Host: localhost    Database: ooad
-- ------------------------------------------------------
-- Server version	5.7.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `check_item`
--

DROP TABLE IF EXISTS `check_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `check_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(63) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `checkitem_title_uindex` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=1641 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `check_item`
--

LOCK TABLES `check_item` WRITE;
/*!40000 ALTER TABLE `check_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `check_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `check_item_process`
--

DROP TABLE IF EXISTS `check_item_process`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `check_item_process` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_process_id` int(11) DEFAULT NULL,
  `check_item_id` int(11) DEFAULT NULL,
  `item_state` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `checkitem_process_checkitem_id_fk` (`check_item_id`),
  KEY `checkitem_process_task_process_id_fk` (`task_process_id`),
  CONSTRAINT `checkitem_process_checkitem_id_fk` FOREIGN KEY (`check_item_id`) REFERENCES `check_item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `checkitem_process_task_process_id_fk` FOREIGN KEY (`task_process_id`) REFERENCES `task_process` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=483 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `check_item_process`
--

LOCK TABLES `check_item_process` WRITE;
/*!40000 ALTER TABLE `check_item_process` DISABLE KEYS */;
/*!40000 ALTER TABLE `check_item_process` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `check_task`
--

DROP TABLE IF EXISTS `check_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `check_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(63) DEFAULT NULL,
  `template_id` int(11) DEFAULT NULL,
  `post_date` datetime DEFAULT NULL,
  `deadline` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `check_task_title_uindex` (`title`),
  KEY `check_task_template_id_fk` (`template_id`),
  CONSTRAINT `check_task_template_id_fk` FOREIGN KEY (`template_id`) REFERENCES `template` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=297 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `check_task`
--

LOCK TABLES `check_task` WRITE;
/*!40000 ALTER TABLE `check_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `check_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `check_task_check_item`
--

DROP TABLE IF EXISTS `check_task_check_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `check_task_check_item` (
  `check_task_id` int(11) NOT NULL,
  `check_item_id` int(11) NOT NULL,
  PRIMARY KEY (`check_task_id`,`check_item_id`),
  KEY `check_task_check_item_check_item_id_fk` (`check_item_id`),
  CONSTRAINT `check_task_check_item_check_item_id_fk` FOREIGN KEY (`check_item_id`) REFERENCES `check_item` (`id`),
  CONSTRAINT `check_task_check_item_check_task_id_fk` FOREIGN KEY (`check_task_id`) REFERENCES `check_task` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `check_task_check_item`
--

LOCK TABLES `check_task_check_item` WRITE;
/*!40000 ALTER TABLE `check_task_check_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `check_task_check_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(63) DEFAULT NULL,
  `company_code` varchar(63) DEFAULT NULL,
  `organization_code` varchar(63) DEFAULT NULL,
  `contact` varchar(63) DEFAULT NULL,
  `contact_phone_number` varchar(63) DEFAULT NULL,
  `business_group` varchar(63) DEFAULT NULL,
  `business_category` varchar(63) DEFAULT NULL,
  `business_scope` varchar(63) DEFAULT NULL,
  `company_state` varchar(63) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `company_company_code_uindex` (`company_code`),
  UNIQUE KEY `company_organization_code_uindex` (`organization_code`),
  UNIQUE KEY `company_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=962 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (862,'Wed Jun 21 10:02:43 CST 20172','Wed Jun 21 10:02:43 CST 20172','Wed Jun 21 10:02:43 CST 20172','Wed Jun 21 10:02:43 CST 20172','Wed Jun 21 10:02:43 CST 20172','Wed Jun 21 10:02:43 CST 20172','Wed Jun 21 10:02:43 CST 20172','Wed Jun 21 10:02:43 CST 20172','0');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_process`
--

DROP TABLE IF EXISTS `task_process`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_process` (
  `finish_time` datetime DEFAULT NULL,
  `task_process_state` varchar(20) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `check_task_id` int(11) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `task_process_check_task_id_company_id_uindex` (`check_task_id`,`company_id`),
  KEY `task_process_company_id_fk` (`company_id`),
  CONSTRAINT `task_process_check_task_id_fk` FOREIGN KEY (`check_task_id`) REFERENCES `check_task` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `task_process_company_id_fk` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_process`
--

LOCK TABLES `task_process` WRITE;
/*!40000 ALTER TABLE `task_process` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_process` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `template`
--

DROP TABLE IF EXISTS `template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(63) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `template_title_uindex` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=328 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `template`
--

LOCK TABLES `template` WRITE;
/*!40000 ALTER TABLE `template` DISABLE KEYS */;
/*!40000 ALTER TABLE `template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `template_check_item`
--

DROP TABLE IF EXISTS `template_check_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `template_check_item` (
  `check_item_id` int(11) NOT NULL,
  `template_id` int(11) NOT NULL,
  PRIMARY KEY (`check_item_id`,`template_id`),
  KEY `template_checkitem_template_id_fk` (`template_id`),
  CONSTRAINT `template_checkitem_checkitem_id_fk` FOREIGN KEY (`check_item_id`) REFERENCES `check_item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `template_checkitem_template_id_fk` FOREIGN KEY (`template_id`) REFERENCES `template` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `template_check_item`
--

LOCK TABLES `template_check_item` WRITE;
/*!40000 ALTER TABLE `template_check_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `template_check_item` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-21 10:10:23
