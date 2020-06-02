-- MySQL dump 10.16  Distrib 10.1.44-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: exam_portal
-- ------------------------------------------------------
-- Server version	10.1.44-MariaDB-0ubuntu0.18.04.1

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
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ans` varchar(255) DEFAULT NULL,
  `answer_is_given` bit(1) DEFAULT NULL,
  `exam_attendant_date` datetime DEFAULT NULL,
  `exam_id` bigint(20) DEFAULT NULL,
  `que_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtrb94m44ljfvmhy6yjc8gafq3` (`exam_id`),
  KEY `FK5bigakia6ukl2cww9mba9x5mn` (`que_id`),
  CONSTRAINT `FK5bigakia6ukl2cww9mba9x5mn` FOREIGN KEY (`que_id`) REFERENCES `question` (`id`),
  CONSTRAINT `FKtrb94m44ljfvmhy6yjc8gafq3` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` VALUES (1,'','\0','2020-03-02 14:51:27',1,1),(2,'','\0','2020-03-02 14:52:28',1,2),(3,'','\0','2020-03-02 14:53:29',1,4),(4,'','\0','2020-03-02 14:54:30',1,5);
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exam`
--

DROP TABLE IF EXISTS `exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exam` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKo99y1c1rj2adr54ca8ldt7cho` (`created_by`),
  CONSTRAINT `FKo99y1c1rj2adr54ca8ldt7cho` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exam`
--

LOCK TABLES `exam` WRITE;
/*!40000 ALTER TABLE `exam` DISABLE KEYS */;
INSERT INTO `exam` VALUES (1,'2020-02-28 14:52:00','Exam 001',2);
/*!40000 ALTER TABLE `exam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exam_assign`
--

DROP TABLE IF EXISTS `exam_assign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exam_assign` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_of_assign` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `assign_by` bigint(20) DEFAULT NULL,
  `assign_to` bigint(20) DEFAULT NULL,
  `exam_id` bigint(20) DEFAULT NULL,
  `result_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKiag3dx22dxsk92h1dgsvctlok` (`assign_by`),
  KEY `FKx6jyno9rl3cp7jqlry8bijcc` (`assign_to`),
  KEY `FKt6c7i7cgspl8qvstirt6k9vjy` (`exam_id`),
  KEY `FKqwfpb52sj1hgk7ld7grt6dtj2` (`result_id`),
  CONSTRAINT `FKiag3dx22dxsk92h1dgsvctlok` FOREIGN KEY (`assign_by`) REFERENCES `user` (`id`),
  CONSTRAINT `FKqwfpb52sj1hgk7ld7grt6dtj2` FOREIGN KEY (`result_id`) REFERENCES `result` (`id`),
  CONSTRAINT `FKt6c7i7cgspl8qvstirt6k9vjy` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`),
  CONSTRAINT `FKx6jyno9rl3cp7jqlry8bijcc` FOREIGN KEY (`assign_to`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exam_assign`
--

LOCK TABLES `exam_assign` WRITE;
/*!40000 ALTER TABLE `exam_assign` DISABLE KEYS */;
INSERT INTO `exam_assign` VALUES (1,'2020-03-02 14:49:57',NULL,2,1,1,NULL);
/*!40000 ALTER TABLE `exam_assign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ans_category` varchar(255) DEFAULT NULL,
  `correct_ans` varchar(255) DEFAULT NULL,
  `option1` varchar(255) DEFAULT NULL,
  `option1_is_ans` bit(1) DEFAULT NULL,
  `option2` varchar(255) DEFAULT NULL,
  `option2_is_ans` bit(1) DEFAULT NULL,
  `option3` varchar(255) DEFAULT NULL,
  `option3_is_ans` bit(1) DEFAULT NULL,
  `option4` varchar(255) DEFAULT NULL,
  `option4_is_ans` bit(1) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `time_per_question` bigint(20) DEFAULT NULL,
  `exam_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhupso6ldavcx993tfnrjsdl1p` (`exam_id`),
  CONSTRAINT `FKhupso6ldavcx993tfnrjsdl1p` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,'Single select','','abc','','xyz','\0','mno','\0','pqr','\0','What is name of Indian PM?',1,1),(2,'Multi select',NULL,'Football','','Dancing','','Singing','\0','Cokking','\0','Your Hobby?',1,1),(4,'Multi select','','Actor','','Singer','','Rapper','\0','Comedian','\0','Who id Daniel Radcliffe',1,1),(5,'Single select',NULL,'Actor','','Singer','\0','Song writer','\0','None of this','\0','Who is Emma Watson?',1,1),(10,'Text ans','He is Singer','',NULL,'',NULL,'',NULL,'',NULL,'Who is Nick Jonas?',70,1);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `result`
--

DROP TABLE IF EXISTS `result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cut_off` bigint(20) DEFAULT NULL,
  `out_off` bigint(20) DEFAULT NULL,
  `result` varchar(255) DEFAULT NULL,
  `total_correct_answer` double DEFAULT NULL,
  `exam_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7sc8ejc10fh429vn3gfpkhem9` (`exam_id`),
  KEY `FKpjjrrf0483ih2cvyfmx70a16b` (`user_id`),
  CONSTRAINT `FK7sc8ejc10fh429vn3gfpkhem9` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`),
  CONSTRAINT `FKpjjrrf0483ih2cvyfmx70a16b` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `result`
--

LOCK TABLES `result` WRITE;
/*!40000 ALTER TABLE `result` DISABLE KEYS */;
/*!40000 ALTER TABLE `result` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dob` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `reset_token` varchar(255) DEFAULT NULL,
  `role` varchar(60) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `profile_image_url` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'1996-10-31','pritesh.d.java@gmail.com','Pritesh2','Male','Dudhatra1','$2a$10$aElo6iHPw2xYXq.tCD.nQumq8mNe50xGgfRrir4CCQsOmutzgHXtS',NULL,'Admin','Krupa','/downloadProfileImage/userService/1591080725167.jpeg'),(2,'1996-10-31','krupajadeja56@gmail.com','Krupaba','Female','Jadeja','$2a$10$n/vopMyg2F91COpDcM9uDeYT.yMFJNeQjjid4b9rfZqpyqDNJN6ya','','Admin','Krupaba',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-02 12:40:13
