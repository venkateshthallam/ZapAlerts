-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 01, 2014 at 06:58 AM
-- Server version: 5.5.32
-- PHP Version: 5.4.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `zapalerts`
--
CREATE DATABASE IF NOT EXISTS `zapalerts` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `zapalerts`;

-- --------------------------------------------------------

--
-- Table structure for table `customerinfo`
--

CREATE TABLE IF NOT EXISTS `customerinfo` (
  `CustomerName` varchar(100) NOT NULL,
  `CustomerId` int(10) NOT NULL,
  `E-Mail` varchar(100) NOT NULL,
  `Phone Number` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customerinfo`
--

INSERT INTO `customerinfo` (`CustomerName`, `CustomerId`, `E-Mail`, `Phone Number`) VALUES
('Venkatesh', 1, 'venkatesh.thallam@nyu.edu', 2017906465);

-- --------------------------------------------------------

--
-- Table structure for table `productdata`
--

CREATE TABLE IF NOT EXISTS `productdata` (
  `ProductID` int(100) NOT NULL,
  `ProductName` varchar(100) NOT NULL,
  `CustomerId` int(100) NOT NULL,
  `ProductPrice` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `productdata`
--

INSERT INTO `productdata` (`ProductID`, `ProductName`, `CustomerId`, `ProductPrice`) VALUES
(1, 'ariat', 0, 156.95);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
