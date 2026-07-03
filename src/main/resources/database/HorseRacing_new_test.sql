-- ============================================================
-- Database: HorseRacing_new_test
-- Mô tả: Hệ thống quản lý giải đua ngựa (bản sửa lỗi)
-- ============================================================

USE master;
GO

-- Xóa database cũ nếu tồn tại (cẩn thận)
IF EXISTS (SELECT name FROM sys.databases WHERE name = N'HorseRacing_new_test')
BEGIN
    ALTER DATABASE HorseRacing_new_test SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE HorseRacing_new_test;
END
GO

-- Tạo database mới
CREATE DATABASE HorseRacing_new_test;
GO

USE HorseRacing_new_test;
GO

-- 1. Bảng Roles
CREATE TABLE roles (
    role_id INT IDENTITY(1,1) PRIMARY KEY,
    role_name NVARCHAR(50) NOT NULL UNIQUE
);
GO

-- 2. Bảng Users
CREATE TABLE users (
    user_id INT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(50) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    full_name NVARCHAR(100),
    email NVARCHAR(100) NOT NULL UNIQUE,
    phone NVARCHAR(20),
    enabled BIT DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE()
);
GO

-- 3. Bảng UserRoles
CREATE TABLE user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE
);
GO

-- 4. Bảng Tournaments
CREATE TABLE tournaments (
    tournament_id INT IDENTITY(1,1) PRIMARY KEY,
    tournament_name NVARCHAR(100) NOT NULL,
    description NVARCHAR(MAX),
    start_date DATE,
    end_date DATE
);
GO

-- 5. Bảng Races
CREATE TABLE races (
    race_id INT IDENTITY(1,1) PRIMARY KEY,
    tournament_id INT NOT NULL,
    race_name NVARCHAR(100) NOT NULL,
    race_date DATETIME,
    location NVARCHAR(200),
    FOREIGN KEY (tournament_id) REFERENCES tournaments(tournament_id)
);
GO

-- 6. Bảng Horses
CREATE TABLE horses (
    horse_id INT IDENTITY(1,1) PRIMARY KEY,
    horse_name NVARCHAR(100) NOT NULL,
    breed NVARCHAR(100),
    age INT,
    owner_id INT NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES users(user_id)
);
GO

-- 7. Bảng Registrations
CREATE TABLE registrations (
    registration_id INT IDENTITY(1,1) PRIMARY KEY,
    horse_id INT NOT NULL,
    race_id INT NOT NULL,
    status NVARCHAR(50) DEFAULT 'PENDING',
    FOREIGN KEY (horse_id) REFERENCES horses(horse_id),
    FOREIGN KEY (race_id) REFERENCES races(race_id),
    UNIQUE (horse_id, race_id)
);
GO

-- 8. Bảng RaceResults
CREATE TABLE race_results (
    result_id INT IDENTITY(1,1) PRIMARY KEY,
    race_id INT NOT NULL,
    horse_id INT NOT NULL,
    position INT,
    completion_time FLOAT,
    FOREIGN KEY (race_id) REFERENCES races(race_id),
    FOREIGN KEY (horse_id) REFERENCES horses(horse_id)
);
GO

-- 9. Bảng Predictions
CREATE TABLE predictions (
    prediction_id INT IDENTITY(1,1) PRIMARY KEY,
    spectator_id INT NOT NULL,
    race_id INT NOT NULL,
    predicted_horse_id INT NOT NULL,
    prediction_date DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (spectator_id) REFERENCES users(user_id),
    FOREIGN KEY (race_id) REFERENCES races(race_id),
    FOREIGN KEY (predicted_horse_id) REFERENCES horses(horse_id)
);
GO

-- 10. Thêm dữ liệu mẫu
INSERT INTO roles (role_name) VALUES 
    ('ADMIN'),
    ('HORSE_OWNER'),
    ('JOCKEY'),
    ('REFEREE'),
    ('SPECTATOR');
GO

-- BCrypt hash của "admin123"
INSERT INTO users (username, password, full_name, email, phone, enabled)
VALUES ('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMy.Mr4U0Kd1eZRr6s5hT5MqYqJZ9tWqYsq', 
        'Administrator', 'admin@horseracing.com', '0901234567', 1);
GO

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
GO

PRINT 'Database HorseRacing_new_test đã được tạo thành công!';
GO