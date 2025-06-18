CREATE DATABASE  IF NOT EXISTS `webserver` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `webserver`;
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 13.209.7.8    Database: webserver
-- ------------------------------------------------------
-- Server version	8.0.42-0ubuntu0.24.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Comment`
--

DROP TABLE IF EXISTS `Comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Comment` (
  `comment_id` int NOT NULL AUTO_INCREMENT COMMENT '댓글 ID',
  `post_id` int NOT NULL COMMENT '게시물 아이디 (외래키)',
  `user_id` int NOT NULL COMMENT '사용자 ID (외래키)',
  `content` text,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성 일시',
  `good_num` int NOT NULL DEFAULT '0' COMMENT '좋아요 개수',
  `bad_num` int NOT NULL DEFAULT '0' COMMENT '싫어요 개수',
  `parent_comment_id` int DEFAULT NULL COMMENT '부모 댓글 ID (대댓글일 경우 연결)',
  PRIMARY KEY (`comment_id`),
  KEY `post_id` (`post_id`),
  KEY `user_id` (`user_id`),
  KEY `fk_parent_comment` (`parent_comment_id`),
  CONSTRAINT `Comment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`),
  CONSTRAINT `Comment_ibfk_3` FOREIGN KEY (`parent_comment_id`) REFERENCES `Comment` (`comment_id`),
  CONSTRAINT `fk_parent_comment` FOREIGN KEY (`parent_comment_id`) REFERENCES `Comment` (`comment_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Comment`
--

LOCK TABLES `Comment` WRITE;
/*!40000 ALTER TABLE `Comment` DISABLE KEYS */;
INSERT INTO `Comment` VALUES (1,1,2,'우와 지려요','2025-06-10 18:25:54',3,5,NULL),(2,1,2,'굿','2025-06-10 19:31:42',1,0,NULL),(3,1,2,'이거 좋네요','2025-06-11 15:19:50',1,0,NULL),(5,14,2,'있겠냐요','2025-06-12 08:50:15',1,0,NULL),(6,5,1,'배고파요','2025-06-13 14:44:01',1,0,NULL),(7,15,2,'하윙','2025-06-14 15:09:21',0,0,NULL);
/*!40000 ALTER TABLE `Comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CommentReaction`
--

DROP TABLE IF EXISTS `CommentReaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CommentReaction` (
  `commentReaction_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `comment_id` int NOT NULL,
  `reactionType` int DEFAULT '0',
  PRIMARY KEY (`commentReaction_id`),
  UNIQUE KEY `user_id` (`user_id`,`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CommentReaction`
--

LOCK TABLES `CommentReaction` WRITE;
/*!40000 ALTER TABLE `CommentReaction` DISABLE KEYS */;
INSERT INTO `CommentReaction` VALUES (1,2,1,2),(2,2,2,1),(3,3,1,2),(4,2,3,1),(5,2,4,2),(6,1,6,1),(7,2,5,1);
/*!40000 ALTER TABLE `CommentReaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DailyBoxOffice`
--

DROP TABLE IF EXISTS `DailyBoxOffice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DailyBoxOffice` (
  `dailyBoxOffice_id` int NOT NULL AUTO_INCREMENT COMMENT '일일 박스오피스 아이디',
  `movie_rank` int NOT NULL,
  `target_date` varchar(30) DEFAULT NULL,
  `movie_title` varchar(150) NOT NULL,
  PRIMARY KEY (`dailyBoxOffice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DailyBoxOffice`
--

LOCK TABLES `DailyBoxOffice` WRITE;
/*!40000 ALTER TABLE `DailyBoxOffice` DISABLE KEYS */;
INSERT INTO `DailyBoxOffice` VALUES (75,1,'2025-06-06','드래곤 길들이기'),(76,2,'2025-05-30','하이파이브'),(77,3,'2025-05-17','미션 임파서블: 파이널 레코닝'),(78,4,'2025-06-02','신명'),(79,5,'2025-05-21','릴로 & 스티치'),(80,6,'2025-05-30','소주전쟁'),(81,7,'2025-06-06','브링 허 백'),(82,8,NULL,'알사탕'),(83,9,'2025-06-11','인피니트 15주년 콘서트 리미티드 에디션 더 무비'),(84,10,'2025-05-29','극장판 프로젝트 세카이 부서진 세카이와 전해지지 않는 미쿠의 노래');
/*!40000 ALTER TABLE `DailyBoxOffice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Movie`
--

DROP TABLE IF EXISTS `Movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Movie` (
  `movie_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `genre` varchar(50) DEFAULT NULL,
  `poster_url` varchar(255) DEFAULT NULL,
  `review_point` float(10,2) DEFAULT NULL,
  `description` text,
  `duration` int DEFAULT NULL,
  `directors` varchar(255) DEFAULT NULL,
  `actors` text,
  `target_date` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`movie_id`)
) ENGINE=InnoDB AUTO_INCREMENT=609 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Movie`
--

LOCK TABLES `Movie` WRITE;
/*!40000 ALTER TABLE `Movie` DISABLE KEYS */;
/*!40000 ALTER TABLE `Movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Payment`
--

DROP TABLE IF EXISTS `Payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Payment` (
  `payment_id` int NOT NULL AUTO_INCREMENT COMMENT '결제 정보 아이디',
  `ticket_id` int NOT NULL COMMENT '예매 아이디 (외래키)',
  `amount` int NOT NULL DEFAULT '0' COMMENT '결제 금액',
  `method` varchar(30) NOT NULL COMMENT '결제 수단 (카드, 현금, 계좌이체)',
  `paid_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '결제 일시',
  PRIMARY KEY (`payment_id`),
  KEY `ticket_id` (`ticket_id`),
  CONSTRAINT `Payment_ibfk_1` FOREIGN KEY (`ticket_id`) REFERENCES `Ticket` (`ticket_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Payment`
--

LOCK TABLES `Payment` WRITE;
/*!40000 ALTER TABLE `Payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `Payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Post`
--

DROP TABLE IF EXISTS `Post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Post` (
  `post_id` int NOT NULL AUTO_INCREMENT COMMENT '게시물 아이디',
  `user_id` int NOT NULL COMMENT '사용자 고유 ID (외래키)',
  `movie_id` int DEFAULT NULL COMMENT '영화 객체들을 구분해주는 아이디 (외래키)',
  `title` varchar(100) NOT NULL COMMENT '게시물 제목',
  `content` text NOT NULL COMMENT '게시물 작성 내용',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '게시물 작성 일시',
  `is_watched` tinyint(1) NOT NULL DEFAULT '0' COMMENT '해당 영화 관람 여부',
  `board_type` varchar(10) NOT NULL DEFAULT 'free' COMMENT '게시판 구분 (자유/영화)',
  `view_count` int NOT NULL DEFAULT '0' COMMENT '게시물 조회수',
  `updated_at` datetime DEFAULT NULL COMMENT '수정된 시간',
  `image_path` varchar(255) DEFAULT NULL COMMENT '이미지 파일 경로 (영화게시판 전용)',
  PRIMARY KEY (`post_id`),
  KEY `user_id` (`user_id`),
  KEY `movie_id` (`movie_id`),
  CONSTRAINT `Post_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`),
  CONSTRAINT `Post_ibfk_2` FOREIGN KEY (`movie_id`) REFERENCES `Movie` (`movie_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Post`
--

LOCK TABLES `Post` WRITE;
/*!40000 ALTER TABLE `Post` DISABLE KEYS */;
INSERT INTO `Post` VALUES (1,2,NULL,'우와 드디어?','반갑다','2025-06-11 00:45:32',0,'free',91,NULL,NULL),(2,2,NULL,'아침밥 ','뀨','2025-06-11 00:46:38',0,'movie',11,NULL,'4f1bb929-d8a4-4f97-9a9b-eed3821d77cf_스크린샷 2024-11-02 040137.png'),(3,2,NULL,'아침은 김치볶음밥','추천할게','2025-06-12 01:08:37',0,'free',3,NULL,NULL),(4,2,NULL,'저녁은 육회에 소주','지리죠?','2025-06-12 01:35:15',0,'free',0,NULL,NULL),(5,2,NULL,'꺼억 오늘 저녁','맛있게따','2025-06-12 01:35:44',0,'free',4,NULL,NULL),(6,2,NULL,'저 오늘 영화관 가는데 다들 보러 가시나요?','ㅈㄱㄴ','2025-06-12 01:35:57',0,'free',0,NULL,NULL),(8,2,NULL,'세상 별에별게 많은 ','프젝','2025-06-12 01:36:25',0,'free',0,NULL,NULL),(9,2,NULL,'다들 뭐하세요','배고픈뎀','2025-06-12 01:37:09',0,'free',0,NULL,NULL),(10,2,NULL,'요즘 팝콘 시세 왜이램?','ㅈㄱㄴ','2025-06-12 01:37:18',0,'free',0,NULL,NULL),(11,2,NULL,'인생 재밌따','깔깔','2025-06-12 01:37:28',0,'free',0,NULL,NULL),(12,2,NULL,'그냥 웃으면 복이 온대서','웃자','2025-06-12 01:37:39',0,'free',11,NULL,NULL),(13,2,NULL,'두번째 영화에요','체리영화','2025-06-12 03:10:21',0,'movie',3,NULL,NULL),(14,2,NULL,'컴퓨터공학과','컴퓨터공학과 손진석 여자친구 구해요 ~! 만관부 ','2025-06-12 17:49:49',0,'free',51,NULL,NULL),(15,1,NULL,'안녕','안녕하세요','2025-06-14 23:17:51',0,'movie',4,NULL,NULL),(16,2,NULL,'우리 모두 정신차려 이 영화를 봐야해요','꼭 보세요','2025-06-16 20:14:16',0,'movie',11,NULL,NULL),(17,2,NULL,'우리 모두 정신차려 이 영화를 봐야해요!!!','ㅈㄱㄴ','2025-06-16 20:22:03',0,'movie',4,NULL,NULL),(18,2,NULL,'꺼억 오늘 저녁','잼','2025-06-16 20:29:48',1,'movie',5,NULL,NULL),(19,2,NULL,'안녕하세요','타조가 알 낳는','2025-06-16 20:36:13',1,'movie',9,NULL,NULL),(20,2,NULL,'안녕하세요','안녕하지못해','2025-06-16 21:31:17',1,'movie',3,NULL,NULL),(21,2,NULL,'안녕하세요','와','2025-06-17 11:04:56',0,'movie',4,NULL,NULL),(22,2,NULL,'안꼬일거에요','안녕','2025-06-17 17:02:55',1,'movie',7,NULL,'KakaoTalk_20250212_011054009_02.jpg'),(23,2,NULL,'제발','되나요 ?','2025-06-17 17:14:52',1,'movie',2,NULL,'KakaoTalk_20250212_011242183.jpg'),(24,2,NULL,'제발','되나요 ?','2025-06-17 17:14:53',1,'movie',3,NULL,'KakaoTalk_20250212_011242183.jpg'),(25,2,NULL,'오 캡틴 오마이 캡틴!','키팅 선생님 같은 선생님을 만났어야했는데 진짜 명작입니다 ㅠㅠㅠ','2025-06-18 18:31:10',1,'movie',2,NULL,'스크린샷 2025-06-18 183024.png'),(27,2,NULL,'카르페디엠 멋지지 않나요?','난 카르페디엠으로~','2025-06-18 18:43:12',1,'movie',2,NULL,'KakaoTalk_20250212_011054009_01.jpg');
/*!40000 ALTER TABLE `Post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Review`
--

DROP TABLE IF EXISTS `Review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Review` (
  `review_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `movie_id` int NOT NULL,
  `context` text NOT NULL COMMENT '사용자가 작성한 리뷰글',
  `rating` int NOT NULL COMMENT '리뷰 별점 1~5',
  `review_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '리뷰 단 시간',
  `like_count` int NOT NULL DEFAULT '0',
  `unlike_count` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`review_id`),
  KEY `user_id` (`user_id`),
  KEY `movie_id` (`movie_id`),
  CONSTRAINT `Review_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`),
  CONSTRAINT `Review_ibfk_2` FOREIGN KEY (`movie_id`) REFERENCES `Movie` (`movie_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Review`
--

LOCK TABLES `Review` WRITE;
/*!40000 ALTER TABLE `Review` DISABLE KEYS */;
INSERT INTO `Review` VALUES (20,1,432,'재미없어요',1,'2025-06-10 06:50:01',1,0),(21,1,432,'재밌는데요',5,'2025-06-10 06:50:09',0,1),(22,1,433,'ㅇㅁㄴㄻㄴㅇ',4,'2025-06-10 06:50:17',0,1),(23,2,431,'뀽 좋아용',4,'2025-06-10 08:53:42',1,1),(24,1,432,'진짜 재밌어요',4,'2025-06-14 14:20:14',0,0),(27,2,432,'재밌었다.. 맞나?',5,'2025-06-16 17:21:28',0,0),(28,2,431,'와 지려요',5,'2025-06-17 02:03:57',1,0),(29,2,431,'나도 드래곤 키울랭 (뀨)',5,'2025-06-17 03:59:02',0,0);
/*!40000 ALTER TABLE `Review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ReviewReaction`
--

DROP TABLE IF EXISTS `ReviewReaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ReviewReaction` (
  `reviewReaction_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `review_id` int NOT NULL,
  `reactionType` int NOT NULL DEFAULT '0' COMMENT '0: 무선택 / 1: 좋아요 / 2: 싫어요',
  PRIMARY KEY (`reviewReaction_id`),
  UNIQUE KEY `user_id` (`user_id`,`review_id`),
  KEY `review_id` (`review_id`),
  CONSTRAINT `ReviewReaction_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`),
  CONSTRAINT `ReviewReaction_ibfk_2` FOREIGN KEY (`review_id`) REFERENCES `Review` (`review_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ReviewReaction`
--

LOCK TABLES `ReviewReaction` WRITE;
/*!40000 ALTER TABLE `ReviewReaction` DISABLE KEYS */;
INSERT INTO `ReviewReaction` VALUES (23,1,20,0),(24,1,21,0),(25,1,22,2),(26,2,23,1),(28,1,24,0),(30,2,20,1),(31,2,21,2),(34,2,27,0),(35,2,28,1),(36,2,29,0),(38,1,23,2);
/*!40000 ALTER TABLE `ReviewReaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Room`
--

DROP TABLE IF EXISTS `Room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Room` (
  `room_id` int NOT NULL AUTO_INCREMENT,
  `theater_id` int NOT NULL,
  `room_number` int NOT NULL COMMENT '상영관 번호 (예: 1관, 2관)',
  `capacity` int NOT NULL COMMENT '좌석 수',
  PRIMARY KEY (`room_id`),
  KEY `theater_id` (`theater_id`),
  CONSTRAINT `Room_ibfk_1` FOREIGN KEY (`theater_id`) REFERENCES `Theater` (`theater_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Room`
--

LOCK TABLES `Room` WRITE;
/*!40000 ALTER TABLE `Room` DISABLE KEYS */;
/*!40000 ALTER TABLE `Room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Screening`
--

DROP TABLE IF EXISTS `Screening`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Screening` (
  `screening_id` int NOT NULL AUTO_INCREMENT,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `theater_id` int NOT NULL,
  `movie_id` int NOT NULL,
  `room_number` int NOT NULL,
  `language` varchar(20) DEFAULT 'Korean',
  `format` varchar(20) DEFAULT '2D',
  `screening_date` date NOT NULL COMMENT '상영 날짜',
  PRIMARY KEY (`screening_id`),
  KEY `theater_id` (`theater_id`),
  KEY `movie_id` (`movie_id`),
  CONSTRAINT `Screening_ibfk_1` FOREIGN KEY (`theater_id`) REFERENCES `Theater` (`theater_id`),
  CONSTRAINT `Screening_ibfk_2` FOREIGN KEY (`movie_id`) REFERENCES `Movie` (`movie_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Screening`
--

LOCK TABLES `Screening` WRITE;
/*!40000 ALTER TABLE `Screening` DISABLE KEYS */;
INSERT INTO `Screening` VALUES (1,'2025-06-11 10:00:00','2025-06-11 11:45:00',1,433,1,'Korean','2D','2025-06-11'),(2,'2025-06-11 13:00:00','2025-06-11 14:35:00',2,432,1,'Korean','2D','2025-06-11'),(3,'2025-06-11 15:00:00','2025-06-11 17:30:00',3,431,2,'Korean','IMAX','2025-06-11');
/*!40000 ALTER TABLE `Screening` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Seat`
--

DROP TABLE IF EXISTS `Seat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Seat` (
  `seat_id` int NOT NULL AUTO_INCREMENT COMMENT '좌석 아이디',
  `screening_id` int NOT NULL COMMENT '상영 아이디',
  `row_num` int NOT NULL COMMENT '행 번호',
  `col_num` int NOT NULL COMMENT '열 번호',
  `is_reserved` tinyint(1) NOT NULL DEFAULT '0' COMMENT '좌석 예약 여부',
  PRIMARY KEY (`seat_id`),
  KEY `screening_id` (`screening_id`),
  CONSTRAINT `Seat_ibfk_1` FOREIGN KEY (`screening_id`) REFERENCES `Screening` (`screening_id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Seat`
--

LOCK TABLES `Seat` WRITE;
/*!40000 ALTER TABLE `Seat` DISABLE KEYS */;
INSERT INTO `Seat` VALUES (1,1,3,1,0),(2,1,2,1,0),(3,1,1,1,1),(4,1,3,2,0),(5,1,2,2,0),(6,1,1,2,0),(7,1,3,3,0),(8,1,2,3,0),(9,1,1,3,0),(10,1,3,4,0),(11,1,2,4,0),(12,1,1,4,0),(13,1,3,5,0),(14,1,2,5,0),(15,1,1,5,0),(16,2,3,1,0),(17,2,2,1,0),(18,2,1,1,0),(19,2,3,2,0),(20,2,2,2,0),(21,2,1,2,0),(22,2,3,3,0),(23,2,2,3,0),(24,2,1,3,0),(25,2,3,4,0),(26,2,2,4,0),(27,2,1,4,0),(28,2,3,5,0),(29,2,2,5,0),(30,2,1,5,0),(31,3,3,1,0),(32,3,2,1,0),(33,3,1,1,0),(34,3,3,2,0),(35,3,2,2,0),(36,3,1,2,0),(37,3,3,3,0),(38,3,2,3,0),(39,3,1,3,0),(40,3,3,4,0),(41,3,2,4,0),(42,3,1,4,0),(43,3,3,5,0),(44,3,2,5,0),(45,3,1,5,0);
/*!40000 ALTER TABLE `Seat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Theater`
--

DROP TABLE IF EXISTS `Theater`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Theater` (
  `theater_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`theater_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Theater`
--

LOCK TABLES `Theater` WRITE;
/*!40000 ALTER TABLE `Theater` DISABLE KEYS */;
INSERT INTO `Theater` VALUES (1,'서경대점','서울 서경로'),(2,'정릉점','서울 정릉'),(3,'길음점','서울 길음');
/*!40000 ALTER TABLE `Theater` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Ticket`
--

DROP TABLE IF EXISTS `Ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Ticket` (
  `ticket_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `screening_id` int NOT NULL,
  `seat_id` int NOT NULL,
  PRIMARY KEY (`ticket_id`),
  KEY `user_id` (`user_id`),
  KEY `screening_id` (`screening_id`),
  KEY `seat_id` (`seat_id`),
  CONSTRAINT `Ticket_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`),
  CONSTRAINT `Ticket_ibfk_2` FOREIGN KEY (`screening_id`) REFERENCES `Screening` (`screening_id`),
  CONSTRAINT `Ticket_ibfk_3` FOREIGN KEY (`seat_id`) REFERENCES `Seat` (`seat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Ticket`
--

LOCK TABLES `Ticket` WRITE;
/*!40000 ALTER TABLE `Ticket` DISABLE KEYS */;
INSERT INTO `Ticket` VALUES (4,1,1,3);
/*!40000 ALTER TABLE `Ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(200) NOT NULL,
  `password` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `class` varchar(10) NOT NULL DEFAULT 'BASIC',
  `join_date` date DEFAULT (curdate()),
  `birth` date NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `profile_image` varchar(255) DEFAULT '기본프로필.png',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `unique_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'nahjjun1125@naver.com','jagDx2sZYVjhuDsXy/twXllsHmc9g3ukOjixj7ehG98=','나형준','BASIC','2025-06-10','2002-11-25',NULL,'nahjjun1125_ISBN1235_1749538256507.jpg'),(2,'ockda0423@naver.com','4QKn3sqI8+gb1slI/20HyMXSoCpErdVLhb25pvKJJ+0=','임다현','BASIC','2025-06-10','2004-04-23','010-6433-1385','ockda0423_KakaoTalk_20250212_011054009_01_1750125941322.jpg'),(3,'wjdghhks7767@naver.com','FK+2ddDvljhtLF/WJ9hD+lRBHi50qrLoloTYuI9+20g=','김정환','BASIC','2025-06-11','2002-01-27',NULL,'wjdghhks7767_정신체리_1749646550571.jpeg'),(5,'ockda0423@skuniv.ac.kr','4QKn3sqI8+gb1slI/20HyMXSoCpErdVLhb25pvKJJ+0=','임다현','BASIC','2025-06-18','2004-04-23',NULL,'기본프로필.png');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-18 21:34:03
