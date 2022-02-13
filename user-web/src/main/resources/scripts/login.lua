--用户登录token控制脚本
local accountKey=KEYS[1]
local userKey=KEYS[2]

local accountVal=ARGV[1]
local userVal=ARGV[2]
local expire=tonumber(ARGV[3])

local accountKeyExist = redis.call("EXISTS", accountKey);

if (accountKeyExist < 1) then
    redis.call("SET",accountKey,accountVal)
    redis.call("SET",userKey,userVal)
    redis.call("EXPIRE",accountKey,expire)
    redis.call("EXPIRE",userKey,expire)
    return accountVal
else
  return tostring(redis.call("GET",accountKey))
end