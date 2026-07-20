CREATE TABLE `inventory` (
  `product_id` int unsigned NOT NULL AUTO_INCREMENT,
  `product_type` varchar(1000) NOT NULL,
  `product_brand` varchar(1000) NOT NULL,
  `product_model` varchar(1000) NOT NULL,
  `product_purchase_price` int unsigned NOT NULL,
  `product_selling_price` int unsigned NOT NULL,
  `product_quantity` int unsigned NOT NULL,
  `product_last_changed_date` varchar(12) NOT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
