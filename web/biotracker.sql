-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 29, 2021 at 12:11 PM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `biotracker`
--

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE `comments` (
  `comm_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `comm_text` text NOT NULL,
  `post_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `community_post`
--

CREATE TABLE `community_post` (
  `post_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `likes` int(11) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `daily_data`
--

CREATE TABLE `daily_data` (
  `data_id` int(11) NOT NULL,
  `ammonia_level` int(11) DEFAULT NULL,
  `ph_level` int(11) DEFAULT NULL,
  `oxygen_level` int(11) DEFAULT NULL,
  `nitrogen_level` int(11) DEFAULT NULL,
  `nitrate_level` int(11) NOT NULL,
  `nitrite_level` int(11) NOT NULL,
  `mortality_count` int(11) DEFAULT NULL,
  `data_date` varchar(30) NOT NULL,
  `farm_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `daily_data`
--

INSERT INTO `daily_data` (`data_id`, `ammonia_level`, `ph_level`, `oxygen_level`, `nitrogen_level`, `nitrate_level`, `nitrite_level`, `mortality_count`, `data_date`, `farm_id`) VALUES
(1, 10, 25, 55, 54, 0, 0, 5, '16-04-2021', 8),
(6, 10, 25, 55, 54, 10, 11, 5, '15-04-2021', 8),
(7, 7, 7, 8, 6, 7, 8, 0, '17-04-2021', 8),
(8, 7, 7, 10, 8, 9, 10, 0, '18-04-2021', 8),
(9, 8, 9, 7, 9, 5, 8, 0, '19-04-2021', 8),
(10, 7, 8, 10, 7, 9, 9, 0, '22-04-2021', 9);

-- --------------------------------------------------------

--
-- Table structure for table `farm`
--

CREATE TABLE `farm` (
  `farm_id` int(11) NOT NULL,
  `fish_type` varchar(35) NOT NULL,
  `fish_count` int(11) NOT NULL,
  `tank_volume` decimal(10,0) NOT NULL,
  `start_date` date NOT NULL,
  `est_time` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `farm`
--

INSERT INTO `farm` (`farm_id`, `fish_type`, `fish_count`, `tank_volume`, `start_date`, `est_time`, `user_id`) VALUES
(8, 'Nutter', 500, '6358500', '2021-04-16', 6, 13),
(9, 'Gift Tillapia', 500, '420000', '2021-04-22', 6, 13);

-- --------------------------------------------------------

--
-- Table structure for table `instructions`
--

CREATE TABLE `instructions` (
  `ins_id` int(11) NOT NULL,
  `language` varchar(15) NOT NULL,
  `ins_name` varchar(30) NOT NULL,
  `ins_text` text NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL,
  `mobile` bigint(12) NOT NULL,
  `password` varchar(35) NOT NULL,
  `role` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`login_id`, `mobile`, `password`, `role`) VALUES
(2, 9564558548, 'asdfg', 'USER'),
(3, 9565224554, 'qweas', 'USER'),
(4, 8766316667, 'aaass', 'USER'),
(5, 9546679357, 'momoom', 'USER'),
(6, 5484584854, 'asaaas', 'USER'),
(7, 9533626233, 'dhldhl', 'USER'),
(10, 9633058949, 'suhu3515@', 'USER'),
(13, 9946739215, 'pvmstores', 'SELLER'),
(15, 9876543210, 'admin', 'ADMIN'),
(16, 6238383110, 'anbintl', 'SELLER'),
(17, 9946739216, 'sakkeer123', 'USER');

-- --------------------------------------------------------

--
-- Table structure for table `marketplace`
--

CREATE TABLE `marketplace` (
  `product_id` int(11) NOT NULL,
  `product_name` varchar(70) NOT NULL,
  `product_price` int(11) NOT NULL,
  `product_qty` int(11) NOT NULL,
  `product_desc` varchar(100) NOT NULL,
  `product_img` text NOT NULL,
  `seller_id` int(11) NOT NULL,
  `product_status` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `marketplace`
--

INSERT INTO `marketplace` (`product_id`, `product_name`, `product_price`, `product_qty`, `product_desc`, `product_img`, `seller_id`, `product_status`) VALUES
(3, 'SunSun HJ - 3000 Multi Function Submersible Pump', 2100, 12, 'Pump body and casing are made of high quality plastic which is anti corrosive and highly durable.', 'images/motor.jpg', 2, 1),
(4, 'Gift Tillapia', 4, 1000, 'Best Quality fish seeds', 'images/Tilapia-Seed-gift.jpg', 2, 0);

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `product_qty` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `order_amount` int(11) NOT NULL,
  `payment_mode` varchar(20) NOT NULL,
  `delivery_date` date DEFAULT NULL,
  `payment_status` int(11) NOT NULL DEFAULT 0,
  `order_status` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `product_id`, `product_qty`, `user_id`, `order_amount`, `payment_mode`, `delivery_date`, `payment_status`, `order_status`) VALUES
(2, 3, 3, 13, 6300, 'COD', NULL, 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `reports`
--

CREATE TABLE `reports` (
  `report_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `report_text` text NOT NULL,
  `post_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `seller`
--

CREATE TABLE `seller` (
  `seller_id` int(11) NOT NULL,
  `seller_name` varchar(40) NOT NULL,
  `seller_place` varchar(40) NOT NULL,
  `seller_addr` varchar(40) NOT NULL,
  `seller_dst` varchar(40) NOT NULL,
  `seller_phone` bigint(12) NOT NULL,
  `seller_mail` varchar(40) NOT NULL,
  `seller_upi_id` varchar(15) NOT NULL,
  `seller_gstin` varchar(30) NOT NULL,
  `seller_status` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `seller`
--

INSERT INTO `seller` (`seller_id`, `seller_name`, `seller_place`, `seller_addr`, `seller_dst`, `seller_phone`, `seller_mail`, `seller_upi_id`, `seller_gstin`, `seller_status`) VALUES
(2, 'PVM Stores', 'CV Junction', 'Chamravattom Center', 'Malappuram', 9946739215, 'pvmstores@gmail.com', '2147483647', 'GSTIN00568975', 1),
(3, 'ANB International', 'Ermanagalam', 'assalama mission', 'Kozhikode', 6238383110, 'anbintl@gmail.com', '9656823@ybl', 'GSTIN00568965', 0);

-- --------------------------------------------------------

--
-- Table structure for table `tutorials`
--

CREATE TABLE `tutorials` (
  `tut_id` int(11) NOT NULL,
  `language` varchar(15) NOT NULL,
  `tut_name` varchar(30) NOT NULL,
  `tut_txt` text NOT NULL,
  `tut_link` varchar(30) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `user_name` varchar(40) NOT NULL,
  `user_dob` date NOT NULL,
  `user_hname` varchar(30) NOT NULL,
  `user_place` varchar(30) NOT NULL,
  `user_pincode` int(7) NOT NULL,
  `user_dst` varchar(30) NOT NULL,
  `user_mobile` bigint(12) NOT NULL,
  `user_email` varchar(35) NOT NULL,
  `user_status` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `user_name`, `user_dob`, `user_hname`, `user_place`, `user_pincode`, `user_dst`, `user_mobile`, `user_email`, `user_status`) VALUES
(2, 'fsg', '2021-04-09', 'asds', 'adadd', 558464, 'adas', 9564558548, 's@s.s', 1),
(3, 'ysf', '2021-04-09', 'afaf', 'dsff', 678581, 'zfacx', 9565224554, 's@s.s', 1),
(4, 'suh', '2021-04-09', 'bsnjs', 'ghsjh', 679594, 'bakjzis', 8766316667, 's@s.s', 1),
(5, 'sijhh', '2021-04-09', 'hhjs', 'bjsjjs', 975946, 'bjauhs', 9546679357, 's@s.s', 1),
(6, 'fsff', '0000-00-00', 'asaaa', 'dgweg', 685581, 'FCC dh', 5484584854, 'sczd@sdd.ffg', 1),
(7, 'fhh', '2021-04-09', 'gghg', 'gjhg', 678585, 'dgfd', 9533626233, 's@e.s', 1),
(10, 'Suhail A K', '1998-10-28', 'Valathel House', 'Maranchery', 679581, 'Malappuram', 9633058949, 'suhu3515@gmail.com', 1),
(11, 'Safwan V', '2002-09-30', 'Valathel House', 'Maranchery', 679581, 'Malappuram', 7994367615, 'safwan@gmail.com', 1),
(13, 'Mohammed Sakkeer', '1980-05-02', 'valathel', 'athikaripadi', 679581, 'malappuram', 9946739216, 'mohamedsawani@gmail.com', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`comm_id`),
  ADD KEY `fk_commented_user` (`user_id`),
  ADD KEY `fk_commented_post` (`post_id`);

--
-- Indexes for table `community_post`
--
ALTER TABLE `community_post`
  ADD PRIMARY KEY (`post_id`),
  ADD KEY `fk_post_user` (`user_id`);

--
-- Indexes for table `daily_data`
--
ALTER TABLE `daily_data`
  ADD PRIMARY KEY (`data_id`),
  ADD KEY `fk_farm_data` (`farm_id`);

--
-- Indexes for table `farm`
--
ALTER TABLE `farm`
  ADD PRIMARY KEY (`farm_id`),
  ADD KEY `fk_user_farm` (`user_id`);

--
-- Indexes for table `instructions`
--
ALTER TABLE `instructions`
  ADD PRIMARY KEY (`ins_id`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`login_id`);

--
-- Indexes for table `marketplace`
--
ALTER TABLE `marketplace`
  ADD PRIMARY KEY (`product_id`),
  ADD KEY `fk_product_seller` (`seller_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `fk_ordered_product` (`product_id`),
  ADD KEY `fk_ordered_user` (`user_id`);

--
-- Indexes for table `reports`
--
ALTER TABLE `reports`
  ADD PRIMARY KEY (`report_id`),
  ADD KEY `fk_reported_user` (`user_id`),
  ADD KEY `fk_reported_post` (`post_id`);

--
-- Indexes for table `seller`
--
ALTER TABLE `seller`
  ADD PRIMARY KEY (`seller_id`);

--
-- Indexes for table `tutorials`
--
ALTER TABLE `tutorials`
  ADD PRIMARY KEY (`tut_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comments`
--
ALTER TABLE `comments`
  MODIFY `comm_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `community_post`
--
ALTER TABLE `community_post`
  MODIFY `post_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `daily_data`
--
ALTER TABLE `daily_data`
  MODIFY `data_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `farm`
--
ALTER TABLE `farm`
  MODIFY `farm_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `instructions`
--
ALTER TABLE `instructions`
  MODIFY `ins_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `login`
--
ALTER TABLE `login`
  MODIFY `login_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `marketplace`
--
ALTER TABLE `marketplace`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `reports`
--
ALTER TABLE `reports`
  MODIFY `report_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `seller`
--
ALTER TABLE `seller`
  MODIFY `seller_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tutorials`
--
ALTER TABLE `tutorials`
  MODIFY `tut_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `fk_commented_post` FOREIGN KEY (`post_id`) REFERENCES `community_post` (`post_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_commented_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON UPDATE CASCADE;

--
-- Constraints for table `community_post`
--
ALTER TABLE `community_post`
  ADD CONSTRAINT `fk_post_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON UPDATE CASCADE;

--
-- Constraints for table `daily_data`
--
ALTER TABLE `daily_data`
  ADD CONSTRAINT `fk_farm_data` FOREIGN KEY (`farm_id`) REFERENCES `farm` (`farm_id`) ON UPDATE CASCADE;

--
-- Constraints for table `farm`
--
ALTER TABLE `farm`
  ADD CONSTRAINT `fk_user_farm` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON UPDATE CASCADE;

--
-- Constraints for table `marketplace`
--
ALTER TABLE `marketplace`
  ADD CONSTRAINT `fk_product_seller` FOREIGN KEY (`seller_id`) REFERENCES `seller` (`seller_id`) ON UPDATE CASCADE;

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `fk_ordered_product` FOREIGN KEY (`product_id`) REFERENCES `marketplace` (`product_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_ordered_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON UPDATE CASCADE;

--
-- Constraints for table `reports`
--
ALTER TABLE `reports`
  ADD CONSTRAINT `fk_reported_post` FOREIGN KEY (`post_id`) REFERENCES `community_post` (`post_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_reported_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
