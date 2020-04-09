# fun
学习 使用redis lua 实现 限流
相比Redis事务，Lua脚本的优点：

减少网络开销： 使用Lua脚本，无需向Redis 发送多次请求，执行一次即可，减少网络传输
原子操作：Redis 将整个Lua脚本作为一个命令执行，原子，无需担心并发
复用：Lua脚本一旦执行，会永久保存 Redis 中,，其他客户端可复用

Lua脚本大致逻辑如下：
-- 获取调用脚本时传入的第一个key值（用作限流的 key）
local key = KEYS[1]
-- 获取调用脚本时传入的第一个参数值（限流大小）
local limit = tonumber(ARGV[1])

-- 获取当前流量大小
local curentLimit = tonumber(redis.call('get', key) or "0")

-- 是否超出限流
if curentLimit + 1 > limit then
    -- 返回(拒绝)
    return 0
else
    -- 没有超出 value + 1
    redis.call("INCRBY", key, 1)
    -- 设置过期时间
    redis.call("EXPIRE", key, 2)
    -- 返回(放行)
    return 1
end
复制代码
通过KEYS[1] 获取传入的key参数
通过ARGV[1]获取传入的limit参数
redis.call方法，从缓存中get和key相关的值，如果为null那么就返回0
接着判断缓存中记录的数值是否会大于限制大小，如果超出表示该被限流，返回0
如果未超过，那么该key的缓存值+1，并设置过期时间为1秒钟以后，并返回缓存值+1

