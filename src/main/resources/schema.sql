CREATE TABLE `m_users`
(
   `id` int NOT NULL AUTO_INCREMENT,
   `user_name` varchar (50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
   `furigana` varchar (50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
   `e_mail` varchar (50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
   `password` varchar (250) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
   `postal_code` varchar (50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
   `prefectures` varchar (50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
   `city` varchar (50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
   `town_name` varchar (50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
   `others` varchar (50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
   `phone_number` varchar (50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
   `gender` int NOT NULL,
   `birthday` date NOT NULL,
   `role` varchar (50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
   `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   UNIQUE KEY `e_mail_UNIQUE` (`e_mail`)
);

CREATE TABLE `m_goods`
(
   `id` int NOT NULL AUTO_INCREMENT,
   `goods_name` varchar (50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
   `description` varchar (250) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
   `price` int NOT NULL,
   `image_url` varchar (250) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
   `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   UNIQUE KEY `image_url_UNIQUE` (`image_url`)
);

CREATE TABLE `cart`
(
   `id` int NOT NULL AUTO_INCREMENT,
   `user_id` int NOT NULL,
   `goods_id` int NOT NULL,
   `quantity` varchar (50) NOT NULL,
   `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`)
);

CREATE TABLE `order`
(
   `id` int NOT NULL AUTO_INCREMENT,
   `user_id` int NOT NULL,
   `order_date` datetime NOT NULL,
   `order_number` varchar (50) NOT NULL,
   `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`)
);

CREATE TABLE `order_details`
(
   `id` int NOT NULL AUTO_INCREMENT,
   `order_id` int NOT NULL,
   `goods_id` int NOT NULL,
   `price` int NOT NULL,
   `quantity` int NOT NULL,
   `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`)
);

CREATE TABLE `delivery_address`
(
   `id` int NOT NULL AUTO_INCREMENT,
   `order_id` int NOT NULL,
   `postal_code` varchar (50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
   `prefectures` varchar (50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
   `city` varchar (50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
   `town_name` varchar (50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
   `others` varchar (50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
   `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`)
);

CREATE TABLE `monthly_sales`
(
   `id` int NOT NULL AUTO_INCREMENT,
   `month` int NOT NULL,
   `year` int NOT NULL,
   `sales` int NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `uq_year_month`
   (
      `year`,
      `month`
   )
);