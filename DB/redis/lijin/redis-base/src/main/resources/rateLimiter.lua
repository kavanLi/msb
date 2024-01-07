--java端送入三个参数（1个key,2个param  ）string
--limitKey(redi中key的值)
local key =KEYS[1];
--limit(次数)
local times = ARGV[1];
--expire(秒S)
local expire = ARGV[2];
--对key-value中的 value +1的操作  返回一个结果

local afterval=  redis.call('incr',key);
if afterval ==1 then --第一次
    redis.call('expire',key,tonumber(expire) )  --失效时间(1S)  TLL 1S
    return 1; --第一次不会进行限制
end
--不是第一次，进行判断
if afterval > tonumber(times) then
    --限制了
    return 0;
end

return 1;

