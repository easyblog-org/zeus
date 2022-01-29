use easyblog-dev;

CREATE TABLE `account`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id`       bigint       NOT NULL COMMENT '用户id',
    `identity_type` int          NOT NULL DEFAULT '0' COMMENT '系统用户 1、邮箱 2、手机 3，QQ 4、微信 5、微博 6、GitHub 7、Gitee 8',
    `identifier`    varchar(100) NOT NULL DEFAULT '' COMMENT '身份唯一标识，如：登录账号、邮箱地址、手机号码、QQ号码、微信号、微博号',
    `credential`    varchar(256) NOT NULL DEFAULT '' COMMENT '站内账号是密码、第三方登录是Token',
    `verified`      int          NOT NULL DEFAULT '0' COMMENT '授权账号是否被验证',
    `status`        int          NOT NULL DEFAULT '0' COMMENT '状态状态',
    `create_time`   timestamp    NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   timestamp    NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_uid` (`user_id`),
    KEY `idx_account_entity` (`identity_type`, `identifier`, `credential`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `phone_area_code`
(
    `id`           bigint       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `crown_code`   varchar(10)  NOT NULL DEFAULT '' COMMENT '国际冠码',
    `country_code` varchar(100) NOT NULL DEFAULT '' COMMENT '国家码',
    `area_code`    varchar(10)  NOT NULL DEFAULT '' COMMENT '区域码',
    `area_name`    varchar(20)  NOT NULL DEFAULT '' COMMENT '区域名',
    `create_time`  timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `phone_auth`
(
    `id`              bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    `phone_area_code` int         DEFAULT '0' COMMENT '国际区号',
    `phone`           varchar(11) DEFAULT '' COMMENT '手机号',
    `create_time`     timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `sign_in_log`
(
    `id`               bigint       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id`          bigint       NOT NULL DEFAULT '0' COMMENT '用户id',
    `status`           int          NOT NULL DEFAULT '0' COMMENT '状态',
    `ip`               varchar(128) NOT NULL DEFAULT '' COMMENT '登录设备ip',
    `device`           varchar(100) NOT NULL DEFAULT '' COMMENT '登录设备名称',
    `operation_system` varchar(100) NOT NULL DEFAULT '' COMMENT '登录设备操作系统',
    `location`         varchar(100) NOT NULL DEFAULT '' COMMENT '登录基于ip模糊定位地址',
    `create_time`      timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY                `idx_uid` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `user`
(
    `id`            bigint NOT NULL AUTO_INCREMENT COMMENT '用户id',
    `nick_name`     varchar(20) DEFAULT '' COMMENT '昵称',
    `integration`   int         DEFAULT '0' COMMENT '积分',
    `header_img_id` int         DEFAULT '0' COMMENT '头像',
    `level`         int         DEFAULT '1' COMMENT '等级',
    `visit`         int         DEFAULT '0' COMMENT '用户文章访问量',
    `active`        int         DEFAULT '1' COMMENT '用户账户状态',
    `create_time`   timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY             `idx_nick_name` (`nick_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `user_header_img`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `header_img_url` varchar(500) NOT NULL DEFAULT '' COMMENT '头像url地址',
    `user_id`        bigint       NOT NULL DEFAULT '0' COMMENT '用户id',
    `status`         int          NOT NULL DEFAULT '0' COMMENT '状态',
    `create_time`    timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY              `idx_uid_status` (`user_id`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
