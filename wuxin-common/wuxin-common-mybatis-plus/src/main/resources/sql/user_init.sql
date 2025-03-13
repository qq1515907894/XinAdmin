CREATE TABLE `user` (
                        `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键 ID',
                        `name` VARCHAR(100) NOT NULL COMMENT '用户名',
                        `age` INT DEFAULT NULL COMMENT '年龄',
                        `email` VARCHAR(255) DEFAULT NULL COMMENT '电子邮箱'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
