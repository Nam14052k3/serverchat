-- Sử dụng cơ sở dữ liệu
USE chat_application;
GO

-- Xóa ràng buộc khóa ngoại trong bảng [user_account]
IF OBJECT_ID(N'user_account', N'U') IS NOT NULL
BEGIN
    ALTER TABLE [user_account] DROP CONSTRAINT FK_UserAccount_User;
END
GO

-- Xóa bảng [user_account]
IF OBJECT_ID(N'user_account', N'U') IS NOT NULL
BEGIN
    DROP TABLE [user_account];
END
GO

-- Xóa bảng [user]
IF OBJECT_ID(N'[user]', N'U') IS NOT NULL
BEGIN
    DROP TABLE [user];
END
GO

-- Tạo bảng [user]
CREATE TABLE [user] (
    [UserID] INT NOT NULL IDENTITY(1,1),
    [UserName] NVARCHAR(255) NULL,
    [Password] NVARCHAR(255) NULL,
    CONSTRAINT PK_User PRIMARY KEY CLUSTERED ([UserID])
);
GO

-- Tạo bảng [user_account]
CREATE TABLE [user_account] (
    [UserID] INT NOT NULL,
    [UserName] NVARCHAR(255) NULL,
    [Gender] CHAR(1) NOT NULL DEFAULT '',
    [Image] VARBINARY(MAX) NULL,
    [ImageString] NVARCHAR(255) NULL DEFAULT '',
    [Status] CHAR(1) NOT NULL DEFAULT '1',
    CONSTRAINT PK_UserAccount PRIMARY KEY CLUSTERED ([UserID]),
    CONSTRAINT FK_UserAccount_User FOREIGN KEY ([UserID])
        REFERENCES [user] ([UserID]) ON DELETE CASCADE ON UPDATE CASCADE
);
GO
INSERT INTO [user] ([UserName], [Password])
VALUES 
    ('user1', '123'),
    ('user2', '123'),
    ('user3', '123');
GO

-- Kiểm tra kết quả
SELECT * FROM [user];
GO