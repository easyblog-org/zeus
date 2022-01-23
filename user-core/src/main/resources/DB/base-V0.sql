--系统SQL文件，每个版本的SQL变更建议都写在对应版本SQL文件中，SQL文件命名建议：项目名称-{版本}，比如user-V1

create table `user`
(
    id            int(64) primary key AUTO_INCREMENT comment '用户id',
    nick_name     varchar(20) default '' comment '昵称',
    integration   int(32) default 0 comment '积分',
    header_img_id int(64) default 0 comment '头像',
    level         int(10) default 1 comment '等级',
    visit         int(64) default 0 comment '用户文章访问量',
    active        byte        default 1 comment '用户账户状态',
    create_time   timestamp   default current_timestamp comment '创建时间',
    update_time   timestamp   default current_timestamp comment '更新时间'
);

create table `account`
(
    id            int(64) primary key AUTO_INCRMENT comment 'id',
    user_id       int(64) not null comment '用户id',
    identity_type int(7) not null default '' comment '系统用户 1、邮箱 2、手机 3，QQ 4、微信 5、微博 6、GitHub 7、Gitlab 8',
    identifier    varchar(100) not null default '' comment '身份唯一标识，如：登录账号、邮箱地址、手机号码、QQ号码、微信号、微博号',
    credential    varchar(256) not null default '' comment '站内账号是密码、第三方登录是Token',
    verified      int(7) not null default 0 comment '授权账号是否被验证',
    status        int(7) not null default 0 comment '状态状态',
    create_time   timestamp             default current_timestamp comment '创建时间',
    update_time   timestamp             default current_timestamp comment '更新时间'
);

create table `phone_auth`
(
    id              int(64) primary key AUTO_INCRMENT comment 'id',
    phone_area_code int(10) default 0 comment '国际区号',
    phone           varchar(11) default '' comment '手机号',
    create_time     timestamp   default current_timestamp comment '创建时间',
    update_time     timestamp   default current_timestamp comment '更新时间'
);

create table user_header_img
(
    id          int(64) primary key AUTO_INCRMENT comment 'id',
    header_img_url not null default '' comment '头像url地址',
    user_id     int(64) not null default null comment '用户id',
    status      int(7) not null default 0 comment '状态',
    create_time timestamp   default current_timestamp comment '创建时间',
    update_time timestamp   default current_timestamp comment '更新时间'
);

create table sign_in_log
(
    id               int(64) primary key AUTO_INCRMENT comment 'id',
    user_id          int(64) not null default null comment '用户id',
    status           int(7) not null default 0 comment '状态',
    ip               varchar(128) not null default '' comment '登录设备ip',
    device           varchar(100) not null default '' comment '登录设备名称',
    operation_system varchar(100) not null default '' comment '登录设备操作系统',
    location         varchar(100) not null default '' comment '登录基于ip模糊定位地址',
    create_time      timestamp             default current_timestamp comment '创建时间',
    update_time      timestamp             default current_timestamp comment '更新时间'
);

create table phone_area_code
(
    id           int(64) primary key AUTO_INCRMENT comment 'id',
    crown_code   varchar(10)  not null default '' comment '国际冠码',
    country_code varchar(100) not null default '' comment '国家码',
    area_code    varchar(10)  not null default '' comment '区域码',
    area_name    varchar(20)  not null default '' comment '区域名',
    create_time  timestamp             default current_timestamp comment '创建时间',
    update_time  timestamp             default current_timestamp comment '更新时间'
);