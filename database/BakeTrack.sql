-- MySQL dump 10.13  Distrib 8.0.42, for Linux (x86_64)
--
-- Host: localhost    Database: PROJECT_BAKERY
-- ------------------------------------------------------
-- Server version	8.0.42-0ubuntu0.24.04.1

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
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendance` (
  `employee_id` int NOT NULL,
  `attend_date` date NOT NULL,
  `check_in_time` time DEFAULT NULL,
  `check_out_time` time DEFAULT NULL,
  `status` enum('present','absent','late') NOT NULL,
  PRIMARY KEY (`employee_id`,`attend_date`),
  CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
INSERT INTO `attendance` VALUES (1,'2025-06-01','08:00:00','17:00:00','present'),(1,'2025-06-07','08:00:00','17:00:00','present'),(2,'2025-06-01','08:15:00','17:00:00','late'),(2,'2025-06-06','08:00:00','17:00:00','present'),(2,'2025-06-07','08:00:00','17:00:00','present'),(2,'2025-06-11','08:00:00','17:00:00','present'),(2,'2025-06-12','08:00:00','17:00:00','present'),(3,'2025-06-01','08:00:00','17:00:00','absent'),(4,'2025-06-01','08:00:00','17:00:00','present'),(5,'2025-06-01','08:05:00','17:00:00','present'),(6,'2025-06-01','08:30:00','16:30:00','late'),(7,'2025-06-01','08:00:00','17:00:00','present');
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `customer_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `contact_no` varchar(20) NOT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `unique_contact` (`contact_no`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Cafe Delight','100 Coffee St','0771234567'),(2,'Garment Factory 1','500 Textile Ave','0772234567'),(3,'Bakery Express','200 Bakery Blvd','0773234567'),(4,'Garment Factory 2','600 Fabric Rd','0774234567'),(5,'Mobile Seller Group A','N/A','0775234567'),(6,'Cafe Sunrise','300 Morning St','0776234567'),(7,'Garment Factory 3','700 Stitch Ln','0777234567'),(8,'Sandula','mathugama,kaluthara','0765371402');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivery`
--

DROP TABLE IF EXISTS `delivery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `delivery` (
  `delivery_id` int NOT NULL AUTO_INCREMENT,
  `vehicle_id` int DEFAULT NULL,
  `employee_id` int DEFAULT NULL,
  `delivery_date` date NOT NULL,
  `area` varchar(100) NOT NULL,
  PRIMARY KEY (`delivery_id`),
  KEY `vehicle_id` (`vehicle_id`),
  KEY `employee_id` (`employee_id`),
  CONSTRAINT `delivery_ibfk_1` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`vehicle_id`),
  CONSTRAINT `delivery_ibfk_2` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery`
--

LOCK TABLES `delivery` WRITE;
/*!40000 ALTER TABLE `delivery` DISABLE KEYS */;
INSERT INTO `delivery` VALUES (1,1,2,'2025-06-01','Colombo 01'),(2,2,3,'2025-06-01','Colombo 02'),(3,3,5,'2025-06-02','Colombo 03'),(4,1,7,'2025-06-02','Colombo 04'),(5,4,2,'2025-06-03','Colombo 05'),(6,5,3,'2025-06-03','Colombo 06'),(7,6,5,'2025-06-04','Colombo 07'),(8,7,2,'2025-06-06','eeeee'),(9,1,NULL,'2025-06-07','mathugama'),(10,7,2,'2025-06-12','kaluthara,mathugama');
/*!40000 ALTER TABLE `delivery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `employee_id` int NOT NULL AUTO_INCREMENT,
  `emp_name` varchar(50) NOT NULL,
  `emp_address` varchar(100) DEFAULT NULL,
  `salary` decimal(10,2) NOT NULL,
  `contact_no` varchar(15) DEFAULT NULL,
  `roles` enum('Production_emp','Mobile_sellers') NOT NULL,
  PRIMARY KEY (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'Alice Johnson','123 Elm St',65000.00,'0711234567','Production_emp'),(2,'Bob Smith','456 Oak St',35000.00,'0765371402','Mobile_sellers'),(3,'Charlie Lee','789 Pine St',30000.00,'0713234567','Mobile_sellers'),(4,'Diana Ray','321 Maple St',55000.00,'0714234567','Production_emp'),(5,'Evan Black','147 Cedar St',28000.00,'0715234567','Mobile_sellers'),(6,'Fiona White','258 Birch St',40000.00,'0716234567','Production_emp'),(7,'George Green','369 Walnut St',34000.00,'0717234567','Mobile_sellers');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingredient`
--

DROP TABLE IF EXISTS `ingredient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredient` (
  `ingredient_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `stock_amount` int DEFAULT NULL,
  `unit` varchar(20) NOT NULL,
  `buying_price` decimal(10,2) NOT NULL,
  `expire_date` date NOT NULL,
  PRIMARY KEY (`ingredient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredient`
--

LOCK TABLES `ingredient` WRITE;
/*!40000 ALTER TABLE `ingredient` DISABLE KEYS */;
INSERT INTO `ingredient` VALUES (1,'Flour',2200,'g',80.00,'2025-12-31'),(2,'Sugar',3800,'g',60.00,'2025-10-31'),(3,'Butter',10000,'g',150.00,'2025-09-15'),(4,'Yeast',2000,'g',100.00,'2025-11-30'),(5,'Chocolate',6000,'g',300.00,'2025-08-20'),(6,'Eggs',988,'pieces',12.00,'2025-07-25'),(7,'Milk',500,'liters',90.00,'2025-06-30');
/*!40000 ALTER TABLE `ingredient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_detail` (
  `product_id` int NOT NULL,
  `order_id` int NOT NULL,
  `quantity` int NOT NULL,
  `price_at_order` decimal(10,2) NOT NULL,
  PRIMARY KEY (`product_id`,`order_id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `order_detail_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `order_detail_ibfk_2` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` VALUES (1,1,50,50.00),(1,8,3,50.00),(1,9,3,50.00),(2,1,5,500.00),(2,8,2,500.00),(2,9,2,500.00),(3,2,30,80.00),(4,3,10,120.00),(4,10,3,120.00),(5,4,7,200.00),(5,10,4,200.00),(6,5,20,150.00),(7,6,15,130.00);
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int DEFAULT NULL,
  `delivery_id` int DEFAULT NULL,
  `order_date` date NOT NULL,
  `total_price` decimal(10,2) NOT NULL,
  `status` enum('pending','processing','in transit','Delivered','cancelled') DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `customer_id` (`customer_id`),
  KEY `delivery_id` (`delivery_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`delivery_id`) REFERENCES `delivery` (`delivery_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1,1,'2025-06-01',5000.00,'pending'),(2,2,2,'2025-06-01',3000.00,'processing'),(3,3,3,'2025-06-02',4500.00,'in transit'),(4,4,4,'2025-06-02',6000.00,'Delivered'),(5,5,5,'2025-06-03',2000.00,'cancelled'),(6,6,6,'2025-06-03',3500.00,'processing'),(7,7,7,'2025-06-04',4000.00,'pending'),(8,8,8,'2025-06-06',1150.00,'Delivered'),(9,8,9,'2025-06-07',1150.00,'processing'),(10,8,10,'2025-06-12',1160.00,'Delivered');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `payment_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `payment_method` varchar(20) DEFAULT NULL,
  `payment_date` date DEFAULT NULL,
  `status` enum('pending','paid') DEFAULT NULL,
  PRIMARY KEY (`payment_id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,1,5000.00,'Cash','2025-06-02','paid'),(2,2,3000.00,'Credit Card','2025-06-02','pending'),(3,3,4500.00,'Cash','2025-06-03','paid'),(4,4,6000.00,'Bank Transfer','2025-06-04','paid'),(5,5,2000.00,'Cash',NULL,'pending'),(6,6,3500.00,'Credit Card','2025-06-05','paid'),(7,7,4000.00,'Cash',NULL,'pending'),(8,8,1150.00,'card','2025-06-06','paid'),(9,9,1150.00,NULL,NULL,'pending'),(10,10,1160.00,'cash','2025-06-12','paid');
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payroll`
--

DROP TABLE IF EXISTS `payroll`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payroll` (
  `payroll_id` int NOT NULL AUTO_INCREMENT,
  `employee_id` int DEFAULT NULL,
  `pay_date` date NOT NULL,
  `over_time_hours` decimal(5,2) DEFAULT NULL,
  `base_salary` decimal(10,2) DEFAULT NULL,
  `full_salary` decimal(10,2) NOT NULL,
  `status` enum('paid','not paid') NOT NULL,
  PRIMARY KEY (`payroll_id`),
  KEY `employee_id` (`employee_id`),
  CONSTRAINT `payroll_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payroll`
--

LOCK TABLES `payroll` WRITE;
/*!40000 ALTER TABLE `payroll` DISABLE KEYS */;
INSERT INTO `payroll` VALUES (1,1,'2025-05-31',10.00,60000.00,65000.00,'paid'),(2,2,'2025-05-31',5.00,32000.00,35000.00,'paid'),(3,3,'2025-05-31',8.00,27000.00,30000.00,'paid'),(4,4,'2025-05-31',12.00,50000.00,55000.00,'paid'),(5,5,'2025-05-31',6.00,26000.00,28000.00,'paid'),(6,6,'2025-05-31',7.00,38000.00,40000.00,'paid'),(7,7,'2025-05-31',4.00,32000.00,34000.00,'paid'),(8,1,'2025-06-30',9.00,60000.00,64500.00,'paid'),(9,2,'2025-06-30',6.00,32000.00,35000.00,'paid'),(10,3,'2025-06-30',7.00,27000.00,29500.00,'paid'),(11,4,'2025-06-30',10.00,50000.00,54500.00,'paid'),(12,5,'2025-06-30',5.00,26000.00,27500.00,'paid'),(13,6,'2025-06-30',8.00,38000.00,41000.00,'not paid'),(14,7,'2025-06-30',3.00,32000.00,33500.00,'not paid');
/*!40000 ALTER TABLE `payroll` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `category` varchar(100) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `total_quantity` int NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Bun Classic','Buns',50.00,194,'Classic soft bun'),(2,'Chocolate Cake','Cakes',500.00,46,'Rich chocolate layered cake'),(3,'Cinnamon Roll','Buns',80.00,150,'Sweet cinnamon roll with icing'),(4,'Cupcake Vanilla','Cakes',120.00,97,'Vanilla cupcake with cream'),(5,'Multigrain Bread','Bread',200.00,66,'Healthy multigrain bread loaf'),(6,'Croissant','Pastries',150.00,120,'Flaky buttery croissant'),(7,'Cheese Danish','Pastries',130.00,90,'Pastry with cream cheese filling');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_ingredient`
--

DROP TABLE IF EXISTS `product_ingredient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_ingredient` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `ingredient_id` int NOT NULL,
  `quantity_required` decimal(10,2) NOT NULL,
  `unit` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  KEY `ingredient_id` (`ingredient_id`),
  CONSTRAINT `product_ingredient_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE,
  CONSTRAINT `product_ingredient_ibfk_2` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredient` (`ingredient_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_ingredient`
--

LOCK TABLES `product_ingredient` WRITE;
/*!40000 ALTER TABLE `product_ingredient` DISABLE KEYS */;
INSERT INTO `product_ingredient` VALUES (1,1,1,500.00,'g'),(2,1,2,200.00,'g'),(3,2,5,1000.00,'g'),(4,3,3,300.00,'g'),(5,4,6,4.00,'pieces'),(6,5,1,1200.00,'g'),(7,6,4,400.00,'g');
/*!40000 ALTER TABLE `product_ingredient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `supplier_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `contact` varchar(15) DEFAULT NULL,
  `address` varchar(100) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`supplier_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (1,'Supplier A','0771230001','10 Supplier Rd','contact@suppliera.com'),(2,'Supplier B','0771230002','20 Supplier Rd','sandulasanchana@gmail.com'),(3,'Supplier C','0771230003','30 Supplier Rd','contact@supplierc.com'),(4,'Supplier D','0771230004','40 Supplier Rd','contact@supplierd.com'),(5,'Supplier E','0771230005','50 Supplier Rd','contact@suppliere.com'),(6,'Supplier F','0771230006','60 Supplier Rd','contact@supplierf.com'),(7,'Supplier G','0771230007','70 Supplier Rd','contact@supplierg.com');
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier_ingredient`
--

DROP TABLE IF EXISTS `supplier_ingredient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier_ingredient` (
  `ingredient_id` int NOT NULL,
  `supplier_id` int NOT NULL,
  `price_per_unit` decimal(10,2) NOT NULL,
  `unit` varchar(20) NOT NULL,
  `last_order_date` date NOT NULL,
  PRIMARY KEY (`ingredient_id`,`supplier_id`),
  KEY `supplier_id` (`supplier_id`),
  CONSTRAINT `supplier_ingredient_ibfk_1` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredient` (`ingredient_id`),
  CONSTRAINT `supplier_ingredient_ibfk_2` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier_ingredient`
--

LOCK TABLES `supplier_ingredient` WRITE;
/*!40000 ALTER TABLE `supplier_ingredient` DISABLE KEYS */;
INSERT INTO `supplier_ingredient` VALUES (1,1,80.00,'kg','2025-05-01'),(2,2,60.00,'kg','2025-05-03'),(3,3,150.00,'kg','2025-05-05'),(4,4,100.00,'kg','2025-05-07'),(5,5,300.00,'kg','2025-06-06'),(6,6,12.00,'pieces','2025-05-11'),(7,7,90.00,'liters','2025-05-13');
/*!40000 ALTER TABLE `supplier_ingredient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_settings`
--

DROP TABLE IF EXISTS `system_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_settings` (
  `setting_name` varchar(50) NOT NULL,
  `setting_value` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`setting_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_settings`
--

LOCK TABLES `system_settings` WRITE;
/*!40000 ALTER TABLE `system_settings` DISABLE KEYS */;
INSERT INTO `system_settings` VALUES ('ot_rate',170.00);
/*!40000 ALTER TABLE `system_settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL,
  `user_password` varchar(255) NOT NULL,
  `roles` enum('Admin','StoreManager','HRManager') NOT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Owner','567','Admin','sandulasanchana@gmail.com'),(2,'HR','345','HRManager','sandulasanchana@gmail.com'),(3,'SK','9876','StoreManager','sandulasanchana@gmail.com');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicle`
--

DROP TABLE IF EXISTS `vehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehicle` (
  `vehicle_id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(20) DEFAULT NULL,
  `license_plate` varchar(20) NOT NULL,
  `status` enum('available','not available') DEFAULT NULL,
  PRIMARY KEY (`vehicle_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicle`
--

LOCK TABLES `vehicle` WRITE;
/*!40000 ALTER TABLE `vehicle` DISABLE KEYS */;
INSERT INTO `vehicle` VALUES (1,'Van','ABC-1234','not available'),(2,'Truck','XYZ-5678','not available'),(3,'Motorbike','MNO-4321','available'),(4,'Van','DEF-2345','available'),(5,'Truck','UVW-6789','available'),(6,'Motorbike','PQR-9876','not available'),(7,'Van','GHI-3456','available');
/*!40000 ALTER TABLE `vehicle` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-18  8:08:50
