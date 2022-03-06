#添加登录token
ALTER TABLE sign_in_log
    ADD COLUMN `token` varchar(100) not null default '' after `user_id`;
#修改phone_area_code类型
ALTER TABLE phone_auth
    MODIFY COLUMN `phone_area_code` varchar(10) not null default '';

#sign_in_log增加account_id列
ALTER TABLE sign_in_log
    ADD COLUMN `account_id` bigint not null default '' after `user_id`;