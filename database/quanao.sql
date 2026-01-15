-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th1 15, 2026 lúc 03:06 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `quanao`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chi_tiet_hoa_don`
--

CREATE TABLE `chi_tiet_hoa_don` (
  `MACTHD` varchar(10) NOT NULL,
  `MAHD` varchar(10) DEFAULT NULL,
  `MASP` varchar(10) DEFAULT NULL,
  `SOLUONG` int(11) NOT NULL,
  `DONGIA` decimal(10,2) NOT NULL,
  `THANHTIEN` decimal(10,0) NOT NULL,
  `CHIETKHAU` int(11) DEFAULT NULL,
  `THUCTHU` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `chi_tiet_hoa_don`
--

INSERT INTO `chi_tiet_hoa_don` (`MACTHD`, `MAHD`, `MASP`, `SOLUONG`, `DONGIA`, `THANHTIEN`, `CHIETKHAU`, `THUCTHU`) VALUES
('CTHD001', 'HD001', 'SP103', 1, 280000.00, 280000, 0, NULL),
('CTHD002', 'HD002', 'SP106', 1, 1200000.00, 1200000, 0, NULL),
('CTHD003', 'HD003', 'SP103', 1, 280000.00, 280000, 0, NULL),
('CTHD004', 'HD003', 'SP105', 1, 950000.00, 950000, 0, NULL),
('CTHD005', 'HD004', 'SP102', 1, 300000.00, 300000, 30, NULL),
('CTHD006', 'HD004', 'SP105', 1, 950000.00, 950000, 30, NULL),
('CTHD007', 'HD005', 'SP101', 1, 350000.00, 350000, 0, NULL),
('CTHD008', 'HD006', 'SP102', 1, 300000.00, 300000, 0, NULL),
('CTHD009', 'HD007', 'SP105', 1, 950000.00, 950000, 25, NULL),
('CTHD010', 'HD007', 'SP105', 1, 950000.00, 950000, 25, NULL),
('CTHD011', 'HD007', 'SP102', 1, 300000.00, 300000, 25, NULL),
('CTHD012', 'HD008', 'SP102', 1, 300000.00, 300000, 30, NULL),
('CTHD013', 'HD008', 'SP101', 1, 350000.00, 350000, 30, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chi_tiet_san_pham`
--

CREATE TABLE `chi_tiet_san_pham` (
  `MACTSP` varchar(10) NOT NULL,
  `MASP` varchar(10) DEFAULT NULL,
  `KICHTHUOC` varchar(50) DEFAULT NULL,
  `MAUSAC` varchar(50) DEFAULT NULL,
  `CHATLIEU` varchar(100) DEFAULT NULL,
  `TRONGLUONG` decimal(10,2) DEFAULT NULL,
  `GHICHU` text DEFAULT NULL,
  `NGAYTAO` datetime DEFAULT current_timestamp(),
  `NGAYSUA` datetime DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `chi_tiet_san_pham`
--

INSERT INTO `chi_tiet_san_pham` (`MACTSP`, `MASP`, `KICHTHUOC`, `MAUSAC`, `CHATLIEU`, `TRONGLUONG`, `GHICHU`, `NGAYTAO`, `NGAYSUA`) VALUES
('CTSP001', 'SP301', 'M', 'Đen', 'Polyester', 0.30, 'Thoáng mát, phù hợp tập luyện', '2025-08-01 15:18:01', NULL),
('CTSP002', 'SP301', 'L', 'Trắng', 'Polyester', 0.35, 'Kẻ sọc, chất lượng cao', '2025-08-01 15:18:01', NULL),
('CTSP003', 'SP302', 'S', 'Xanh', 'Cotton', 0.25, 'Thiết kế baby tee, nhẹ nhàng', '2025-08-01 15:18:01', NULL),
('CTSP004', 'SP302', 'M', 'Đỏ', 'Cotton', 0.28, 'In logo nổi bật', '2025-08-01 15:18:01', NULL),
('CTSP005', 'SP303', 'M', 'Xám', 'Nylon', 0.40, 'Nhẹ, thoải mái khi chạy bộ', '2025-08-01 15:18:01', NULL),
('CTSP006', 'SP304', 'S', 'Đen', 'Jean', 0.50, 'Chất vải dày dặn, có lớp lót', '2025-08-01 15:18:01', NULL),
('CTSP007', 'SP304', 'M', 'Trắng', 'Jean', 0.52, 'Thiết kế 2 màu thời trang', '2025-08-01 15:18:01', NULL),
('CTSP008', 'SP305', '36', 'Hồng', 'Da tổng hợp', 0.80, 'Đế cao su chống trượt', '2025-08-01 15:18:01', NULL),
('CTSP009', 'SP306', '40', 'Đen', 'Da tổng hợp', 0.90, 'Phù hợp cho nam, đế bền', '2025-08-01 15:18:01', NULL),
('CTSP010', 'SP307', 'S', 'Xanh Lá', 'Vải lụa', 0.60, 'Thấm hút mồ hôi tốt', '2025-08-01 15:18:01', NULL),
('CTSP011', 'SP308', 'L', 'Xám', 'Len', 0.70, 'Dài tay, ấm áp', '2025-08-01 15:18:01', NULL),
('CTSP012', 'SP309', 'One Size', 'Trắng', 'Nylon', 0.40, 'Dung tích lớn, hiện đại', '2025-08-01 15:18:01', NULL),
('CTSP013', 'SP310', 'One Size', 'Đen', 'Cotton', 0.20, 'Phong cách trẻ trung', '2025-08-01 15:18:01', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `danh_gia`
--

CREATE TABLE `danh_gia` (
  `MADANHGIA` varchar(10) NOT NULL,
  `MAKH` varchar(10) DEFAULT NULL,
  `MASP` varchar(10) DEFAULT NULL,
  `DIEM` int(11) DEFAULT NULL,
  `NHANXET` text DEFAULT NULL,
  `NGAYDANHGIA` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `danh_gia`
--

INSERT INTO `danh_gia` (`MADANHGIA`, `MAKH`, `MASP`, `DIEM`, `NHANXET`, `NGAYDANHGIA`) VALUES
('DG001', 'KH001', 'SP102', 5, 'Áo rất đẹp, chất lượng tốt!', '2025-07-25 09:05:20'),
('DG003', 'KH004', 'SP301', 4, 'Áo đẹp, mặc thoải mái!', '2025-07-25 09:01:16'),
('DG004', 'KH006', 'SP306', 5, 'Giày chất lượng, đáng tiền.', '2025-07-25 09:01:16'),
('DG007', 'KH002', 'SP105', 4, 'Giày thoải mái nhưng hơi đắt.', '2025-07-25 09:05:20'),
('DG008', 'KH001', 'SP301', 5, 'Áo rất đẹp, chất lượng tốt!', '2025-07-25 09:30:28'),
('DG010', 'KH003', 'SP306', 4, 'Giày thoải mái nhưng hơi đắt.', '2025-07-25 09:30:28');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `danh_muc_san_pham`
--

CREATE TABLE `danh_muc_san_pham` (
  `MADANHMUC` varchar(10) NOT NULL,
  `TENDANHMUC` varchar(255) NOT NULL,
  `NGAYTAO` datetime DEFAULT current_timestamp(),
  `NGAYSUA` datetime DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `danh_muc_san_pham`
--

INSERT INTO `danh_muc_san_pham` (`MADANHMUC`, `TENDANHMUC`, `NGAYTAO`, `NGAYSUA`) VALUES
('DM001', 'Áo thể thao', '2025-07-25 07:25:22', NULL),
('DM002', 'Quần thể thao', '2025-07-25 07:25:22', NULL),
('DM003', 'Giày', '2025-07-25 07:25:22', NULL),
('DM004', 'Set', '2025-07-25 07:25:22', NULL),
('DM005', 'Phụ kiện', '2025-07-25 07:25:22', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `giao_dich_kho`
--

CREATE TABLE `giao_dich_kho` (
  `MAGD` varchar(10) NOT NULL,
  `MASP` varchar(10) DEFAULT NULL,
  `SOLUONG` int(11) NOT NULL,
  `LOAI_GIAO_DICH` enum('NHAP','XUAT') NOT NULL,
  `NGUON_LYDO` varchar(255) DEFAULT NULL,
  `NGAYGIAODICH` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `giao_dich_kho`
--

INSERT INTO `giao_dich_kho` (`MAGD`, `MASP`, `SOLUONG`, `LOAI_GIAO_DICH`, `NGUON_LYDO`, `NGAYGIAODICH`) VALUES
('GD001', 'SP108', 50, 'NHAP', 'Nhà cung cấp Adidas VN', '2025-07-15 10:00:00'),
('GD002', 'SP104', 100, 'NHAP', 'Nhà cung cấp Adidas VN', '2025-07-15 10:00:00'),
('GD003', 'SP109', 2, 'XUAT', 'Bán hàng cho hóa đơn 1', '2025-07-16 15:00:00'),
('GD004', 'SP110', 1, 'XUAT', 'Bán hàng cho hóa đơn 2', '2025-07-16 15:30:00'),
('GD005', 'SP302', 75, 'NHAP', 'Nhà cung cấp Under Armour US', '2025-07-20 09:00:00'),
('GD006', 'SP304', 120, 'NHAP', 'Nhà cung cấp Under Armour VN', '2025-07-20 09:30:00'),
('GD007', 'SP309', 3, 'XUAT', 'Bán hàng cho hóa đơn 3', '2025-07-21 14:00:00'),
('GD008', 'SP310', 2, 'XUAT', 'Bán hàng cho hóa đơn 4', '2025-07-21 14:30:00'),
('GD009', 'SP304', 100, 'NHAP', 'Nhà cung cấp NIKE VN', '2025-07-15 10:00:00'),
('GD010', 'SP309', 2, 'XUAT', 'Bán hàng cho hóa đơn 5', '2025-07-16 15:00:00'),
('GD011', 'SP310', 1, 'XUAT', 'Bán hàng cho hóa đơn 6', '2025-07-16 15:30:00'),
('GD012', 'SP308', 50, 'NHAP', 'Nhà cung cấp NIKE VN', '2025-07-15 10:00:00');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `gio_hang`
--

CREATE TABLE `gio_hang` (
  `MAGIOHANG` varchar(10) NOT NULL,
  `MAKH` varchar(10) DEFAULT NULL,
  `MASP` varchar(10) DEFAULT NULL,
  `SOLUONG` int(11) NOT NULL,
  `NGAYTHEM` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `gio_hang`
--

INSERT INTO `gio_hang` (`MAGIOHANG`, `MAKH`, `MASP`, `SOLUONG`, `NGAYTHEM`) VALUES
('GH001', 'KH001', 'SP101', 2, '2025-07-25 09:27:06'),
('GH003', 'KH004', 'SP305', 1, '2025-07-25 09:18:59'),
('GH004', 'KH006', 'SP308', 2, '2025-07-25 09:18:59'),
('GH005', 'KH001', 'SP301', 2, '2025-07-25 09:30:28'),
('GH006', 'KH003', 'SP302', 1, '2025-07-25 09:30:28');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hinh_anh`
--

CREATE TABLE `hinh_anh` (
  `MAHINH` varchar(10) NOT NULL,
  `MASP` varchar(10) DEFAULT NULL,
  `URL_HINH` varchar(255) NOT NULL,
  `MO_TA` text DEFAULT NULL,
  `NGAYTAO` datetime DEFAULT current_timestamp(),
  `NGAYSUA` datetime DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `hinh_anh`
--

INSERT INTO `hinh_anh` (`MAHINH`, `MASP`, `URL_HINH`, `MO_TA`, `NGAYTAO`, `NGAYSUA`) VALUES
('AA101', 'SP101', 'AA101.jpg', 'Hình ảnh áo thun thể thao kẻ sọc', '2025-07-25 08:47:11', NULL),
('AA102', 'SP101', 'AA102.jpg', 'Hình ảnh áo thun thể thao kẻ sọc', '2025-07-25 08:47:11', NULL),
('AA201', 'SP102', 'AA201.jpg', 'Hình ảnh áo thun thể thao nữ Adidas', '2025-07-25 08:47:11', NULL),
('AA202', 'SP102', 'AA202.jpg', 'Hình ảnh áo thun thể thao nữ Adidas', '2025-07-25 08:47:11', NULL),
('AG101', 'SP105', 'AG101.jpg', 'Hình ảnh giày thể thao nữ Adidas', '2025-07-25 08:47:11', NULL),
('AG102', 'SP105', 'AG102.jpg', 'Hình ảnh giày thể thao nữ Adidas', '2025-07-25 08:47:11', NULL),
('AG201', 'SP106', 'AG201.jpg', 'Hình ảnh giày thể thao nam Adidas', '2025-07-25 08:47:11', NULL),
('AG202', 'SP106', 'AG202.jpg', 'Hình ảnh giày thể thao nam Adidas', '2025-07-25 08:47:11', NULL),
('AP101', 'SP109', 'AP101.jpg', 'Hình ảnh vớ cổ cao thể thao nam, nữ Adidas', '2025-07-25 08:47:11', NULL),
('AP102', 'SP109', 'AP102.jpg', 'Hình ảnh vớ cổ cao thể thao nam, nữ Adidas', '2025-07-25 08:47:11', NULL),
('AP201', 'SP110', 'AP201.jpg', 'Hình ảnh mũ lưỡi trai thể thao Adidas', '2025-07-25 08:47:11', NULL),
('AP202', 'SP110', 'AP202.jpg', 'Hình ảnh mũ lưỡi trai thể thao Adidas', '2025-07-25 08:47:11', NULL),
('AQ101', 'SP103', 'AQ101.jpg', 'Hình ảnh quần chạy bộ nam Adidas', '2025-07-25 08:47:11', NULL),
('AQ102', 'SP103', 'AQ102.jpg', 'Hình ảnh quần chạy bộ nam Adidas', '2025-07-25 08:47:11', NULL),
('AQ201', 'SP104', 'AQ201.jpg', 'Hình ảnh chân váy thể thao nữ Adidas', '2025-07-25 08:47:11', NULL),
('AQ202', 'SP104', 'AQ202.jpg', 'Hình ảnh chân váy thể thao nữ Adidas', '2025-07-25 08:47:11', NULL),
('AS101', 'SP107', 'AS101.jpg', 'Hình ảnh set đồ thể thao nữ Adidas', '2025-07-25 08:47:11', NULL),
('AS102', 'SP107', 'AS102.jpg', 'Hình ảnh set đồ thể thao nữ Adidas', '2025-07-25 08:47:11', NULL),
('AS201', 'SP108', 'AS201.jpg', 'Hình ảnh set đồ thể thao nam Adidas', '2025-07-25 08:47:11', NULL),
('AS202', 'SP108', 'AS202.jpg', 'Hình ảnh set đồ thể thao nam Adidas', '2025-07-25 08:47:11', NULL),
('NA101', 'SP201', 'UA101.jpg', 'Hình ảnh Áo thun Tenis Nike - Màu Trắng', '2025-07-25 08:44:09', NULL),
('NA102', 'SP201', 'UA102.jpg', 'Hình ảnh Áo thun Tenis Nike - Màu xanh', '2025-07-25 08:44:09', NULL),
('NA201', 'SP202', 'UA201.jpg', 'Hình ảnh áo Áo thun thể thao Nike xám - Mặt trước', '2025-07-25 08:44:09', NULL),
('NA202', 'SP202', 'UA202.jpg', 'Hình ảnh áo Áo thun thể thao Nike xám - Mặt sau', '2025-07-25 08:44:09', NULL),
('NG101', 'SP205', 'UG101.jpg', 'Hình ảnh Giày Originals Nam Nike Handball Spezial IG6192 - Bên trái', '2025-07-25 08:44:09', NULL),
('NG102', 'SP205', 'UG102.jpg', 'Hình ảnh Giày Originals Nam Nike Handball Spezial IG6192 - Bên phải', '2025-07-25 08:44:09', NULL),
('NG201', 'SP206', 'UG201.jpg', 'Hình ảnh Giày Originals Nam ADIDAS Samba Og JI3201 - Bên trái', '2025-07-25 08:44:09', NULL),
('NG202', 'SP206', 'UG202.jpg', 'Hình ảnh Giày Originals Nam ADIDAS Samba Og JI3201 - Bên phải', '2025-07-25 08:44:09', NULL),
('NP101', 'SP209', 'UP101.jpg', 'Hình ảnh Nón Thể Thao Unisex NIKE Dri-Fit Club Unstructured Featherlight FB5682-010 - Mặt trước', '2025-07-25 08:44:09', NULL),
('NP102', 'SP209', 'UP102.jpg', 'Hình ảnh Nón Thể Thao Unisex NIKE Dri-Fit Club Unstructured Featherlight FB5682-010 - Mặt sau', '2025-07-25 08:44:09', NULL),
('NP201', 'SP210', 'UP201.jpg', 'Hình ảnh Mũ lưỡi trai Nike - Mặt trước', '2025-07-25 08:44:09', NULL),
('NP202', 'SP210', 'UP202.jpg', 'Hình ảnh Mũ lưỡi trai Nike - Mặt sau', '2025-07-25 08:44:09', NULL),
('NQ101', 'SP203', 'UQ101.jpg', 'Quần Short nam Nike - Mặt trước', '2025-07-25 08:44:09', NULL),
('NQ102', 'SP203', 'UQ102.jpg', 'Quần Short nam Nike - Mặt sau', '2025-07-25 08:44:09', NULL),
('NQ201', 'SP204', 'UQ201.jpg', 'Quẩn thể thao Nike - Mặt trước', '2025-07-25 08:44:09', NULL),
('NQ202', 'SP204', 'UQ202.jpg', 'Quẩn thể thao Nike - Mặt sau', '2025-07-25 08:44:09', NULL),
('NS101', 'SP207', 'US101.jpg', 'Hình ảnh Set đồ Áo Bóng Rổ Nam NIKE As M Nk Tee M90 Oc Photo FZ8082-133 - Mặt trước', '2025-07-25 08:44:09', NULL),
('NS102', 'SP207', 'US102.jpg', 'Hình ảnh set đồ Set đồ Áo Bóng Rổ Nam NIKE As M Nk Tee M90 Oc Photo FZ8082-133 - Mặt sau', '2025-07-25 08:44:09', NULL),
('NS201', 'SP208', 'US201.jpg', 'Hình ảnh Set đồ Áo Phông - Áo thun Thể Thao Nam NIKE As M Nsw Tee Club Ssnl Hbr FV5712-133 - Mặt trước', '2025-07-25 08:44:09', NULL),
('NS202', 'SP208', 'US202.jpg', 'Hình ảnh Set đồ Áo Phông - Áo thun Thể Thao Nam NIKE As M Nsw Tee Club Ssnl Hbr FV5712-133 - Mặt sau', '2025-07-25 08:44:09', NULL),
('PA101', 'SP401', 'PA101.jpg', 'Hình ảnh áo thun crop top Puma Classics Logo nữ - Mặt trước', '2025-07-29 17:44:07', NULL),
('PA102', 'SP401', 'PA102.jpg', 'Hình ảnh áo thun crop top Puma Classics Logo nữ - Mặt sau', '2025-07-29 17:44:07', NULL),
('PA201', 'SP402', 'PA201.jpg', 'Hình ảnh áo Polo Crop Top Puma Classics Nữ - Mặt trước', '2025-07-29 17:44:07', NULL),
('PA202', 'SP402', 'PA202.jpg', 'Hình ảnh áo Polo Crop Top Puma Classics Nữ - Mặt sau', '2025-07-29 17:44:07', NULL),
('PG101', 'SP405', 'PG101.jpg', 'Hình ảnh giày bệt Puma Speedcat LS Nữ - Mặt bên', '2025-07-29 17:44:07', NULL),
('PG102', 'SP405', 'PG102.jpg', 'Hình ảnh giày bệt Puma Speedcat LS Nữ - Người mẫu mang giày', '2025-07-29 17:44:07', NULL),
('PG201', 'SP406', 'PG201.jpg', 'Hình ảnh giày Puma Speedcat Motorsport X Nữ - Mặt bên', '2025-07-29 17:44:07', NULL),
('PG202', 'SP406', 'PG202.jfif', 'Hình ảnh giày Puma Speedcat Motorsport X Nữ - Người mẫu mang giày', '2025-07-29 17:44:07', NULL),
('PN101', 'SP410', 'PN201.jpg', 'Hình ảnh nón bucket Puma x Squid Game - Toàn cảnh', '2025-07-29 17:44:07', NULL),
('PN102', 'SP410', 'PN202.jpg', 'Hình ảnh nón bucket Puma x Squid Game - Người mẫu đội', '2025-07-29 17:44:07', NULL),
('PN201', 'SP409', 'PN101.jpg', 'Hình ảnh nón lưỡi trai Puma Essential No.1 Logo - Toàn cảnh', '2025-07-29 17:44:07', NULL),
('PN202', 'SP409', 'PN102.jpg', 'Hình ảnh nón lưỡi trai Puma Essential No.1 Logo - Người mẫu đội', '2025-07-29 17:44:07', NULL),
('PQ101', 'SP403', 'PQ101.jpg', 'Hình ảnh quần đùi đạp xe Hyrox Puma Nữ - Toàn cảnh', '2025-07-29 17:44:07', NULL),
('PQ102', 'SP403', 'PQ102.jpg', 'Hình ảnh quần đùi đạp xe Hyrox Puma Nữ - Người mẫu mặc', '2025-07-29 17:44:07', NULL),
('PQ201', 'SP404', 'PQ201.jpg', 'Hình ảnh quần short 2 trong 1 Hyrox Puma Nữ - Toàn cảnh', '2025-07-29 17:44:07', NULL),
('PQ202', 'SP404', 'PQ202.jpg', 'Hình ảnh quần short 2 trong 1 Hyrox Puma Nữ - Người mẫu mặc', '2025-07-29 17:44:07', NULL),
('PS101', 'SP407', 'PS101.jpg', 'Hình ảnh set chân váy dài và áo crop top Puma Nữ - Toàn cảnh', '2025-07-29 17:44:07', NULL),
('PS102', 'SP407', 'PS102.jpg', 'Hình ảnh set chân váy dài và áo crop top Puma Nữ - Người mẫu mặc', '2025-07-29 17:44:07', NULL),
('PS201', 'SP408', 'PS201.jpg', 'Hình ảnh set áo polo dài tay và chân váy midi Puma Nữ - Mặt trước', '2025-07-29 17:44:07', NULL),
('PS202', 'SP408', 'PS202.jpg', 'Hình ảnh set áo polo dài tay và chân váy midi Puma Nữ - Mặt sau', '2025-07-29 17:44:07', NULL),
('UA101', 'SP301', 'UA101.jpg', 'Hình ảnh áo thun thể thao kẻ sọc Under Armour - Mặt trước', '2025-07-25 07:25:22', NULL),
('UA102', 'SP301', 'UA102.jpg', 'Hình ảnh áo thun thể thao kẻ sọc Under Armour - Mặt sau', '2025-07-25 07:25:22', NULL),
('UA201', 'SP302', 'UA201.jpg', 'Hình ảnh áo thun thể thao nữ Under Armour - Mặt trước', '2025-07-25 07:25:22', NULL),
('UA202', 'SP302', 'UA202.jpg', 'Hình ảnh áo thun thể thao nữ Under Armour - Mặt sau', '2025-07-25 07:25:22', NULL),
('UG101', 'SP305', 'UG101.jpg', 'Hình ảnh giày thể thao nữ Under Armour - Bên trái', '2025-07-25 07:25:22', NULL),
('UG102', 'SP305', 'UG102.jpg', 'Hình ảnh giày thể thao nữ Under Armour - Bên phải', '2025-07-25 07:25:22', NULL),
('UG201', 'SP306', 'UG201.jpg', 'Hình ảnh giày thể thao nam Under Armour - Bên trái', '2025-07-25 07:25:22', NULL),
('UG202', 'SP306', 'UG202.jpg', 'Hình ảnh giày thể thao nam Under Armour - Bên phải', '2025-07-25 07:25:22', NULL),
('UP101', 'SP309', 'UP101.jpg', 'Hình ảnh balo trắng Under Armour - Mặt trước', '2025-07-25 07:25:22', NULL),
('UP102', 'SP309', 'UP102.jpg', 'Hình ảnh balo trắng Under Armour - Mặt sau', '2025-07-25 07:25:22', NULL),
('UP201', 'SP310', 'UP201.jpg', 'Hình ảnh mũ lưỡi trai Under Armour - Mặt trước', '2025-07-25 07:25:22', NULL),
('UP202', 'SP310', 'UP202.jpg', 'Hình ảnh mũ lưỡi trai Under Armour - Mặt sau', '2025-07-25 07:25:22', NULL),
('UQ101', 'SP303', 'UQ101.jpg', 'Hình ảnh quần chạy bộ nam Under Armour - Mặt trước', '2025-07-25 07:25:22', NULL),
('UQ102', 'SP303', 'UQ102.jpg', 'Hình ảnh quần chạy bộ nam Under Armour - Mặt sau', '2025-07-25 07:25:22', NULL),
('UQ201', 'SP304', 'UQ201.jpg', 'Hình ảnh chân váy thể thao nữ Under Armour - Mặt trước', '2025-07-25 07:25:22', NULL),
('UQ202', 'SP304', 'UQ202.jpg', 'Hình ảnh chân váy thể thao nữ Under Armour - Mặt sau', '2025-07-25 07:25:22', NULL),
('US101', 'SP307', 'US101.jpg', 'Hình ảnh set đồ thể thao nữ Under Armour - Mặt trước', '2025-07-25 07:25:22', NULL),
('US102', 'SP307', 'US102.jpg', 'Hình ảnh set đồ thể thao nữ Under Armour - Mặt sau', '2025-07-25 07:25:22', NULL),
('US201', 'SP308', 'US201.jpg', 'Hình ảnh set đồ thể thao nam Under Armour - Mặt trước', '2025-07-25 07:25:22', NULL),
('US202', 'SP308', 'US202.jpg', 'Hình ảnh set đồ thể thao nam Under Armour - Mặt sau', '2025-07-25 07:25:22', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hoa_don`
--

CREATE TABLE `hoa_don` (
  `MAHD` varchar(10) NOT NULL,
  `MAKH` varchar(10) DEFAULT NULL,
  `user_id` varchar(10) DEFAULT NULL,
  `TONGTIEN` decimal(10,2) DEFAULT NULL,
  `NGAYLAP` datetime DEFAULT current_timestamp(),
  `NGAYCAPNHAT` datetime DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `hoa_don`
--

INSERT INTO `hoa_don` (`MAHD`, `MAKH`, `user_id`, `TONGTIEN`, `NGAYLAP`, `NGAYCAPNHAT`) VALUES
('HD001', 'VL001', 'US005', 280000.00, '2025-08-03 14:29:04', NULL),
('HD002', 'KH07', 'US005', 1200000.00, '2025-08-03 14:29:37', NULL),
('HD003', 'KH07', 'US005', 1230000.00, '2025-08-03 14:55:25', NULL),
('HD004', 'VL001', 'US005', 875000.00, '2025-08-03 17:16:00', NULL),
('HD005', 'VL001', 'US005', 350000.00, '2025-08-03 21:37:32', NULL),
('HD006', 'VL001', 'US005', 300000.00, '2025-08-03 21:41:45', NULL),
('HD007', 'KH07', 'US005', 1650000.00, '2025-08-05 20:08:58', NULL),
('HD008', 'KH07', 'US005', 455000.00, '2025-08-06 07:33:01', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khach_hang`
--

CREATE TABLE `khach_hang` (
  `MAKH` varchar(10) NOT NULL,
  `HOTEN` varchar(255) NOT NULL,
  `EMAIL` varchar(255) NOT NULL,
  `SDT` varchar(20) DEFAULT NULL,
  `DIACHI` text DEFAULT NULL,
  `GT` varchar(15) NOT NULL,
  `NGAYTAO` datetime DEFAULT current_timestamp(),
  `NGAYSUA` datetime DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `khach_hang`
--

INSERT INTO `khach_hang` (`MAKH`, `HOTEN`, `EMAIL`, `SDT`, `DIACHI`, `GT`, `NGAYTAO`, `NGAYSUA`) VALUES
('KH001', 'Nguyễn Văn A', 'vana@gmail.com', '0901234567', '123 Đường Láng, TP.HCM', 'Nam', '2025-07-25 09:04:39', '2025-07-31 16:39:40'),
('KH002', 'Trần Thị B', 'tranb@gmail.com', '0912345678', '456 Nguyễn Trãi, Hà Nội', 'Nữ', '2025-07-25 09:04:39', '2025-07-31 16:40:49'),
('KH003', 'Lê Văn C', 'levanc@gmail.com', '0923456789', '789 Lê Lợi, Đà Nẵng', 'Nam', '2025-07-25 09:04:39', '2025-07-31 16:39:52'),
('KH004', 'Trần Văn D', 'trand@gmail.com', '0934567890', '234 Lý Thường Kiệt, Cần Thơ', 'Nam', '2025-07-25 07:25:22', '2025-07-31 16:40:39'),
('KH005', 'Phạm Thị E', 'phame@gmail.com', '0945678901', '567 Hai Bà Trưng, Huế', 'Nữ', '2025-07-25 07:25:22', '2025-07-31 16:40:02'),
('KH006', 'Hoàng Văn F', 'hoangf@gmail.com', '0956789012', '890 Trần Phú, Nha Trang', 'Nam', '2025-07-25 07:25:22', '2025-07-31 16:40:11'),
('KH007', 'Trần Lý Tiểu Nhi', 'Nhichodien@gmail.com', '098765432', '248a', 'Nữ', '2025-08-01 15:48:17', NULL),
('KH010', 'Trần Văn G', 'trand1@gmail.com', '0934567891', '234 Lý Thường Kiệt, Cần Thơ', 'Nam', '2025-07-25 09:33:34', '2025-07-31 16:40:18'),
('KH011', 'Phạm Thị H', 'phame2@gmail.com', '0945678921', '567 Hai Bà Trưng, Huế', 'Nữ', '2025-07-25 09:33:34', '2025-07-31 16:40:27'),
('KH012', 'Hoàng Văn E', 'hoangf3@gmail.com', '0956784012', '890 Trần Phú, Nha Trang', 'Nam', '2025-07-25 09:33:34', '2025-07-31 16:40:34'),
('KH013', 'Trần Quốc Huy', 'A123@gmail.com', '0917607249', '22a LBB', 'Nam', '2025-08-01 15:55:47', NULL),
('KH07', 'Trần Lý Tiểu Nhi', '0856754315A@gmail.com', '0917607248', '149 Lũy Bán Bích', 'Nam', '2025-08-01 15:41:51', '2025-08-04 21:52:24'),
('VL001', 'Khách vãng lai', '0', '0000000000', '0', 'Khác', '2025-08-01 21:09:02', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khuyen_mai`
--

CREATE TABLE `khuyen_mai` (
  `MAKM` varchar(10) NOT NULL,
  `TENKM` varchar(255) NOT NULL,
  `PHANTRAMGIAM` int(11) DEFAULT NULL,
  `NGAYBATDAU` date NOT NULL,
  `NGAYKETTHUC` date DEFAULT NULL,
  `NGAYTAO` datetime DEFAULT current_timestamp(),
  `NGAYSUA` datetime DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `khuyen_mai`
--

INSERT INTO `khuyen_mai` (`MAKM`, `TENKM`, `PHANTRAMGIAM`, `NGAYBATDAU`, `NGAYKETTHUC`, `NGAYTAO`, `NGAYSUA`) VALUES
('KM001', 'Giảm giá mùa hè', 20, '2025-07-01', '2025-08-01', '2025-07-25 09:04:39', NULL),
('KM002', 'Black Friday', 30, '2025-11-25', '2025-11-30', '2025-07-25 09:04:39', NULL),
('KM003', 'Giảm giá mùa đông', 25, '2025-12-01', '2025-12-31', '2025-07-25 08:58:47', NULL),
('KM004', 'Ưu đãi Tết', 20, '2026-01-20', '2026-02-05', '2025-07-25 08:58:47', '2025-08-01 15:05:42');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `san_pham`
--

CREATE TABLE `san_pham` (
  `MASP` varchar(10) NOT NULL,
  `TENSP` varchar(255) NOT NULL,
  `DONGIA` decimal(10,2) NOT NULL,
  `SOLUONG` int(11) NOT NULL,
  `MOTA` text DEFAULT NULL,
  `HINHANH` varchar(255) DEFAULT NULL,
  `MADANHMUC` varchar(10) DEFAULT NULL,
  `MATHUONGHIEU` varchar(10) DEFAULT NULL,
  `NGAYTAO` datetime DEFAULT current_timestamp(),
  `NGAYSUA` datetime DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `san_pham`
--

INSERT INTO `san_pham` (`MASP`, `TENSP`, `DONGIA`, `SOLUONG`, `MOTA`, `HINHANH`, `MADANHMUC`, `MATHUONGHIEU`, `NGAYTAO`, `NGAYSUA`) VALUES
('SP101', 'Áo thun thể thao Adidas kẻ sọc', 350000.00, 100, 'Áo thun thể thao Adidas nam nữ kẻ sọc 2 màu, chất liệu thoáng mát', 'AA101.jpg', 'DM001', 'TH002', '2025-07-25 08:46:35', NULL),
('SP102', 'Áo thun thể thao nữ Adidas in logo', 300000.00, 150, 'Áo thun thể thao Adidas nữ from baby tee, chất liệu thoáng mát', 'AA202.jpg', 'DM001', 'TH002', '2025-07-25 08:46:35', NULL),
('SP103', 'Quần chạy bộ nam Adidas', 280000.00, 50, 'Quần chạy bộ Adidas, nhẹ và thoải mái', '1753786090673_AQ102.png', 'DM001', 'TH001', '2025-07-25 08:46:35', '2025-07-29 17:48:11'),
('SP104', 'Chân váy thể thao nữ Adidas', 420000.00, 100, 'Chân váy thể thao nữ Adidas 2 màu, có lớp lót, chất vải dày dặn, nhẹ và thoải mái', 'AQ201.jpg', 'DM002', 'TH002', '2025-07-25 08:46:35', NULL),
('SP105', 'Giày thể thao nữ Adidas', 950000.00, 60, 'Giày Adidas nữ chính hãng, đế cao su chống trượt, đủ size', 'AG101.jpg', 'DM003', 'TH002', '2025-07-25 08:46:35', NULL),
('SP106', 'Giày thể thao nam Adidas', 1200000.00, 30, 'Giày Adidas nam chính hãng, đế cao su chống trượt', 'AG201.jpg', 'DM003', 'TH002', '2025-07-25 08:46:35', NULL),
('SP107', 'Set đồ bộ thể thao nữ Adidas', 250000.00, 100, 'Set bộ thể thao nữ Adidas, vải thấm hút mồ hôi, nhẹ và thoải mái', 'AS101.jpg', 'DM004', 'TH002', '2025-07-25 08:46:35', NULL),
('SP108', 'Set đồ bộ thể thao nam Adidas dài', 340000.00, 80, 'Set bộ thể thao nam dài tay Adidas, vải thấm hút mồ hôi, nhẹ và thoải mái', 'AS201.jpg', 'DM004', 'TH002', '2025-07-25 08:46:35', NULL),
('SP109', 'Vớ cổ cao Adidas', 115000.00, 200, 'Vớ cổ cao 2 màu dành cho nam và nữ, chất liệu dày dặn', 'AP101.jpg', 'DM005', 'TH002', '2025-07-25 08:46:35', NULL),
('SP110', 'Mũ lưỡi trai Adidas', 165000.00, 100, 'Mũ thể thao Adidas, phong cách trẻ trung', 'AP201.jpg', 'DM005', 'TH002', '2025-07-25 08:46:35', NULL),
('SP201', 'Áo thun Tenis Nike ', 2500000.00, 100, 'Áo thun Tenis Nike, chất liệu thoáng mát thấm hút mồ hôi', 'NA101.jpg', 'DM001', 'TH001', '2025-07-25 08:33:17', NULL),
('SP202', 'Áo thun thể thao Nike xám', 400000.00, 100, 'Áo thun thể thao nike thoáng mát hợp trend', 'NA102.jpg', 'DM001', 'TH001', '2025-07-25 08:33:17', NULL),
('SP203', 'Quần Short nam Nike', 300000.00, 50, 'Quần Short nike, nhẹ và thoải mái', 'NQ101.jpg', 'DM002', 'TH001', '2025-07-25 08:33:17', NULL),
('SP204', 'Quẩn thể thao Nike ', 450000.00, 100, 'Quần thể thao Nike co giản', 'NQ201.jpg', 'DM002', 'TH001', '2025-07-25 08:33:17', NULL),
('SP205', 'Giày Originals Nam Nike Handball Spezial IG6192', 1000000.00, 60, 'Giày Originals Nam ADIDAS Handball Spezial IG6192, đế cao su chống trượt, đủ size', 'NG101.jpg', 'DM003', 'TH001', '2025-07-25 08:33:17', NULL),
('SP206', 'Giày Originals Nam ADIDAS Samba Og JI3201', 1300000.00, 30, 'Giày Originals Nam ADIDAS Samba Og JI3201, đế cao su chống trượt', 'NG201.jpg', 'DM003', 'TH001', '2025-07-25 08:33:17', NULL),
('SP207', 'Set đồ Áo Bóng Rổ Nam NIKE As M Nk Tee M90 Oc Photo FZ8082-133', 280000.00, 100, 'Set bộ đồ Áo Bóng Rổ Nam NIKE, vải thấm hút mồ hôi, nhẹ và thoải mái', 'NS101.jpg', 'DM004', 'TH001', '2025-07-25 08:33:17', NULL),
('SP208', 'Set đồ Áo Phông - Áo thun Thể Thao Nam NIKE As M Nsw Tee Club Ssnl Hbr FV5712-133', 370000.00, 80, 'Áo Phông - Áo thun Thể Thao Nam NIKE As M Nsw Tee Club Ssnl Hbr FV5712-133, vải thấm hút mồ hôi, nhẹ và thoải mái', 'NS201.jpg', 'DM004', 'TH001', '2025-07-25 08:33:17', NULL),
('SP209', 'Nón Thể Thao Unisex NIKE Dri-Fit Club Unstructured Featherlight FB5682-010', 600000.00, 50, 'Nón thể thao co giản thấm hút mồ hôi', 'NP101.jpg', 'DM005', 'TH001', '2025-07-25 08:33:17', NULL),
('SP210', 'Mũ lưỡi trai Nike', 180000.00, 100, 'Mũ thể thao Nike, phong cách trẻ trung', 'NP201.jpg', 'DM005', 'TH001', '2025-07-25 08:33:17', NULL),
('SP301', 'Áo thun thể thao Under Armour kẻ sọc', 400000.00, 100, 'Áo thun thể thao Under Armour nam nữ kẻ sọc, chất liệu thoáng mát', 'UA101.jpg', 'DM001', 'TH004', '2025-07-25 07:25:22', NULL),
('SP302', 'Áo thun thể thao nữ Under Armour in logo', 350000.00, 150, 'Áo thun thể thao Under Armour nữ from baby tee, chất liệu thoáng mát', 'UA201.jpg', 'DM001', 'TH004', '2025-07-25 07:25:22', NULL),
('SP303', 'Quần chạy bộ nam Under Armour', 300000.00, 50, 'Quần chạy bộ Under Armour, nhẹ và thoải mái', 'UQ101.jpg', 'DM002', 'TH004', '2025-07-25 07:25:22', NULL),
('SP304', 'Chân váy thể thao nữ Under Armour', 450000.00, 100, 'Chân váy thể thao nữ Under Armour 2 màu, có lớp lót, chất vải dày dặn, nhẹ và thoải mái', 'UQ201.jpg', 'DM002', 'TH004', '2025-07-25 07:25:22', NULL),
('SP305', 'Giày thể thao nữ Under Armour', 1000000.00, 60, 'Giày Under Armour nữ chính hãng, đế cao su chống trượt, đủ size', 'UG101.jpg', 'DM003', 'TH004', '2025-07-25 07:25:22', NULL),
('SP306', 'Giày thể thao nam Under Armour', 1300000.00, 30, 'Giày Under Armour nam chính hãng, đế cao su chống trượt', 'UG201.jpg', 'DM003', 'TH004', '2025-07-25 07:25:22', NULL),
('SP307', 'Set đồ bộ thể thao nữ Under Armour', 280000.00, 100, 'Set bộ thể thao nữ Under Armour, vải thấm hút mồ hôi, nhẹ và thoải mái', 'US101.jpg', 'DM004', 'TH004', '2025-07-25 07:25:22', NULL),
('SP308', 'Set đồ bộ thể thao nam Under Armour dài', 370000.00, 80, 'Set bộ thể thao nam dài tay Under Armour, vải thấm hút mồ hôi, nhẹ và thoải mái', 'US201.jpg', 'DM004', 'TH004', '2025-07-25 07:25:22', NULL),
('SP309', 'Balo trắng Under Armour', 600000.00, 50, 'Balo trắng Under Armour, thiết kế hiện đại, dung tích lớn', 'UP101.jpg', 'DM005', 'TH004', '2025-07-25 07:25:22', NULL),
('SP310', 'Mũ lưỡi trai Under Armour', 180000.00, 100, 'Mũ thể thao Under Armour, phong cách trẻ trung', 'UP201.jpg', 'DM005', 'TH004', '2025-07-25 07:25:22', NULL),
('SP401', 'Áo thun thể thao Puma Classic', 550000.00, 45, 'Áo thun thoáng khí, thiết kế cổ điển cho tập luyện', 'PA101.jpg', 'DM001', 'TH003', '2025-07-29 17:43:25', NULL),
('SP402', 'Áo croptop thể thao Puma Fit', 480000.00, 55, 'Áo croptop thời trang, hỗ trợ vận động linh hoạt', 'PA201.jpg', 'DM001', 'TH003', '2025-07-29 17:43:25', NULL),
('SP403', 'Quần short thể thao Puma Speed', 320000.00, 70, 'Quần short nhẹ nhàng, tối ưu cho chạy bộ', 'PQ101.jpg', 'DM002', 'TH003', '2025-07-29 17:43:25', '2025-07-29 17:49:53'),
('SP404', 'Quần legging thể thao Puma Flex', 420000.00, 65, 'Quần legging co giãn, thấm hút mồ hôi hiệu quả', 'PQ201.jpg', 'DM002', 'TH003', '2025-07-29 17:43:25', NULL),
('SP405', 'Giày thể thao Puma Run', 700000.00, 30, 'Giày chạy bộ chuyên nghiệp, đế êm ái', 'PG101.jpg', 'DM003', 'TH003', '2025-07-29 17:43:25', NULL),
('SP406', 'Giày thể thao Puma Street', 650000.00, 35, 'Giày phong cách đường phố, bền bỉ', 'PG201.jpg', 'DM003', 'TH003', '2025-07-29 17:43:25', NULL),
('SP407', 'Set thể thao Puma Active - Xanh Đen', 900000.00, 25, 'Bộ áo croptop xanh mát mắt kết hợp váy đen thanh lịch, lý tưởng cho phong cách thể thao hiện đại', 'PS101.jpg', 'DM004', 'TH003', '2025-07-29 17:43:25', NULL),
('SP408', 'Set thể thao Puma Casual - Vàng Xanh', 850000.00, 30, 'Bộ áo vàng tay dài ấm áp phối cùng váy xanh rêu độc đáo, hoàn hảo cho ngày năng động', 'PS201.jpg', 'DM004', 'TH003', '2025-07-29 17:43:25', NULL),
('SP409', 'Nón thể thao Puma Cap - Đen', 200000.00, 80, 'Nón lưỡi trai đen phong cách, bảo vệ khỏi nắng khi tập luyện', 'PN101.jpg', 'DM005', 'TH003', '2025-07-29 17:43:25', NULL),
('SP410', 'Nón thể thao Puma Snapback - Xanh', 250000.00, 70, 'Nón snapback xanh thời thượng, lý tưởng cho phong cách đường phố', 'PN201.jpg', 'DM005', 'TH003', '2025-07-29 17:43:25', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thuong_hieu`
--

CREATE TABLE `thuong_hieu` (
  `MATHUONGHIEU` varchar(10) NOT NULL,
  `TENTHUONGHIEU` varchar(255) NOT NULL,
  `NGAYTAO` datetime DEFAULT current_timestamp(),
  `NGAYSUA` datetime DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `thuong_hieu`
--

INSERT INTO `thuong_hieu` (`MATHUONGHIEU`, `TENTHUONGHIEU`, `NGAYTAO`, `NGAYSUA`) VALUES
('TH001', 'Nike', '2025-06-06 00:00:00', NULL),
('TH002', 'Adidas', '2025-06-06 00:00:00', NULL),
('TH003', 'Puma', '2025-06-06 00:00:00', NULL),
('TH004', 'Under Armour', '2025-06-06 00:00:00', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ton_kho`
--

CREATE TABLE `ton_kho` (
  `MASP` varchar(10) NOT NULL,
  `SOLUONGTON` int(11) NOT NULL,
  `LUONGXUAT` int(11) NOT NULL,
  `SOLUONGCUOI` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `ton_kho`
--

INSERT INTO `ton_kho` (`MASP`, `SOLUONGTON`, `LUONGXUAT`, `SOLUONGCUOI`) VALUES
('SP101', 99, 1, 98),
('SP102', 147, 3, 146),
('SP103', 49, 1, 48),
('SP104', 100, 0, 100),
('SP105', 57, 3, 56),
('SP106', 30, 0, 30),
('SP107', 100, 0, 100),
('SP108', 80, 0, 80),
('SP109', 200, 0, 200),
('SP110', 100, 0, 100),
('SP201', 100, 0, 100),
('SP202', 100, 0, 100),
('SP203', 50, 0, 50),
('SP204', 100, 0, 100),
('SP205', 60, 0, 60),
('SP206', 30, 0, 30),
('SP207', 100, 0, 100),
('SP208', 80, 0, 80),
('SP209', 50, 0, 50),
('SP210', 100, 0, 100),
('SP301', 100, 0, 100),
('SP302', 150, 0, 150),
('SP303', 50, 0, 50),
('SP304', 100, 0, 100),
('SP305', 60, 0, 60),
('SP306', 30, 0, 30),
('SP307', 100, 0, 100),
('SP308', 80, 0, 80),
('SP309', 50, 0, 50),
('SP310', 100, 0, 100),
('SP401', 45, 0, 45),
('SP402', 55, 0, 55),
('SP403', 70, 0, 70),
('SP404', 65, 0, 65),
('SP405', 30, 0, 30),
('SP406', 35, 0, 35),
('SP407', 25, 0, 25),
('SP408', 30, 0, 30),
('SP409', 80, 0, 80),
('SP410', 70, 0, 70);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `user_id` varchar(10) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(50) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `status` varchar(20) DEFAULT 'Active',
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT NULL ON UPDATE current_timestamp(),
  `profile_image` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `role`, `email`, `status`, `created_at`, `updated_at`, `profile_image`) VALUES
('US001', 'Admin1', '123', 'Admin', 'Admin1@gmail.com', 'Active', '2025-07-25 07:47:13', '2025-08-04 13:31:00', 'US001.jpg'),
('US002', 'Admin2', '123', 'Admin', 'Admin2@gmail.com', 'Active', '2025-07-25 07:47:13', '2025-08-04 13:31:00', 'US002.jpg'),
('US003', 'Manager1', '123', 'Manager', 'Manager1@gmail.com', 'Active', '2025-07-25 07:47:13', '2025-08-04 13:31:00', 'US003.jpg'),
('US004', 'Manager2', '123', 'Manager', 'Manager2@gmail.com', 'Active', '2025-07-25 07:47:13', '2025-08-04 13:31:00', 'US004.jpg'),
('US005', 'Thao', '1', 'Employee', 'nguyenthg@gmail.com', 'Active', '2025-07-25 07:47:13', '2025-08-04 13:31:00', 'US005.jpg'),
('US006', 'Ha', '123\r\n', 'Employee', 'tranvanh@gmail.com', 'Active', '2025-07-25 07:47:13', '2025-08-04 13:31:18', 'US006.jpg');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
