---退出登录

local accountKey = KEYS[1]

local token = redis.call("GET",accountKey)

if (token == nil or token == false) then
  return nil;
else
  redis.call("DEL",accountKey)
  local userInfo=redis.call("GET","user:token:"..tostring(token))
  redis.call("DEL","user:token:"..tostring(token))
  return userInfo
end