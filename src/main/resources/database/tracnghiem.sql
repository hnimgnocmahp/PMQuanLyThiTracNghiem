-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 13, 2025 at 11:00 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tracnghiem`
--

-- --------------------------------------------------------

--
-- Table structure for table `answers`
--

CREATE TABLE `answers` (
                           `awID` int(11) NOT NULL,
                           `qID` int(11) NOT NULL COMMENT 'id câu hỏi',
                           `awContent` text NOT NULL,
                           `awPictures` text NOT NULL COMMENT 'url ảnh',
                           `isRight` tinyint(4) NOT NULL COMMENT '1: đúng; 0: Sai',
                           `awStatus` tinyint(4) NOT NULL COMMENT '1: active; 0: hidden'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `exams`
--

CREATE TABLE `exams` (
                         `testCode` varchar(20) NOT NULL,
                         `exOrder` varchar(1) NOT NULL COMMENT 'A;B;C;D;E;F',
                         `exCode` varchar(20) NOT NULL COMMENT '=testCode + exOrder',
                         `ex_quesIDs` text NOT NULL COMMENT 'mảng các id câu hỏi'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `logs`
--

CREATE TABLE `logs` (
                        `logID` int(11) NOT NULL,
                        `logContent` text NOT NULL,
                        `logUserID` int(11) NOT NULL,
                        `logExCode` varchar(20) NOT NULL,
                        `logDate` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE `questions` (
                             `qID` int(11) NOT NULL,
                             `qContent` text NOT NULL COMMENT 'nội dung câu hỏi',
                             `qPictures` text NOT NULL COMMENT 'url hình đính kèm',
                             `qTopicID` int(11) NOT NULL,
                             `qLevel` varchar(10) NOT NULL COMMENT 'easy, meidum, diff',
                             `qStatus` tinyint(4) NOT NULL COMMENT '1: active; 0: hidden'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `result`
--

CREATE TABLE `result` (
                          `rs_num` tinyint(11) NOT NULL,
                          `userID` int(11) NOT NULL,
                          `exCode` varchar(20) NOT NULL,
                          `rs_anwsers` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'các đáp án đã chọn' CHECK (json_valid(`rs_anwsers`)),
                          `rs_mark` decimal(10,0) NOT NULL,
                          `rs_date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `test`
--

CREATE TABLE `test` (
                        `testID` int(11) NOT NULL,
                        `testCode` varchar(20) NOT NULL COMMENT 'mã bài thi',
                        `testTilte` text NOT NULL,
                        `testTime` int(11) NOT NULL COMMENT 'thời gian làm bài (phút)',
                        `tpID` int(11) NOT NULL COMMENT 'id của chủ đề/bài học',
                        `num_easy` int(11) NOT NULL COMMENT 'số lượng câu dễ',
                        `num_medium` int(11) NOT NULL COMMENT 'số lượng câu trung bình',
                        `num_diff` int(11) NOT NULL COMMENT 'số lượng câu khó',
                        `testLimit` tinyint(4) NOT NULL COMMENT 'số lần thi',
                        `testDate` date NOT NULL,
                        `testStatus` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `topics`
--

CREATE TABLE `topics` (
                          `tpID` int(11) NOT NULL,
                          `tpTitle` text NOT NULL COMMENT 'tên topic',
                          `tpParent` int(11) NOT NULL COMMENT 'id của topic cha',
                          `tpStatus` tinyint(4) NOT NULL COMMENT '1: active; 0: hidden'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
                         `userID` int(11) NOT NULL,
                         `userName` varchar(40) NOT NULL COMMENT 'login = userName',
                         `userEmail` varchar(20) NOT NULL,
                         `userPassword` varchar(40) NOT NULL COMMENT 'mã hóa dùng md5',
                         `userFullName` varchar(40) NOT NULL,
                         `isAdmin` tinyint(4) NOT NULL COMMENT '1: admin; 0: user'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `answers`
--
ALTER TABLE `answers`
    ADD PRIMARY KEY (`awID`),
  ADD KEY `qID` (`qID`);

--
-- Indexes for table `exams`
--
ALTER TABLE `exams`
    ADD PRIMARY KEY (`testCode`,`exOrder`),
  ADD KEY `testCode` (`testCode`),
  ADD KEY `exCode` (`exCode`);

--
-- Indexes for table `logs`
--
ALTER TABLE `logs`
    ADD PRIMARY KEY (`logID`),
  ADD KEY `logUserID` (`logUserID`);

--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
    ADD PRIMARY KEY (`qID`),
  ADD KEY `qTopicID` (`qTopicID`);

--
-- Indexes for table `result`
--
ALTER TABLE `result`
    ADD PRIMARY KEY (`rs_num`,`userID`,`exCode`),
  ADD KEY `userID` (`userID`),
  ADD KEY `exID` (`exCode`),
  ADD KEY `exCode` (`exCode`);

--
-- Indexes for table `test`
--
ALTER TABLE `test`
    ADD PRIMARY KEY (`testID`,`tpID`) USING BTREE,
  ADD KEY `tpID` (`tpID`),
  ADD KEY `testCode` (`testCode`);

ALTER TABLE `result`
    ADD CONSTRAINT `result_ibfk_2`
        FOREIGN KEY (`exCode`) REFERENCES `exams` (`exCode`);

--
-- Indexes for table `topics`
--
ALTER TABLE `topics`
    ADD PRIMARY KEY (`tpID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
    ADD PRIMARY KEY (`userID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `answers`
--
ALTER TABLE `answers`
    MODIFY `awID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `logs`
--
ALTER TABLE `logs`
    MODIFY `logID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `questions`
--
ALTER TABLE `questions`
    MODIFY `qID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `topics`
--
ALTER TABLE `topics`
    MODIFY `tpID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
    MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `test`
    MODIFY `testID` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `answers`
--
ALTER TABLE `answers`
    ADD CONSTRAINT `answers_ibfk_1` FOREIGN KEY (`qID`) REFERENCES `questions` (`qID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `exams`
--


--
-- Constraints for table `logs`
--
ALTER TABLE `logs`
    ADD CONSTRAINT `logs_ibfk_1` FOREIGN KEY (`logUserID`) REFERENCES `users` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `questions`
--
ALTER TABLE `questions`
    ADD CONSTRAINT `questions_ibfk_1` FOREIGN KEY (`qTopicID`) REFERENCES `topics` (`tpID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `result`
--
ALTER TABLE `result`
    ADD CONSTRAINT `result_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `test`
--
ALTER TABLE `test`
    ADD CONSTRAINT `test_ibfk_1` FOREIGN KEY (`tpID`) REFERENCES `topics` (`tpID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `test_ibfk_2` FOREIGN KEY (`testCode`) REFERENCES `exams` (`testCode`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
