-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: ecommerce
-- ------------------------------------------------------
-- Server version	5.7.31-log

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
-- Table structure for table `banner`
--

DROP TABLE IF EXISTS `banner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `banner` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `image` varchar(255) DEFAULT NULL,
  `sub_title` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banner`
--

LOCK TABLES `banner` WRITE;
/*!40000 ALTER TABLE `banner` DISABLE KEYS */;
INSERT INTO `banner` VALUES (2,'string','subtitle','newitemslsit',NULL);
/*!40000 ALTER TABLE `banner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `baught_products`
--

DROP TABLE IF EXISTS `baught_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `baught_products` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PRODUCT_ID` bigint(20) DEFAULT NULL,
  `COST` bigint(20) DEFAULT NULL,
  `QUANTITY` bigint(20) DEFAULT NULL,
  `ORDER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `baught_products`
--

LOCK TABLES `baught_products` WRITE;
/*!40000 ALTER TABLE `baught_products` DISABLE KEYS */;
INSERT INTO `baught_products` VALUES (1,1,1800,1,1),(2,1,1800,1,2),(3,1,1800,1,3),(4,1,1800,1,4),(5,1,1800,1,5);
/*!40000 ALTER TABLE `baught_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bills`
--

DROP TABLE IF EXISTS `bills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bills` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bill` blob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bills`
--

LOCK TABLES `bills` WRITE;
/*!40000 ALTER TABLE `bills` DISABLE KEYS */;
/*!40000 ALTER TABLE `bills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PRODUCT_ID` bigint(20) DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  `QUANTITY` bigint(20) DEFAULT NULL,
  `DATE` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) DEFAULT NULL,
  `DESCRIPTION` varchar(1000) DEFAULT NULL,
  `IS_AVAILABLE` varchar(50) DEFAULT NULL,
  `IMAGE` varchar(255) DEFAULT NULL,
  `STATIC_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Footwear',NULL,'true','https://multifileupload-bucket.s3.ap-south-1.amazonaws.com/product/foot.webp',3),(2,'Clothing',NULL,'true','https://multifileupload-bucket.s3.ap-south-1.amazonaws.com/product/clothing.jpeg',3),(3,'Puma',NULL,'true','https://multifileupload-bucket.s3.ap-south-1.amazonaws.com/product/pume.jpg',1),(4,'Adidas',NULL,'true','https://multifileupload-bucket.s3.ap-south-1.amazonaws.com/product/Adidas-logo.jpg',1),(5,'Louis Vuitton',NULL,'true','https://multifileupload-bucket.s3.ap-south-1.amazonaws.com/product/492px-Louis_Vuitton_logo_and_wordmark.svg.png',1);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complains`
--

DROP TABLE IF EXISTS `complains`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `complains` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BAUGHT_PRODUCT_ID` bigint(20) DEFAULT NULL,
  `REASON` varchar(45) DEFAULT NULL,
  `DESCRIPTION` varchar(1000) DEFAULT NULL,
  `PRIORITY` varchar(45) DEFAULT NULL,
  `START_DATE` varchar(45) DEFAULT NULL,
  `END_DATE` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complains`
--

LOCK TABLES `complains` WRITE;
/*!40000 ALTER TABLE `complains` DISABLE KEYS */;
/*!40000 ALTER TABLE `complains` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) DEFAULT NULL,
  `LAST_NAME` varchar(45) DEFAULT NULL,
  `ADDRESS` varchar(500) DEFAULT NULL,
  `MOBILE_NUMBER` varchar(45) DEFAULT NULL,
  `EMAIL_ID` varchar(45) DEFAULT NULL,
  `CITY` varchar(45) DEFAULT NULL,
  `STATE` varchar(45) DEFAULT NULL,
  `COUNTRY` varchar(45) DEFAULT NULL,
  `PIN_CODE` varchar(45) DEFAULT NULL,
  `PASSWORD` varchar(45) DEFAULT NULL,
  `OTP` varchar(45) DEFAULT NULL,
  `DEVICE_ID` varchar(100) DEFAULT NULL,
  `OS` varchar(45) DEFAULT NULL,
  `IS_ADMIN` varchar(255) DEFAULT NULL,
  `IS_GUEST` varchar(255) DEFAULT NULL,
  `LANDMARK` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'sachin','parmar','3k16','7983606931','abc@gmail.com','Bhopal','MP','India','244012','pass',NULL,'fdasf','android',NULL,NULL,NULL),(2,'vivek','Sharma','3F ModelTown','9887646553','vivek@gmail.com','Ghaziabad','UP','India','201001','passvivek',NULL,NULL,NULL,NULL,NULL,NULL),(3,'Shivam','Sharma','3F GandhiNAGAR','9887646587','shivam@gmail.com','Ghaziabad','UP','India','201677','passshivam',NULL,NULL,NULL,NULL,NULL,NULL),(4,'Surya','prakash','3F SHiv Nagar','9887646987','surya@gmail.com','Ghaziabad','UP','India','201987','passSurya',NULL,NULL,NULL,NULL,NULL,NULL),(5,'Sagar','patil','pune','7769979396','sap@gmail.com','Mumbai','Maharashtra','India','400102','test123',NULL,NULL,NULL,'false','false',NULL),(6,'shiva','patil','civil lines','9864235789','shiva@gmail.com','MBD','UP','India','247281','passShiva',NULL,NULL,NULL,'false','false','string');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discount`
--

DROP TABLE IF EXISTS `discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discount` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `PERCENTAGE_OF_BASE_COST` bigint(20) DEFAULT NULL,
  `COST_AFTER_DISCOUNT` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discount`
--

LOCK TABLES `discount` WRITE;
/*!40000 ALTER TABLE `discount` DISABLE KEYS */;
INSERT INTO `discount` VALUES (1,'Diwali Discount','discount',10,NULL);
/*!40000 ALTER TABLE `discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ADDRESS` varchar(255) DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  `ORDER_TOTAL` bigint(20) DEFAULT NULL,
  `STATUS` varchar(255) DEFAULT NULL,
  `DELIVERY_DATE` varchar(255) DEFAULT NULL,
  `AMT_PAID` bigint(20) DEFAULT NULL,
  `CUSTOMER_NAME` varchar(255) DEFAULT NULL,
  `DELIVERY_PERSON_MOBILE_NUMBER` varchar(255) DEFAULT NULL,
  `DELIVERY_PERSON_NAME` varchar(255) DEFAULT NULL,
  `OFFER_PRICE` bigint(20) DEFAULT NULL,
  `ORDER_DATE` varchar(255) DEFAULT NULL,
  `WALLET_AMT_USED` bigint(20) DEFAULT NULL,
  `order_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'MDA',1,1800,'placed','2021-04-25',1800,'sachin',NULL,NULL,1800,'2021-04-23',0,NULL);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) DEFAULT NULL,
  `DESCRIPTION` varchar(1000) DEFAULT NULL,
  `SUB_CATEGORY_ID` bigint(20) DEFAULT NULL,
  `IS_AVAILABLE` varchar(50) DEFAULT NULL,
  `EST_TIME_TO_DELIVER` varchar(45) DEFAULT NULL,
  `NO_OF_PIECE_SOLD` bigint(20) DEFAULT NULL,
  `COST` bigint(20) DEFAULT NULL,
  `IMAGE_1` varchar(255) DEFAULT NULL,
  `IMAGE_2` varchar(255) DEFAULT NULL,
  `IMAGE_3` varchar(255) DEFAULT NULL,
  `IMAGE_4` varchar(255) DEFAULT NULL,
  `BRAND_ID` bigint(20) DEFAULT NULL,
  `DISCOUNT_ID` bigint(20) DEFAULT NULL,
  `WALLET_CREDIT_AMT` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Puma T-Shirt','Puma T shirt with\n1- Black Color',2,'true',NULL,NULL,2000,'https://multifileupload-bucket.s3.ap-south-1.amazonaws.com/product/download.jpeg','https://multifileupload-bucket.s3.ap-south-1.amazonaws.com/product/download.jpeg','https://multifileupload-bucket.s3.ap-south-1.amazonaws.com/product/download.jpeg',NULL,3,1,5),(2,'Adidas T-shirt','Adidas T- shirt',2,'true',NULL,NULL,3000,'https://multifileupload-bucket.s3.ap-south-1.amazonaws.com/product/cad042_1.jpg','https://multifileupload-bucket.s3.ap-south-1.amazonaws.com/product/cad042_1.jpg','https://multifileupload-bucket.s3.ap-south-1.amazonaws.com/product/cad042_1.jpg',NULL,4,1,10);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sub_category`
--

DROP TABLE IF EXISTS `sub_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sub_category` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) DEFAULT NULL,
  `DESCRIPTION` varchar(1000) DEFAULT NULL,
  `IS_AVAILABLE` varchar(50) DEFAULT NULL,
  `CATEGORY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sub_category`
--

LOCK TABLES `sub_category` WRITE;
/*!40000 ALTER TABLE `sub_category` DISABLE KEYS */;
INSERT INTO `sub_category` VALUES (1,'mobiles','mobile items','yes',1),(2,'tv','television','yes',1),(3,'chair','sit','yes',4),(4,'KidsCloth',NULL,'yes',2),(5,'WomenCloths',NULL,'yes',2),(6,'mensCloth',NULL,'yes',2),(7,'Wintercloth',NULL,'yes',2),(8,'summerCloth',NULL,'yes',2),(9,'jwellery24K',NULL,'yes',3),(10,'jwellery20k',NULL,'yes',3),(11,'Sofa',NULL,'yes',4),(12,'Table',NULL,'yes',4);
/*!40000 ALTER TABLE `sub_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` bigint(20) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `razor_pay_order_id` varchar(255) DEFAULT NULL,
  `signature` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  `transaction_date` varchar(255) DEFAULT NULL,
  `transaction_id` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wallet`
--

DROP TABLE IF EXISTS `wallet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wallet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wallet`
--

LOCK TABLES `wallet` WRITE;
/*!40000 ALTER TABLE `wallet` DISABLE KEYS */;
INSERT INTO `wallet` VALUES (1,5.00,1),(2,0.00,6);
/*!40000 ALTER TABLE `wallet` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-30 13:27:51
