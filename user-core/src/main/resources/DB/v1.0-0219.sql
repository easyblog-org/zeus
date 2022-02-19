---添加登录token
ALTER TABLE sign_in_log
    ADD COLUMN `token` varchar(100) not null default '' after `user_id`;