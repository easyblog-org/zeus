package top.easyblog.titan.service;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author: frank.huang
 * @date: 2022-01-29 18:58
 */
@Slf4j
@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    @Qualifier("loginRedisScript")
    private DefaultRedisScript<String> loginRedisScript;

    @Autowired
    @Qualifier("logoutRedisScript")
    private DefaultRedisScript<String> logoutRedisScript;

    /**
     * 指定缓存失效时间，单位s
     *
     * @param key  键
     * @param time 时间(秒)
     */
    public void expire(String key, long time, TimeUnit timeUnit) {
        try {
            if (time >= 0) {
                stringRedisTemplate.expire(key, time, timeUnit);
            } else {
                log.error("redis expire time must be greater than 0");
                throw new IllegalArgumentException("redis expire time must be greater than 0");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 获取key过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public Long getExpire(String key) {
        try {
            return stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0L;
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean exists(String key) {
        try {
            return stringRedisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public Boolean delete(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                return stringRedisTemplate.delete(key[0]);
            } else {
                Long deleted = stringRedisTemplate.delete(CollectionUtils.arrayToList(key));
                return Objects.nonNull(deleted) && deleted > 0;
            }
        }
        return false;
    }


    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key 键
     * @return true成功 false失败
     */
    public boolean set(String key, String value) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        try {
            stringRedisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }

    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, String value, Long time, TimeUnit timeUnit) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        if (Objects.isNull(time) || time > 0) {
            stringRedisTemplate.opsForValue().set(key, value, time, timeUnit);
        } else {
            stringRedisTemplate.opsForValue().set(key, value);
        }
        return true;
    }

    public String getAndSet(String key, String value) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        return stringRedisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public Long incr(String key, long delta) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public Long decr(String key, long delta) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        return stringRedisTemplate.opsForValue().increment(key, -delta);
    }

    //================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        return stringRedisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        return stringRedisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        try {
            stringRedisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map, Long time, TimeUnit timeUnit) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        try {
            stringRedisTemplate.opsForHash().putAll(key, map);
            expire(key, time, timeUnit);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        try {
            stringRedisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param h    键
     * @param hk   项
     * @param hv   值
     * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String h, String hk, Object hv, long time, TimeUnit timeUnit) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(h), "hash key can not be null");
        Preconditions.checkArgument(StringUtils.isNotEmpty(hk), "key can not be null");
        try {
            stringRedisTemplate.opsForHash().put(h, hk, hv);
            expire(h, time, timeUnit);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public Long hdel(String key, Object... item) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "hash key can not be null");
        return stringRedisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "hash key can not be null");
        return stringRedisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key   键
     * @param item  项
     * @param delta 要增加几
     * @return
     */
    public double hincr(String key, String item, double delta) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "hash key can not be null");
        return stringRedisTemplate.opsForHash().increment(key, item, delta);
    }

    /**
     * hash递减
     *
     * @param key   键
     * @param item  项
     * @param delta 要减少
     * @return
     */
    public double hdecr(String key, String item, double delta) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "hash key can not be null");
        return stringRedisTemplate.opsForHash().increment(key, item, -delta);
    }

    //===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    public List<String> lGet(String key, long start, long end) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        Preconditions.checkArgument(start < end, "list start must not greater than end");
        try {
            return stringRedisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public Long lGetListSize(String key) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        try {
            return stringRedisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public String lGetIndex(String key, long index) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        try {
            return stringRedisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, String value) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        try {
            stringRedisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, String value, long time, TimeUnit timeUnit) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        try {
            stringRedisTemplate.opsForList().rightPush(key, value);
            expire(key, time, timeUnit);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, List<String> value) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        try {
            stringRedisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, List<String> value, long time, TimeUnit timeUnit) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        if (CollectionUtils.isEmpty(value)) {
            return false;
        }
        try {
            stringRedisTemplate.opsForList().rightPushAll(key, value);
            expire(key, time, timeUnit);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, String value) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        Preconditions.checkArgument(index >= 0, "index must greater than 0");
        Long size = lGetListSize(key);
        Preconditions.checkArgument(Objects.nonNull(size) && index < size, "index must less than list size:" + size);
        try {
            stringRedisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public Long lRemove(String key, long count, String value) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        Long size = lGetListSize(key);
        Preconditions.checkArgument(Objects.nonNull(size) && count < size, "removed elements count must less than list size:" + size);
        try {
            return stringRedisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        }
    }


    //============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<String> sGet(String key) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        try {
            return stringRedisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public Boolean sHasKey(String key, String value) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        try {
            return stringRedisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long sSet(String key, String... values) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        try {
            return stringRedisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param member 值 可以是多个
     * @return 成功个数
     */
    public Long sadd(String key, String[] member, long time, TimeUnit timeUnit) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        try {
            Long count = stringRedisTemplate.opsForSet().add(key, member);
            expire(key, time, timeUnit);
            return count;
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        }
    }

    /**
     * 获取set中成员的总数
     *
     * @param key 键
     * @return
     */
    public Long scard(String key) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        try {
            return stringRedisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        }
    }

    /**
     * 移除set中一个或多个
     *
     * @param key     键
     * @param members 需要移除的一个或多个成员
     * @return 移除的个数
     */
    public Long srem(String key, Object... members) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        try {
            return stringRedisTemplate.opsForSet().remove(key, members);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        }
    }


    //====================================ZSet=============================================

    /**
     * 向zset中添加一个成员
     *
     * @param key
     * @param member
     * @param score
     * @return
     */
    public Boolean zadd(String key, String member, double score) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        try {
            return stringRedisTemplate.opsForZSet().add(key, member, score);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 向zset中批量添加成员
     *
     * @param key
     * @param set
     * @return
     */
    public Long zadd(String key, Set<ZSetOperations.TypedTuple<String>> set) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        try {
            return stringRedisTemplate.opsForZSet().add(key, set);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        }
    }

    /**
     * 给zset中一个成员的分数增加或减去score
     *
     * @param key
     * @param member
     * @param score
     * @return
     */
    public Double zincrby(String key, String member, double score) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        try {
            return stringRedisTemplate.opsForZSet().incrementScore(key, member, score);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0.0;
        }
    }

    /**
     * 获取一个zset中一个成员的分数
     *
     * @param key
     * @param member
     * @return
     */
    public Double zscore(String key, String member) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        try {
            return stringRedisTemplate.opsForZSet().score(key, member);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0.0;
        }
    }

    /**
     * 获取得分数介于min 以及 max 其间的成员从高到低排序的排序集。
     *
     * @param key 键
     * @param min 分数下界（包括）
     * @param max 分数上界 （包括）
     * @return
     */
    public Set<String> zRevRangeByScore(String key, double min, double max) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "key can not be null");
        try {
            return stringRedisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }


    //==========================================Lua 定制化脚本========================================================//

    /**
     * 保存登录token脚本
     *
     * @param keys
     * @param args
     * @return
     */
    public String storageToken(List<String> keys, Object... args) {
        return stringRedisTemplate.execute(loginRedisScript, keys, args);
    }

    /**
     * 删除token
     *
     * @param keys
     * @param args
     * @return
     */
    public String logout(List<String> keys, Object... args) {
        return stringRedisTemplate.execute(logoutRedisScript, keys, args);
    }


}
