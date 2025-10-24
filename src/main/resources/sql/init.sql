-- 创建数据库
CREATE DATABASE IF NOT EXISTS lost_found_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE lost_found_system;

-- 普通用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(50) NOT NULL UNIQUE COMMENT '学号',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '账号名',
    college VARCHAR(100) COMMENT '学院',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    phone VARCHAR(20) COMMENT '手机号',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT='普通用户表';

-- 失物管理员表
CREATE TABLE IF NOT EXISTS lost_item_admins (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '账号名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    college VARCHAR(100) COMMENT '学院',
    office_location VARCHAR(100) COMMENT '办公地点',
    phone VARCHAR(20) COMMENT '手机号',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT='失物管理员表';

-- 总管理员表
CREATE TABLE IF NOT EXISTS super_admins (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '账号名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    college VARCHAR(100) COMMENT '学院',
    phone VARCHAR(20) COMMENT '手机号',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT='总管理员表';

-- 失物表
CREATE TABLE IF NOT EXISTS lost_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '物品名称',
    type INT NOT NULL COMMENT '物品类型 1:书 2:包 3:卡 4:钥匙 5:手机 6:手表 7:笔 8:伞 9:耳机 10:其他',
    color INT NOT NULL COMMENT '颜色 1:红色 2:浅红色 3:暗红色 4:绿色 5:浅绿色 6:暗绿色 7:蓝色 8:浅蓝色 9:深蓝色 10:黄色 11:橙色 12:紫色 13:粉红色 14:棕色 15:灰色 16:黑色 17:白色 18:其他',
    description TEXT COMMENT '物品描述',
    found_location VARCHAR(200) COMMENT '捡到地点',
    building INT COMMENT '楼栋编号',
    specific_location VARCHAR(50) COMMENT '具体位置',
    image_url VARCHAR(500) COMMENT '图片路径',
    publish_time DATETIME COMMENT '发布时间',
    is_claimed INT DEFAULT 0 COMMENT '是否被领取 0:未领取 1:已领取',
    claim_time DATETIME COMMENT '领取时间',
    admin_id BIGINT NOT NULL COMMENT '发布管理员ID',
    claimer_student_id VARCHAR(50) COMMENT '领取人学号',
    claimer_name VARCHAR(50) COMMENT '领取人姓名',
    claimer_phone VARCHAR(20) COMMENT '领取人手机号',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_type (type),
    INDEX idx_color (color),
    INDEX idx_building (building),
    INDEX idx_is_claimed (is_claimed),
    INDEX idx_admin_id (admin_id)
) COMMENT='失物表';

-- 已领取失物表
CREATE TABLE IF NOT EXISTS claimed_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    original_lost_item_id BIGINT NOT NULL COMMENT '原失物ID',
    name VARCHAR(100) NOT NULL COMMENT '物品名称',
    type INT NOT NULL COMMENT '物品类型',
    color INT NOT NULL COMMENT '颜色',
    description TEXT COMMENT '物品描述',
    found_location VARCHAR(200) COMMENT '捡到地点',
    building INT COMMENT '楼栋编号',
    specific_location VARCHAR(50) COMMENT '具体位置',
    image_url VARCHAR(500) COMMENT '图片路径',
    publish_time DATETIME COMMENT '发布时间',
    claim_time DATETIME COMMENT '领取时间',
    admin_id BIGINT NOT NULL COMMENT '发布管理员ID',
    claimer_student_id VARCHAR(50) COMMENT '领取人学号',
    claimer_name VARCHAR(50) COMMENT '领取人姓名',
    claimer_phone VARCHAR(20) COMMENT '领取人手机号',
    claim_status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING' COMMENT '申领状态',
    apply_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_admin_id (admin_id),
    INDEX idx_claim_time (claim_time),
    INDEX idx_claim_status (claim_status),
    INDEX idx_apply_time (apply_time),
    INDEX idx_admin_status (admin_id, claim_status)
) COMMENT='已领取失物表';

-- 插入测试数据
-- 普通用户 (明文密码: 123456)
INSERT INTO users (student_id, username, college, password, phone) VALUES
('2023001', 'student1', '计算机学院', '$2a$10$nv8DVmIEyR33Dqy16GcQFObnjAbdG8erEHNINwXm2Xg14Vs31Mz9u', '13800138001'),
('2023002', 'student2', '数学学院', '$2a$10$nv8DVmIEyR33Dqy16GcQFObnjAbdG8erEHNINwXm2Xg14Vs31Mz9u', '13800138002');

-- 失物管理员 (明文密码: 123456)
INSERT INTO lost_item_admins (username, password, college, office_location, phone) VALUES
('admin1', '$2a$10$nv8DVmIEyR33Dqy16GcQFObnjAbdG8erEHNINwXm2Xg14Vs31Mz9u', '计算机学院', '学友楼101', '13900139001'),
('admin2', '$2a$10$nv8DVmIEyR33Dqy16GcQFObnjAbdG8erEHNINwXm2Xg14Vs31Mz9u', '数学学院', '文综楼205', '13900139002');

-- 总管理员 (明文密码: 123456)
INSERT INTO super_admins (username, password, college, phone) VALUES
('superadmin', '$2a$10$nv8DVmIEyR33Dqy16GcQFObnjAbdG8erEHNINwXm2Xg14Vs31Mz9u', '校长办公室', '13700137001');

-- 测试失物数据
INSERT INTO lost_items (name, type, color, description, found_location, building, specific_location, is_claimed, admin_id) VALUES
('《高等数学》教材', 1, 7, '蓝色封面，有笔记', '[文综楼]201教室', 3, '201教室', 0, 1),
('黑色雨伞', 8, 16, '天堂牌长柄雨伞', '[学友楼]门口', 1, '门口', 0, 2),
('校园卡', 3, 7, '学生校园卡，姓名：张三', '[五坡食堂]2楼', 12, '2楼', 0, 1);