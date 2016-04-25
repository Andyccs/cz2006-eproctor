-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 12, 2014 at 08:52 AM
-- Server version: 5.6.16
-- PHP Version: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `eproctor_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `account_admin`
--

CREATE TABLE IF NOT EXISTS `account_admin` (
  `Username` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL,
  PRIMARY KEY (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `account_admin`
--

INSERT INTO `account_admin` (`Username`, `Password`) VALUES
('A0001', 'test');

-- --------------------------------------------------------

--
-- Table structure for table `account_invigilator`
--

CREATE TABLE IF NOT EXISTS `account_invigilator` (
  `Username` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL,
  PRIMARY KEY (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `account_invigilator`
--

INSERT INTO `account_invigilator` (`Username`, `Password`) VALUES
('I0001', 'test'),
('I0002', 'test');

-- --------------------------------------------------------

--
-- Table structure for table `account_student`
--

CREATE TABLE IF NOT EXISTS `account_student` (
  `Username` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL,
  PRIMARY KEY (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `account_student`
--

INSERT INTO `account_student` (`Username`, `Password`) VALUES
('U1220001G', 'test'),
('U1220009F', 'test');

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `AdminID` varchar(20) NOT NULL,
  `Name` varchar(255) NOT NULL,
  PRIMARY KEY (`AdminID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`AdminID`, `Name`) VALUES
('A0001', 'Darren');

-- --------------------------------------------------------

--
-- Table structure for table `announcement`
--

CREATE TABLE IF NOT EXISTS `announcement` (
  `AnnouncementID` int(11) NOT NULL AUTO_INCREMENT,
  `Announcement` text NOT NULL,
  PRIMARY KEY (`AnnouncementID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `announcement`
--

INSERT INTO `announcement` (`AnnouncementID`, `Announcement`) VALUES
(1, 'Remember to register'),
(2, 'CZ2004 exam is being postponed until further notice'),
(5, 'Register your calculators'),
(6, 'Do not cheat during examination'),
(7, 'Any form of cheating will cause the exam to stop immediately'),
(8, 'All the best for your exams');

-- --------------------------------------------------------

--
-- Table structure for table `book_exam`
--

CREATE TABLE IF NOT EXISTS `book_exam` (
  `BookExamID` int(11) NOT NULL AUTO_INCREMENT,
  `StudentID` varchar(20) NOT NULL,
  `ExamID` int(11) NOT NULL,
  `ExamDate` date NOT NULL,
  `StartTime` time NOT NULL,
  `EndTime` time NOT NULL,
  PRIMARY KEY (`BookExamID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `book_exam`
--

INSERT INTO `book_exam` (`BookExamID`, `StudentID`, `ExamID`, `ExamDate`, `StartTime`, `EndTime`) VALUES
(1, 'U1220001G', 4, '2014-04-05', '14:00:00', '16:00:00'),
(2, 'U1220001G', 7, '2014-05-19', '12:00:00', '14:00:00'),
(3, 'U1220001G', 8, '2014-05-14', '08:00:00', '10:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `invigilator`
--

CREATE TABLE IF NOT EXISTS `invigilator` (
  `InvigilatorID` varchar(20) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Address` varchar(255) NOT NULL,
  `PostalCode` int(10) NOT NULL,
  `PhoneNumber` int(20) NOT NULL,
  PRIMARY KEY (`InvigilatorID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `invigilator`
--

INSERT INTO `invigilator` (`InvigilatorID`, `Name`, `Address`, `PostalCode`, `PhoneNumber`) VALUES
('I0001', 'Jack', 'Bedok North St 3, Blk 321, #11-513', 642321, 93331111),
('I0002', 'Andy', 'AMK, Block 11', 645312, 123456789);

-- --------------------------------------------------------

--
-- Table structure for table `submit_report`
--

CREATE TABLE IF NOT EXISTS `submit_report` (
  `SubmitReportID` int(11) NOT NULL,
  `StudentID` varchar(20) NOT NULL,
  `ExamID` int(11) NOT NULL,
  `ProctorReport` text NOT NULL,
  PRIMARY KEY (`SubmitReportID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `submit_report`
--

INSERT INTO `submit_report` (`SubmitReportID`, `StudentID`, `ExamID`, `ProctorReport`) VALUES
(1, 'U1220001G', 2, 'Number of Alert: 0\nForfeit Exam: No\nComment: Student behaves well'),
(2, 'U1220001G', 5, 'Number of Alert: 1\nForfeit Exam: No\nComment: None');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `account_admin`
--
ALTER TABLE `account_admin`
  ADD CONSTRAINT `account_admin_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `admin` (`AdminID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `account_invigilator`
--
ALTER TABLE `account_invigilator`
  ADD CONSTRAINT `account_invigilator_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `invigilator` (`InvigilatorID`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
