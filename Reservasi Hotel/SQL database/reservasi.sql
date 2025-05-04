-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 21, 2025 at 08:33 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.1.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `reservasi`
--

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `id` int(11) NOT NULL,
  `kode_reservasi` text NOT NULL,
  `room` text NOT NULL,
  `nik_passport` bigint(15) NOT NULL,
  `total_malam` int(2) NOT NULL,
  `tanggal_masuk` timestamp NULL DEFAULT NULL,
  `tanggal_keluar` timestamp NULL DEFAULT NULL,
  `payment` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`id`, `kode_reservasi`, `room`, `nik_passport`, `total_malam`, `tanggal_masuk`, `tanggal_keluar`, `payment`) VALUES
(7, 'RSV-HTLD28PKA', '3', 12345678, 4, '2025-04-21 17:00:00', '2025-04-25 17:00:00', 'SUKSES'),
(8, 'RSV-HTLL3JBWX', '5', 11111111, 3, '2025-04-25 02:57:48', '2025-04-28 02:57:48', 'SUKSES'),
(9, 'RSV-HTLYBPH6M', '3', 22222222, 3, '2025-01-02 17:00:00', '2025-01-05 17:00:00', 'SUKSES');

-- --------------------------------------------------------

--
-- Table structure for table `diskon`
--

CREATE TABLE `diskon` (
  `id` int(11) NOT NULL,
  `kode` varchar(20) NOT NULL,
  `discount` int(2) NOT NULL,
  `max` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `diskon`
--

INSERT INTO `diskon` (`id`, `kode`, `discount`, `max`) VALUES
(2, 'LEBARAN2025', 10, 20),
(3, 'HOTELOYO', 10, 100),
(4, 'hhhhh', 70, 8);

-- --------------------------------------------------------

--
-- Table structure for table `pembayaran`
--

CREATE TABLE `pembayaran` (
  `id` int(11) NOT NULL,
  `kode_reservasi` text NOT NULL,
  `diskon` int(11) DEFAULT NULL,
  `jenis_bayar` text NOT NULL,
  `waktu_bayar` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pembayaran`
--

INSERT INTO `pembayaran` (`id`, `kode_reservasi`, `diskon`, `jenis_bayar`, `waktu_bayar`) VALUES
(5, 'RSV-HTLD28PKA', NULL, 'CASH', '2025-04-20 13:57:09'),
(7, 'RSV-HTLL3JBWX', 4, 'MANDIRI', '2025-04-21 03:07:17'),
(8, 'RSV-HTLYBPH6M', NULL, 'BRI', '2025-01-21 03:32:18');

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE `room` (
  `id` int(11) NOT NULL,
  `nomor_ruangan` varchar(12) NOT NULL,
  `kapasitas` int(2) NOT NULL,
  `harga` bigint(20) NOT NULL,
  `jumlah_kamar` int(3) NOT NULL,
  `type` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`id`, `nomor_ruangan`, `kapasitas`, `harga`, `jumlah_kamar`, `type`) VALUES
(3, '5DNMB3', 3, 1000000, 20, 'STANDAR'),
(4, 'HRVMNB', 5, 750000, 100, 'STANDAR'),
(5, 'WCMJEX', 2, 10000000, 5, 'LUXURY');

-- --------------------------------------------------------

--
-- Table structure for table `tamu`
--

CREATE TABLE `tamu` (
  `id` int(11) NOT NULL,
  `nama` text NOT NULL,
  `no_identitas` int(16) NOT NULL,
  `alamat` text NOT NULL,
  `telpon` int(15) NOT NULL,
  `kelamin` tinyint(1) NOT NULL,
  `status` varchar(12) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tamu`
--

INSERT INTO `tamu` (`id`, `nama`, `no_identitas`, `alamat`, `telpon`, `kelamin`, `status`) VALUES
(3, 'Saepul', 12345678, 'Dimari', 0, 1, NULL),
(4, 'Junaedi', 11111111, 'Bojong Gede', 112, 1, NULL),
(5, 'Ucup', 22222222, 'Margonda Raya', 12344321, 1, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `diskon`
--
ALTER TABLE `diskon`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tamu`
--
ALTER TABLE `tamu`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `book`
--
ALTER TABLE `book`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `diskon`
--
ALTER TABLE `diskon`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `pembayaran`
--
ALTER TABLE `pembayaran`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `room`
--
ALTER TABLE `room`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tamu`
--
ALTER TABLE `tamu`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
