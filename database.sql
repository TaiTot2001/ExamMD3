CREATE DATABASE tcomplex;

USE tcomplex;

CREATE TABLE MatBang (
    maMatBang VARCHAR(10) PRIMARY KEY,
    trangThai ENUM('Trống','Hạ tầng','Đầy đủ') NOT NULL,
    dienTich DOUBLE NOT NULL CHECK (dienTich > 20),
    tang INT NOT NULL CHECK (tang BETWEEN 1 AND 15),
    loaiMatBang ENUM('Văn phòng chia sẻ','Văn phòng trọn gói') NOT NULL,
    giaTien DOUBLE NOT NULL CHECK (giaTien > 1000000),
    ngayBatDau DATE NOT NULL,
    ngayKetThuc DATE NOT NULL,
    CHECK (DATEDIFF(ngayKetThuc, ngayBatDau) >= 180)
);

ALTER TABLE MatBang CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
