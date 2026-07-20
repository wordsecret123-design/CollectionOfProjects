CREATE TABLE `employee` (
  `emp_id` int unsigned NOT NULL,
  `emp_first_name` varchar(1000) NOT NULL,
  `emp_last_name` varchar(1000) NOT NULL,
  `emp_address` varchar(1000) NOT NULL DEFAULT 'N/A',
  `emp_userType` varchar(1000) NOT NULL,
  `emp_username` varchar(1000) NOT NULL,
  `emp_password` varchar(1000) NOT NULL,
  PRIMARY KEY (`emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
