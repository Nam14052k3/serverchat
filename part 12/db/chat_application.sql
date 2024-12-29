/*
Navicat MySQL Data Transfer

Source Server         : SEVER 3305
Source Server Version : 50620
Source Host           : localhost:3305
Source Database       : chat_application

Target Server Type    : MYSQL
Target Server Version : 50620
File Encoding         : 65001

Date: 2021-06-15 00:27:33
*/

SET NOCOUNT ON;

-- Xóa bảng nếu đã tồn tại
IF OBJECT_ID('user', 'U') IS NOT NULL
    DROP TABLE [user];

-- Tạo bảng user
CREATE TABLE [user] (
    [UserID] INT NOT NULL IDENTITY(1,1),
    [UserName] NVARCHAR(255) NULL,
    [Password] NVARCHAR(255) NULL,
    PRIMARY KEY ([UserID])
);

-- Chèn dữ liệu vào bảng user
INSERT INTO [user] ([UserName], [Password]) 
VALUES 
    ('Bob', '123'),
    ('Charlie', 'securePass789'),
    ('Diana', 'qwerty123');

-- Truy vấn dữ liệu từ bảng user
SELECT * FROM [user];


