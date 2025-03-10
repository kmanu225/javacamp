-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: libraryDb
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `author`
--

DROP TABLE IF EXISTS `author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `author` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `AuthorName` varchar(50) DEFAULT NULL,
  `Birthdate` int DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
INSERT INTO `author` VALUES (1,'Fran├ºois d\'Assise Konan N\'Dah',1968),(2,'Cheikh Hamidou Kane',1928);
/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `Title` varchar(255) NOT NULL,
  `FirstYearEdition` int NOT NULL,
  `Description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Title`,`FirstYearEdition`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES ('L\'Aventure ambigu├½',1961,'D├®pays├®ment, Afrique, Europe'),('Le Retour de lÔÇÖenfant soldat',2008,'Vengeance, Enfants soldats');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookcopy`
--

DROP TABLE IF EXISTS `bookcopy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookcopy` (
  `CopyId` int NOT NULL,
  `BookTitle` varchar(255) DEFAULT NULL,
  `BookFirstYearEdition` int DEFAULT NULL,
  `EditorISBN` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`CopyId`),
  KEY `EditorISBN` (`EditorISBN`),
  KEY `BookTitle` (`BookTitle`,`BookFirstYearEdition`),
  CONSTRAINT `bookcopy_ibfk_1` FOREIGN KEY (`EditorISBN`) REFERENCES `editor` (`ISBN`),
  CONSTRAINT `bookcopy_ibfk_2` FOREIGN KEY (`BookTitle`, `BookFirstYearEdition`) REFERENCES `book` (`Title`, `FirstYearEdition`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookcopy`
--

LOCK TABLES `bookcopy` WRITE;
/*!40000 ALTER TABLE `bookcopy` DISABLE KEYS */;
INSERT INTO `bookcopy` VALUES (1,'Le Retour de lÔÇÖenfant soldat',2008,' 9782070365197'),(2,'Le Retour de lÔÇÖenfant soldat',2008,' 9782070365197'),(3,'Le Retour de lÔÇÖenfant soldat',2008,' 9782070365197'),(4,'L\'Aventure ambigu├½',1961,'9781422356883'),(5,'L\'Aventure ambigu├½',1961,'9781422356883'),(6,'L\'Aventure ambigu├½',1961,'9781422356883'),(7,'L\'Aventure ambigu├½',1961,'9782264023445'),(8,'L\'Aventure ambigu├½',1961,'9782264023445');
/*!40000 ALTER TABLE `bookcopy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrows`
--

DROP TABLE IF EXISTS `borrows`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `borrows` (
  `BookCopyId` int DEFAULT NULL,
  `BorrowerLogin` varchar(30) DEFAULT NULL,
  `BorrowingDate` date DEFAULT NULL,
  `GiveBackDate` date DEFAULT NULL,
  KEY `BookCopyId` (`BookCopyId`),
  KEY `BorrowerLogin` (`BorrowerLogin`),
  CONSTRAINT `borrows_ibfk_1` FOREIGN KEY (`BookCopyId`) REFERENCES `bookcopy` (`CopyId`),
  CONSTRAINT `borrows_ibfk_2` FOREIGN KEY (`BorrowerLogin`) REFERENCES `User` (`Login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrows`
--

LOCK TABLES `borrows` WRITE;
/*!40000 ALTER TABLE `borrows` DISABLE KEYS */;
/*!40000 ALTER TABLE `borrows` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `changecategory`
--

DROP TABLE IF EXISTS `changecategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `changecategory` (
  `UserLogin` varchar(30) DEFAULT NULL,
  `ManagerLogin` varchar(30) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  `NewCategory` char(1) DEFAULT NULL,
  KEY `UserLogin` (`UserLogin`),
  KEY `ManagerLogin` (`ManagerLogin`),
  CONSTRAINT `changecategory_ibfk_1` FOREIGN KEY (`UserLogin`) REFERENCES `User` (`Login`),
  CONSTRAINT `changecategory_ibfk_2` FOREIGN KEY (`ManagerLogin`) REFERENCES `User` (`Login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `changecategory`
--

LOCK TABLES `changecategory` WRITE;
/*!40000 ALTER TABLE `changecategory` DISABLE KEYS */;
INSERT INTO `changecategory` VALUES ('kelga','kmanu','2022-10-14','A');
/*!40000 ALTER TABLE `changecategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `editor`
--

DROP TABLE IF EXISTS `editor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `editor` (
  `ISBN` varchar(255) NOT NULL,
  `EditionName` varchar(30) DEFAULT NULL,
  `EditionYear` int DEFAULT NULL,
  `BookTitle` varchar(255) DEFAULT NULL,
  `BookFirstYearEdition` int DEFAULT NULL,
  PRIMARY KEY (`ISBN`),
  KEY `BookTitle` (`BookTitle`,`BookFirstYearEdition`),
  CONSTRAINT `editor_ibfk_1` FOREIGN KEY (`BookTitle`, `BookFirstYearEdition`) REFERENCES `book` (`Title`, `FirstYearEdition`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `editor`
--

LOCK TABLES `editor` WRITE;
/*!40000 ALTER TABLE `editor` DISABLE KEYS */;
INSERT INTO `editor` VALUES (' 9782070365197','Valesse',2008,'Le Retour de lÔÇÖenfant soldat',2008),('9781422356883','Julliard',1961,'L\'Aventure ambigu├½',1961),('9782264023445','10/18',1995,'L\'Aventure ambigu├½',1961);
/*!40000 ALTER TABLE `editor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `haswritten`
--

DROP TABLE IF EXISTS `haswritten`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `haswritten` (
  `AuthorId` int DEFAULT NULL,
  `BookTitle` varchar(255) DEFAULT NULL,
  `BookFirstYearEdition` int DEFAULT NULL,
  KEY `BookTitle` (`BookTitle`,`BookFirstYearEdition`),
  KEY `AuthorId` (`AuthorId`),
  CONSTRAINT `haswritten_ibfk_1` FOREIGN KEY (`BookTitle`, `BookFirstYearEdition`) REFERENCES `book` (`Title`, `FirstYearEdition`),
  CONSTRAINT `haswritten_ibfk_2` FOREIGN KEY (`AuthorId`) REFERENCES `author` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `haswritten`
--

LOCK TABLES `haswritten` WRITE;
/*!40000 ALTER TABLE `haswritten` DISABLE KEYS */;
INSERT INTO `haswritten` VALUES (1,'Le Retour de lÔÇÖenfant soldat',2008),(2,'L\'Aventure ambigu├½',1961);
/*!40000 ALTER TABLE `haswritten` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `Login` varchar(30) NOT NULL,
  `LastName` varchar(30) DEFAULT NULL,
  `FirstName` varchar(30) DEFAULT NULL,
  `EmailAddress` varchar(255) DEFAULT NULL,
  `HashedPassword` varchar(255) DEFAULT NULL,
  `Category` char(1) DEFAULT NULL,
  `maxBooks` int DEFAULT NULL,
  `maxDays` int DEFAULT NULL,
  PRIMARY KEY (`Login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES ('kelga','Kambou','Elga','kambou.elga@ensimag-grenoble.fr','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','A',4,7),('kmanu','Konan','Emmanuel','emmanuel.konan@student-cs.fr','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','M',6,10),('TSerge4','Tehe','Serge','tehe.serge@univ.lorraine.fr','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','A',4,7);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'libraryDb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-14 19:32:28
