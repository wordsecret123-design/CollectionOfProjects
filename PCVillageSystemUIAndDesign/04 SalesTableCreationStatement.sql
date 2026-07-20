CREATE TABLE `sales` (
  `sales_id` int unsigned NOT NULL AUTO_INCREMENT,
  `product_id` int unsigned NOT NULL,
  `cashier_id` int unsigned NOT NULL,
  `sales_product_price` int unsigned NOT NULL,
  `sales_quantity_sold` int unsigned NOT NULL,
  `sales_total_price` int unsigned NOT NULL,
  `sales_date_of_purchase` varchar(12) NOT NULL,
  PRIMARY KEY (`sales_id`),
  KEY `FK_product_id` (`product_id`),
  KEY `FK_cashier_id` (`cashier_id`),
  CONSTRAINT `cashier_id` FOREIGN KEY (`cashier_id`) REFERENCES `employee` (`emp_id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_product_id` FOREIGN KEY (`product_id`) REFERENCES `inventory` (`product_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
