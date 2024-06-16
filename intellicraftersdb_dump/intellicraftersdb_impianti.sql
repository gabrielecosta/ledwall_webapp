CREATE DATABASE  IF NOT EXISTS `intellicraftersdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `intellicraftersdb`;
-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: localhost    Database: intellicraftersdb
-- ------------------------------------------------------
-- Server version	8.0.36-0ubuntu0.22.04.1

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
-- Table structure for table `impianti`
--

DROP TABLE IF EXISTS `impianti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `impianti` (
  `id_impianto` int NOT NULL AUTO_INCREMENT,
  `descrizione` varchar(255) DEFAULT NULL,
  `latitudine` decimal(10,5) DEFAULT NULL,
  `longitudine` decimal(10,5) DEFAULT NULL,
  `id_palinsesto` varchar(10) NOT NULL DEFAULT 'P1',
  `status_impianto` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_impianto`),
  KEY `fk_palinsesti_impianti` (`id_palinsesto`),
  CONSTRAINT `fk_palinsesti_impianti` FOREIGN KEY (`id_palinsesto`) REFERENCES `palinsesti` (`id_palinsesto`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `impianti`
--

LOCK TABLES `impianti` WRITE;
/*!40000 ALTER TABLE `impianti` DISABLE KEYS */;
INSERT INTO `impianti` VALUES (1,'Viale della Regione Siciliana',38.11612,13.32942,'P3',1),(2,'Via Francesco Crispi',38.12535,13.36363,'P5',1),(3,'Via Ruggiero Settimo',38.12269,13.35768,'P2',1),(4,'Via della Libertà',38.13500,13.34896,'P1',0),(5,'Piazza Albert Einstein',38.12878,13.32858,'P1',0),(6,'Via Giuseppe Gancibattaglia',38.40001,13.94979,'P4',1),(7,'Via Altofonte 120',38.08834,13.32792,'P1',1),(8,'Via Gennaro Pardo 2',38.22200,13.22220,'P5',1),(9,'piazza marina',0.00000,0.00000,'P1',0),(10,'new desc',0.02330,10.02312,'P1',0),(11,'new desc',0.00000,0.00000,'P5',0);
/*!40000 ALTER TABLE `impianti` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-12 13:37:36
