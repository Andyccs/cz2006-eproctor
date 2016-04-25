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
-- Database: `university_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE IF NOT EXISTS `course` (
  `CourseCode` varchar(10) NOT NULL,
  `CourseName` varchar(255) NOT NULL,
  PRIMARY KEY (`CourseCode`),
  UNIQUE KEY `CourseCode` (`CourseCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`CourseCode`, `CourseName`) VALUES
('CZ1001', 'Discrete Mathematics'),
('CZ1005', 'Digital Logic'),
('CZ1006', 'Computer Organisation and Architecture'),
('CZ1008', 'Engineering Mathematics'),
('CZ2001', 'Algorithms'),
('CZ2002', 'Object Oriented Design &\r\nProgramming'),
('CZ2003', 'Computer Graphics and\nVisualisation'),
('CZ2004', 'Human Computer Interaction'),
('CZ2006', 'Software Engineering');

-- --------------------------------------------------------

--
-- Table structure for table `exam`
--

CREATE TABLE IF NOT EXISTS `exam` (
  `ExamID` int(11) NOT NULL AUTO_INCREMENT,
  `CourseCode` varchar(10) NOT NULL,
  `Duration` int(10) NOT NULL,
  `Description` varchar(255) NOT NULL,
  `Style` varchar(20) NOT NULL,
  `Question` text NOT NULL,
  `StartDate` date NOT NULL,
  `EndDate` date NOT NULL,
  PRIMARY KEY (`ExamID`),
  UNIQUE KEY `ExamID` (`ExamID`),
  KEY `CourseCode` (`CourseCode`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `exam`
--

INSERT INTO `exam` (`ExamID`, `CourseCode`, `Duration`, `Description`, `Style`, `Question`, `StartDate`, `EndDate`) VALUES
(1, 'CZ1001', 2, 'Cannot use calculator', 'MCQ', '<?xml version="1.0" encoding="UTF-8" ?>\n	<paper>\n		<id>0981095423</id>\n		<course>CZ2007</course>\n		<question number = ''1''>\n			<content>Lee''s parents emigrated from China. They have five children. The first four are named La, Le, Li, and Lo. What did they name the fifth? </content>\n			<img></img>\n			<a>Lala</a>\n			<b>Lu </b>\n			<c>Lee </c>\n			<d>Susan </d>\n		</question>\n		<question number = ''2''>\n			<content> A red house is made from red bricks, a blue house is made from blue bricks, a pink house is made from pink bricks, and a black house is made from black bricks. What is a greenhouse made from?</content>\n			<img></img>\n			<a>Wood </a>\n			<b>Green Bricks</b>\n			<c>Moss-covered stones</c>\n			<d>Glass </d>\n		</question>\n		<question number = ''3''>\n			<content>The Spanish Civil War, which began July 17 of 1936, was fought between:</content>\n			<img></img>\n			<a>Spain and Germany</a>\n			<b>Mexico and the United States</b>\n			<c>Spain and Italy</c>\n			<d>None of the above </d>\n		</question>\n		<question number = ''4''>\n			<content>You''re the pilot of an airplane that travels from New York to Chicago - a distance of 800 miles. The plane travels at 200 MPH and makes one stop for 30 minutes. What is the pilot''s name? </content>\n			<img></img>\n			<a>Necessary information is missing.</a>\n			<b>You can''t tell from the question. </b>\n			<c>Both a and b. </c>\n			<d>You can tell from the question. </d>\n		</question>\n		<question number = ''5''>\n			<content>Extra </content>\n			<img></img>\n			<a>Hahaha</a>\n			<b>blablablabla </b>\n			<c>kukuku </c>\n			<d>lalababa </d>\n		</question>\n	</paper>', '2014-04-22', '2014-04-30'),
(2, 'CZ2006', 2, 'Cannot use calculator', 'MCQ', '<?xml version="1.0" encoding="UTF-8" ?>\n	<paper>\n		<id>0981095423</id>\n		<course>CZ2007</course>\n		<question number = ''1''>\n			<content>Lee''s parents emigrated from China. They have five children. The first four are named La, Le, Li, and Lo. What did they name the fifth? </content>\n			<img></img>\n			<a>Lala</a>\n			<b>Lu </b>\n			<c>Lee </c>\n			<d>Susan </d>\n		</question>\n		<question number = ''2''>\n			<content> A red house is made from red bricks, a blue house is made from blue bricks, a pink house is made from pink bricks, and a black house is made from black bricks. What is a greenhouse made from?</content>\n			<img></img>\n			<a>Wood </a>\n			<b>Green Bricks</b>\n			<c>Moss-covered stones</c>\n			<d>Glass </d>\n		</question>\n		<question number = ''3''>\n			<content>The Spanish Civil War, which began July 17 of 1936, was fought between:</content>\n			<img></img>\n			<a>Spain and Germany</a>\n			<b>Mexico and the United States</b>\n			<c>Spain and Italy</c>\n			<d>None of the above </d>\n		</question>\n		<question number = ''4''>\n			<content>You''re the pilot of an airplane that travels from New York to Chicago - a distance of 800 miles. The plane travels at 200 MPH and makes one stop for 30 minutes. What is the pilot''s name? </content>\n			<img></img>\n			<a>Necessary information is missing.</a>\n			<b>You can''t tell from the question. </b>\n			<c>Both a and b. </c>\n			<d>You can tell from the question. </d>\n		</question>\n		<question number = ''5''>\n			<content>Extra </content>\n			<img></img>\n			<a>Hahaha</a>\n			<b>blablablabla </b>\n			<c>kukuku </c>\n			<d>lalababa </d>\n		</question>\n	</paper>', '2014-04-20', '2014-04-30'),
(3, 'CZ1008', 2, 'Can use calculator', 'MCQ', '<?xml version="1.0" encoding="UTF-8" ?>\n	<paper>\n		<id>0981095423</id>\n		<course>CZ2007</course>\n		<question number = ''1''>\n			<content>Lee''s parents emigrated from China. They have five children. The first four are named La, Le, Li, and Lo. What did they name the fifth? </content>\n			<img></img>\n			<a>Lala</a>\n			<b>Lu </b>\n			<c>Lee </c>\n			<d>Susan </d>\n		</question>\n		<question number = ''2''>\n			<content> A red house is made from red bricks, a blue house is made from blue bricks, a pink house is made from pink bricks, and a black house is made from black bricks. What is a greenhouse made from?</content>\n			<img></img>\n			<a>Wood </a>\n			<b>Green Bricks</b>\n			<c>Moss-covered stones</c>\n			<d>Glass </d>\n		</question>\n		<question number = ''3''>\n			<content>The Spanish Civil War, which began July 17 of 1936, was fought between:</content>\n			<img></img>\n			<a>Spain and Germany</a>\n			<b>Mexico and the United States</b>\n			<c>Spain and Italy</c>\n			<d>None of the above </d>\n		</question>\n		<question number = ''4''>\n			<content>You''re the pilot of an airplane that travels from New York to Chicago - a distance of 800 miles. The plane travels at 200 MPH and makes one stop for 30 minutes. What is the pilot''s name? </content>\n			<img></img>\n			<a>Necessary information is missing.</a>\n			<b>You can''t tell from the question. </b>\n			<c>Both a and b. </c>\n			<d>You can tell from the question. </d>\n		</question>\n		<question number = ''5''>\n			<content>Extra </content>\n			<img></img>\n			<a>Hahaha</a>\n			<b>blablablabla </b>\n			<c>kukuku </c>\n			<d>lalababa </d>\n		</question>\n	</paper>', '2014-05-14', '2014-05-31'),
(4, 'CZ2003', 2, 'Can use calculator', 'MCQ', '<?xml version="1.0" encoding="UTF-8" ?>\n	<paper>\n		<id>0981095423</id>\n		<course>CZ2007</course>\n		<question number = ''1''>\n			<content>Lee''s parents emigrated from China. They have five children. The first four are named La, Le, Li, and Lo. What did they name the fifth? </content>\n			<img></img>\n			<a>Lala</a>\n			<b>Lu </b>\n			<c>Lee </c>\n			<d>Susan </d>\n		</question>\n		<question number = ''2''>\n			<content> A red house is made from red bricks, a blue house is made from blue bricks, a pink house is made from pink bricks, and a black house is made from black bricks. What is a greenhouse made from?</content>\n			<img></img>\n			<a>Wood </a>\n			<b>Green Bricks</b>\n			<c>Moss-covered stones</c>\n			<d>Glass </d>\n		</question>\n		<question number = ''3''>\n			<content>The Spanish Civil War, which began July 17 of 1936, was fought between:</content>\n			<img></img>\n			<a>Spain and Germany</a>\n			<b>Mexico and the United States</b>\n			<c>Spain and Italy</c>\n			<d>None of the above </d>\n		</question>\n		<question number = ''4''>\n			<content>You''re the pilot of an airplane that travels from New York to Chicago - a distance of 800 miles. The plane travels at 200 MPH and makes one stop for 30 minutes. What is the pilot''s name? </content>\n			<img></img>\n			<a>Necessary information is missing.</a>\n			<b>You can''t tell from the question. </b>\n			<c>Both a and b. </c>\n			<d>You can tell from the question. </d>\n		</question>\n		<question number = ''5''>\n			<content>Extra </content>\n			<img></img>\n			<a>Hahaha</a>\n			<b>blablablabla </b>\n			<c>kukuku </c>\n			<d>lalababa </d>\n		</question>\n	</paper>', '2014-04-20', '2014-04-30'),
(5, 'CZ2001', 2, 'Cannot use calculator', 'MCQ', '<?xml version="1.0" encoding="UTF-8" ?>\n	<paper>\n		<id>0981095423</id>\n		<course>CZ2007</course>\n		<question number = ''1''>\n			<content>Lee''s parents emigrated from China. They have five children. The first four are named La, Le, Li, and Lo. What did they name the fifth? </content>\n			<img></img>\n			<a>Lala</a>\n			<b>Lu </b>\n			<c>Lee </c>\n			<d>Susan </d>\n		</question>\n		<question number = ''2''>\n			<content> A red house is made from red bricks, a blue house is made from blue bricks, a pink house is made from pink bricks, and a black house is made from black bricks. What is a greenhouse made from?</content>\n			<img></img>\n			<a>Wood </a>\n			<b>Green Bricks</b>\n			<c>Moss-covered stones</c>\n			<d>Glass </d>\n		</question>\n		<question number = ''3''>\n			<content>The Spanish Civil War, which began July 17 of 1936, was fought between:</content>\n			<img></img>\n			<a>Spain and Germany</a>\n			<b>Mexico and the United States</b>\n			<c>Spain and Italy</c>\n			<d>None of the above </d>\n		</question>\n		<question number = ''4''>\n			<content>You''re the pilot of an airplane that travels from New York to Chicago - a distance of 800 miles. The plane travels at 200 MPH and makes one stop for 30 minutes. What is the pilot''s name? </content>\n			<img></img>\n			<a>Necessary information is missing.</a>\n			<b>You can''t tell from the question. </b>\n			<c>Both a and b. </c>\n			<d>You can tell from the question. </d>\n		</question>\n		<question number = ''5''>\n			<content>Extra </content>\n			<img></img>\n			<a>Hahaha</a>\n			<b>blablablabla </b>\n			<c>kukuku </c>\n			<d>lalababa </d>\n		</question>\n	</paper>', '2014-04-20', '2014-04-30'),
(6, 'CZ1005', 2, 'Can use calculator', 'MCQ', '<?xml version="1.0" encoding="UTF-8" ?>\n	<paper>\n		<id>0981095423</id>\n		<course>CZ2007</course>\n		<question number = ''1''>\n			<content>Lee''s parents emigrated from China. They have five children. The first four are named La, Le, Li, and Lo. What did they name the fifth? </content>\n			<img></img>\n			<a>Lala</a>\n			<b>Lu </b>\n			<c>Lee </c>\n			<d>Susan </d>\n		</question>\n		<question number = ''2''>\n			<content> A red house is made from red bricks, a blue house is made from blue bricks, a pink house is made from pink bricks, and a black house is made from black bricks. What is a greenhouse made from?</content>\n			<img></img>\n			<a>Wood </a>\n			<b>Green Bricks</b>\n			<c>Moss-covered stones</c>\n			<d>Glass </d>\n		</question>\n		<question number = ''3''>\n			<content>The Spanish Civil War, which began July 17 of 1936, was fought between:</content>\n			<img></img>\n			<a>Spain and Germany</a>\n			<b>Mexico and the United States</b>\n			<c>Spain and Italy</c>\n			<d>None of the above </d>\n		</question>\n		<question number = ''4''>\n			<content>You''re the pilot of an airplane that travels from New York to Chicago - a distance of 800 miles. The plane travels at 200 MPH and makes one stop for 30 minutes. What is the pilot''s name? </content>\n			<img></img>\n			<a>Necessary information is missing.</a>\n			<b>You can''t tell from the question. </b>\n			<c>Both a and b. </c>\n			<d>You can tell from the question. </d>\n		</question>\n		<question number = ''5''>\n			<content>Extra </content>\n			<img></img>\n			<a>Hahaha</a>\n			<b>blablablabla </b>\n			<c>kukuku </c>\n			<d>lalababa </d>\n		</question>\n	</paper>', '2014-05-14', '2014-05-31'),
(7, 'CZ2004', 2, 'Cannot use calculator', 'MCQ', '<?xml version="1.0" encoding="UTF-8" ?>\n	<paper>\n		<id>0981095423</id>\n		<course>CZ2007</course>\n		<question number = ''1''>\n			<content>Lee''s parents emigrated from China. They have five children. The first four are named La, Le, Li, and Lo. What did they name the fifth? </content>\n			<img></img>\n			<a>Lala</a>\n			<b>Lu </b>\n			<c>Lee </c>\n			<d>Susan </d>\n		</question>\n		<question number = ''2''>\n			<content> A red house is made from red bricks, a blue house is made from blue bricks, a pink house is made from pink bricks, and a black house is made from black bricks. What is a greenhouse made from?</content>\n			<img></img>\n			<a>Wood </a>\n			<b>Green Bricks</b>\n			<c>Moss-covered stones</c>\n			<d>Glass </d>\n		</question>\n		<question number = ''3''>\n			<content>The Spanish Civil War, which began July 17 of 1936, was fought between:</content>\n			<img></img>\n			<a>Spain and Germany</a>\n			<b>Mexico and the United States</b>\n			<c>Spain and Italy</c>\n			<d>None of the above </d>\n		</question>\n		<question number = ''4''>\n			<content>You''re the pilot of an airplane that travels from New York to Chicago - a distance of 800 miles. The plane travels at 200 MPH and makes one stop for 30 minutes. What is the pilot''s name? </content>\n			<img></img>\n			<a>Necessary information is missing.</a>\n			<b>You can''t tell from the question. </b>\n			<c>Both a and b. </c>\n			<d>You can tell from the question. </d>\n		</question>\n		<question number = ''5''>\n			<content>Extra </content>\n			<img></img>\n			<a>Hahaha</a>\n			<b>blablablabla </b>\n			<c>kukuku </c>\n			<d>lalababa </d>\n		</question>\n	</paper>', '2014-05-14', '2014-05-31'),
(8, 'CZ1006', 2, 'Can use calculator', 'MCQ', '<?xml version="1.0" encoding="UTF-8" ?>\n	<paper>\n		<id>0981095423</id>\n		<course>CZ2007</course>\n		<question number = ''1''>\n			<content>Lee''s parents emigrated from China. They have five children. The first four are named La, Le, Li, and Lo. What did they name the fifth? </content>\n			<img></img>\n			<a>Lala</a>\n			<b>Lu </b>\n			<c>Lee </c>\n			<d>Susan </d>\n		</question>\n		<question number = ''2''>\n			<content> A red house is made from red bricks, a blue house is made from blue bricks, a pink house is made from pink bricks, and a black house is made from black bricks. What is a greenhouse made from?</content>\n			<img></img>\n			<a>Wood </a>\n			<b>Green Bricks</b>\n			<c>Moss-covered stones</c>\n			<d>Glass </d>\n		</question>\n		<question number = ''3''>\n			<content>The Spanish Civil War, which began July 17 of 1936, was fought between:</content>\n			<img></img>\n			<a>Spain and Germany</a>\n			<b>Mexico and the United States</b>\n			<c>Spain and Italy</c>\n			<d>None of the above </d>\n		</question>\n		<question number = ''4''>\n			<content>You''re the pilot of an airplane that travels from New York to Chicago - a distance of 800 miles. The plane travels at 200 MPH and makes one stop for 30 minutes. What is the pilot''s name? </content>\n			<img></img>\n			<a>Necessary information is missing.</a>\n			<b>You can''t tell from the question. </b>\n			<c>Both a and b. </c>\n			<d>You can tell from the question. </d>\n		</question>\n		<question number = ''5''>\n			<content>Extra </content>\n			<img></img>\n			<a>Hahaha</a>\n			<b>blablablabla </b>\n			<c>kukuku </c>\n			<d>lalababa </d>\n		</question>\n	</paper>', '2014-05-14', '2014-05-31'),
(9, 'CZ1005', 2, 'Can use calculator', 'MCQ', '<?xml version="1.0" encoding="UTF-8" ?>\n	<paper>\n		<id>0981095423</id>\n		<course>CZ2007</course>\n		<question number = ''1''>\n			<content>Lee''s parents emigrated from China. They have five children. The first four are named La, Le, Li, and Lo. What did they name the fifth? </content>\n			<img></img>\n			<a>Lala</a>\n			<b>Lu </b>\n			<c>Lee </c>\n			<d>Susan </d>\n		</question>\n		<question number = ''2''>\n			<content> A red house is made from red bricks, a blue house is made from blue bricks, a pink house is made from pink bricks, and a black house is made from black bricks. What is a greenhouse made from?</content>\n			<img></img>\n			<a>Wood </a>\n			<b>Green Bricks</b>\n			<c>Moss-covered stones</c>\n			<d>Glass </d>\n		</question>\n		<question number = ''3''>\n			<content>The Spanish Civil War, which began July 17 of 1936, was fought between:</content>\n			<img></img>\n			<a>Spain and Germany</a>\n			<b>Mexico and the United States</b>\n			<c>Spain and Italy</c>\n			<d>None of the above </d>\n		</question>\n		<question number = ''4''>\n			<content>You''re the pilot of an airplane that travels from New York to Chicago - a distance of 800 miles. The plane travels at 200 MPH and makes one stop for 30 minutes. What is the pilot''s name? </content>\n			<img></img>\n			<a>Necessary information is missing.</a>\n			<b>You can''t tell from the question. </b>\n			<c>Both a and b. </c>\n			<d>You can tell from the question. </d>\n		</question>\n		<question number = ''5''>\n			<content>Extra </content>\n			<img></img>\n			<a>Hahaha</a>\n			<b>blablablabla </b>\n			<c>kukuku </c>\n			<d>lalababa </d>\n		</question>\n	</paper>', '2014-05-14', '2014-05-31'),
(10, 'CZ2006', 2, 'Cannot use calculator', 'MCQ', '<?xml version="1.0" encoding="UTF-8" ?>\n	<paper>\n		<id>0981095423</id>\n		<course>CZ2007</course>\n		<question number = ''1''>\n			<content>Lee''s parents emigrated from China. They have five children. The first four are named La, Le, Li, and Lo. What did they name the fifth? </content>\n			<img></img>\n			<a>Lala</a>\n			<b>Lu </b>\n			<c>Lee </c>\n			<d>Susan </d>\n		</question>\n		<question number = ''2''>\n			<content> A red house is made from red bricks, a blue house is made from blue bricks, a pink house is made from pink bricks, and a black house is made from black bricks. What is a greenhouse made from?</content>\n			<img></img>\n			<a>Wood </a>\n			<b>Green Bricks</b>\n			<c>Moss-covered stones</c>\n			<d>Glass </d>\n		</question>\n		<question number = ''3''>\n			<content>The Spanish Civil War, which began July 17 of 1936, was fought between:</content>\n			<img></img>\n			<a>Spain and Germany</a>\n			<b>Mexico and the United States</b>\n			<c>Spain and Italy</c>\n			<d>None of the above </d>\n		</question>\n		<question number = ''4''>\n			<content>You''re the pilot of an airplane that travels from New York to Chicago - a distance of 800 miles. The plane travels at 200 MPH and makes one stop for 30 minutes. What is the pilot''s name? </content>\n			<img></img>\n			<a>Necessary information is missing.</a>\n			<b>You can''t tell from the question. </b>\n			<c>Both a and b. </c>\n			<d>You can tell from the question. </d>\n		</question>\n		<question number = ''5''>\n			<content>Extra </content>\n			<img></img>\n			<a>Hahaha</a>\n			<b>blablablabla </b>\n			<c>kukuku </c>\n			<d>lalababa </d>\n		</question>\n	</paper>', '2013-04-20', '2013-04-30');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE IF NOT EXISTS `student` (
  `StudentID` varchar(20) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `University` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  PRIMARY KEY (`StudentID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`StudentID`, `Name`, `University`, `Email`) VALUES
('U1220001G', 'John', 'NTU', 'john001@e.ntu.edu.sg'),
('U1220009F', 'Darren', 'NTU', 'cyong1230@yahoo.com');

-- --------------------------------------------------------

--
-- Table structure for table `submit_exam`
--

CREATE TABLE IF NOT EXISTS `submit_exam` (
  `SubmitExamID` int(11) NOT NULL AUTO_INCREMENT,
  `StudentID` varchar(20) NOT NULL,
  `ExamID` int(11) NOT NULL,
  `Answer` text NOT NULL,
  PRIMARY KEY (`SubmitExamID`),
  KEY `StudentID` (`StudentID`),
  KEY `ExamID` (`ExamID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `submit_exam`
--

INSERT INTO `submit_exam` (`SubmitExamID`, `StudentID`, `ExamID`, `Answer`) VALUES
(1, 'U1220001G', 2, '<?xml version="1.0" encoding="UTF-8" ?>\n	<Submission>\n		<id>1</id>\n		<Question number = ''1''>\n			<Answer>A</Answer>\n		</Question>\n		<Question number = ''2''>\n			<Answer>D</Answer>\n		</Question>\n		<Question number = ''3''>\n			<Answer>C</Answer>\n		</Question>\n		<Question number = ''4''>\n			<Answer>C</Answer>\n		</Question>\n		<Question number = ''5''>\n			<Answer>C</Answer>\n		</Question>\n	</Submission>\n'),
(2, 'U1220001G', 5, '<?xml version="1.0" encoding="UTF-8" ?>\n	<Submission>\n		<id>6</id>\n		<Question number = ''1''>\n			<Answer>C</Answer>\n		</Question>\n		<Question number = ''2''>\n			<Answer>D</Answer>\n		</Question>\n		<Question number = ''3''>\n			<Answer>C</Answer>\n		</Question>\n		<Question number = ''4''>\n			<Answer>A</Answer>\n		</Question>\n		<Question number = ''5''>\n			<Answer>A</Answer>\n		</Question>\n	</Submission>\n');

-- --------------------------------------------------------

--
-- Table structure for table `take_course`
--

CREATE TABLE IF NOT EXISTS `take_course` (
  `StudentID` varchar(20) NOT NULL,
  `CourseCode` varchar(10) NOT NULL,
  `NoOfRetake` int(11) NOT NULL,
  `Complete` tinyint(1) NOT NULL,
  PRIMARY KEY (`StudentID`,`CourseCode`),
  KEY `StudentID` (`StudentID`),
  KEY `CourseCode` (`CourseCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `take_course`
--

INSERT INTO `take_course` (`StudentID`, `CourseCode`, `NoOfRetake`, `Complete`) VALUES
('U1220001G', 'CZ1005', 0, 0),
('U1220001G', 'CZ1006', 1, 0),
('U1220001G', 'CZ2001', 0, 1),
('U1220001G', 'CZ2003', 0, 0),
('U1220001G', 'CZ2004', 0, 0),
('U1220001G', 'CZ2006', 0, 1);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `exam`
--
ALTER TABLE `exam`
  ADD CONSTRAINT `exam_ibfk_1` FOREIGN KEY (`CourseCode`) REFERENCES `course` (`CourseCode`) ON UPDATE CASCADE;

--
-- Constraints for table `submit_exam`
--
ALTER TABLE `submit_exam`
  ADD CONSTRAINT `submit_exam_ibfk_1` FOREIGN KEY (`StudentID`) REFERENCES `student` (`StudentID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `submit_exam_ibfk_2` FOREIGN KEY (`ExamID`) REFERENCES `exam` (`ExamID`) ON UPDATE CASCADE;

--
-- Constraints for table `take_course`
--
ALTER TABLE `take_course`
  ADD CONSTRAINT `take_course_ibfk_1` FOREIGN KEY (`StudentID`) REFERENCES `student` (`StudentID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `take_course_ibfk_2` FOREIGN KEY (`CourseCode`) REFERENCES `course` (`CourseCode`) ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
