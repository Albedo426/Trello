-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Anamakine: 127.0.0.1
-- Üretim Zamanı: 20 Oca 2021, 12:25:12
-- Sunucu sürümü: 10.4.13-MariaDB
-- PHP Sürümü: 7.4.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `ysproject`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `categories`
--

CREATE TABLE `categories` (
  `categoryId` int(11) NOT NULL,
  `storiesId` int(11) NOT NULL,
  `categoryType` int(11) NOT NULL,
  `FinishDate` text COLLATE utf8mb4_turkish_ci NOT NULL,
  `categoryText` text COLLATE utf8mb4_turkish_ci NOT NULL,
  `categoryTitle` text COLLATE utf8mb4_turkish_ci NOT NULL,
  `categoryResUId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

--
-- Tablo döküm verisi `categories`
--

INSERT INTO `categories` (`categoryId`, `storiesId`, `categoryType`, `FinishDate`, `categoryText`, `categoryTitle`, `categoryResUId`) VALUES
(1, 1, 2, '01.02.2020', 'categoryText1', 'categoryTitle1', 1),
(2, 1, 3, '02.02.2021', 'categoryText2', 'categoryTitle2', 2),
(3, 2, 2, '01.02.2020', 'categoryText31', 'categoryTitle3', 1),
(4, 2, 1, '02.02.2021', 'categoryText4', 'categoryTitle4', 2),
(7, 1, 2, '10.10.2025', 'testtext1', 'testtitle1', 1),
(8, 1, 1, '10.10.2025', 'testtext2', 'testtitle1', 2),
(9, 1, 3, '20.20.2020', 'test', 'test', 2),
(10, 1, 1, '20.20.2000', 'nnn', 'bb', 1),
(11, 1, 1, '10.09.2020', 'hhj', 'nnk', 1),
(12, 1, 2, '10.10.2025', 'asd', 'asd', 2),
(13, 10, 1, '11.11.2020', 'd', 'd', 1),
(14, 4, 1, '10.10.2020', 'test', 'test', 1);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `stories`
--

CREATE TABLE `stories` (
  `storyId` int(11) NOT NULL,
  `storyCraUId` int(11) NOT NULL,
  `storyTitle` text COLLATE utf8mb4_turkish_ci NOT NULL,
  `storyText` text COLLATE utf8mb4_turkish_ci NOT NULL,
  `storyCraDate` text COLLATE utf8mb4_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

--
-- Tablo döküm verisi `stories`
--

INSERT INTO `stories` (`storyId`, `storyCraUId`, `storyTitle`, `storyText`, `storyCraDate`) VALUES
(1, 1, 'storyTitle1', 'storyText1', '10.12.2021'),
(2, 2, 'storyTitle2', 'storyText2', '10.10.2023'),
(3, 2, 'storyTitle2', 'storyText3', '10.10.2023'),
(4, 2, 'storytest', 'hejeurjr', '02.30.2021'),
(9, 2, 'Stori içeriğini girin', 'Stori başlığı yazın', '04.44.2021'),
(10, 2, 'Stori içeriğini girin', 'Stori başlığı yazın', '04.01.2021'),
(11, 2, 'Stori içeriğini girin', 'Stori başlığı yazın', '04.01.2021'),
(12, 2, 'Stori içeriğini girin', 'Stori başlığı yazın', '04.01.2021');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `users`
--

CREATE TABLE `users` (
  `userId` int(11) NOT NULL,
  `userMail` text COLLATE utf8mb4_turkish_ci NOT NULL,
  `userPass` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

--
-- Tablo döküm verisi `users`
--

INSERT INTO `users` (`userId`, `userMail`, `userPass`) VALUES
(1, 'test@tes.com', 123),
(2, 'aa@aaa.com', 123);

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`categoryId`),
  ADD KEY `storiesId` (`storiesId`),
  ADD KEY `categoryResUId` (`categoryResUId`);

--
-- Tablo için indeksler `stories`
--
ALTER TABLE `stories`
  ADD PRIMARY KEY (`storyId`),
  ADD KEY `storyCraUId` (`storyCraUId`);

--
-- Tablo için indeksler `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userId`),
  ADD UNIQUE KEY `userMail` (`userMail`) USING HASH;

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `categories`
--
ALTER TABLE `categories`
  MODIFY `categoryId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Tablo için AUTO_INCREMENT değeri `stories`
--
ALTER TABLE `stories`
  MODIFY `storyId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Tablo için AUTO_INCREMENT değeri `users`
--
ALTER TABLE `users`
  MODIFY `userId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Dökümü yapılmış tablolar için kısıtlamalar
--

--
-- Tablo kısıtlamaları `categories`
--
ALTER TABLE `categories`
  ADD CONSTRAINT `categories_ibfk_1` FOREIGN KEY (`storiesId`) REFERENCES `stories` (`storyId`),
  ADD CONSTRAINT `categories_ibfk_2` FOREIGN KEY (`categoryResUId`) REFERENCES `users` (`userId`);

--
-- Tablo kısıtlamaları `stories`
--
ALTER TABLE `stories`
  ADD CONSTRAINT `stories_ibfk_1` FOREIGN KEY (`storyCraUId`) REFERENCES `users` (`userId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
