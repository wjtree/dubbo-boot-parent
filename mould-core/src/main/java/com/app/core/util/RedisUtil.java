package com.app.core.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Log4j2
public class RedisUtil {
    /**
     * Spring Redis 基础操作模板
     */
    private static StringRedisTemplate redisTemp;
    /**
     * Redis 数据类型-字符串
     */
    private static ValueOperations<String, String> valueOps;
    /**
     * Redis 数据类型-字典
     */
    private static HashOperations<String, String, String> hashOps;
    /**
     * Redis 数据类型-列表
     */
    private static ListOperations<String, String> listOps;
    /**
     * Redis 数据类型-集合
     */
    private static SetOperations<String, String> setOps;
    /**
     * Redis 数据类型-有序集合
     */
    private static ZSetOperations<String, String> zSetOps;

    static {
        RedisUtil.redisTemp = IocUtil.getBean(StringRedisTemplate.class);
        // 初始化各个数据类型模版
        RedisUtil.valueOps = redisTemp.opsForValue();
        RedisUtil.hashOps = redisTemp.opsForHash();
        RedisUtil.listOps = redisTemp.opsForList();
        RedisUtil.setOps = redisTemp.opsForSet();
        RedisUtil.zSetOps = redisTemp.opsForZSet();
    }

    /**
     * 发布消息
     *
     * @param channel 发布频道
     * @param message 消息内容
     */
    public static void publish(String channel, String message) {
        redisTemp.convertAndSend(channel, message);
    }

    /**
     * 获取所有缓存键
     * <p>通过正则表达式匹配</p>
     *
     * @param pattern
     * @return
     */
    public static Set<String> keys(String pattern) {
        return redisTemp.keys(pattern);
    }

    /**
     * 单个删除
     *
     * @param key
     */
    public static void delete(String key) {
        redisTemp.delete(key);
    }

    /**
     * 批量删除
     *
     * @param keys
     */
    public static void deleteAll(Collection<String> keys) {
        redisTemp.delete(keys);
    }

    /**
     * 批量删除
     * <p>通过正则表达式匹配</p>
     *
     * @param pattern
     */
    public static void deleteAll(String pattern) {
        redisTemp.delete(keys(pattern));
    }

    /**
     * 字符串-单个存值
     *
     * @param key
     * @param value
     */
    public static void valueSet(String key, String value) {
        valueOps.set(key, value);
    }

    /**
     * 字符串-单个定时存值
     *
     * @param key
     * @param value
     * @param timeout 有效时间
     * @param unit    时间单位
     */
    public static void valueSet(String key, String value, long timeout, TimeUnit unit) {
        valueOps.set(key, value, timeout, unit);
    }

    /**
     * 字符串-批量存值
     *
     * @param map
     */
    public static void valueMultiSet(Map<String, String> map) {
        valueOps.multiSet(map);
    }

    /**
     * 字符串-单个取值
     *
     * @param key
     * @return
     */
    public static String valueGet(String key) {
        return valueOps.get(key);
    }

    /**
     * 字符串-批量取值
     *
     * @param keys
     * @return
     */
    public static List<String> valueMultiGet(Collection<String> keys) {
        return valueOps.multiGet(keys);
    }

    /**
     * 字符串-批量取值
     * <p>通过正则表达式匹配</p>
     *
     * @param pattern
     * @return
     */
    public static List<String> valueMultiGet(String pattern) {
        return valueOps.multiGet(keys(pattern));
    }

    /**
     * 字典-单个存值
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public static void hashPut(String key, String hashKey, String value) {
        hashOps.put(key, hashKey, value);
    }

    /**
     * 字典-批量存值
     *
     * @param key
     * @param map
     */
    public static void hashPutAll(String key, Map<String, String> map) {
        hashOps.putAll(key, map);
    }

    /**
     * 字典-单个取值
     *
     * @param key
     * @param hashKey
     * @return
     */
    public static String hashGet(String key, String hashKey) {
        return hashOps.get(key, hashKey);
    }

    /**
     * 字典-批量取值
     *
     * @param key
     * @param hashKeys
     * @return
     */
    public static List<String> hashMultiGet(String key, Collection<String> hashKeys) {
        return hashOps.multiGet(key, hashKeys);
    }

    /**
     * 字典-遍历键和值
     *
     * @param key
     * @return
     */
    public static Map<String, String> hashEntries(String key) {
        return hashOps.entries(key);
    }

    /**
     * 字典-遍历键
     *
     * @param key
     * @return
     */
    public static Set<String> hashKeys(String key) {
        return hashOps.keys(key);
    }

    /**
     * 字典-遍历值
     *
     * @param key
     * @return
     */
    public static List<String> hashValues(String key) {
        return hashOps.values(key);
    }

    /**
     * 字典-删除
     *
     * @param key
     * @param hashKeys
     */
    public static void hashDelete(String key, String... hashKeys) {
        hashOps.delete(key, hashKeys);
    }

    /**
     * 列表-左边单个存值
     *
     * @param key
     * @param value
     * @return
     */
    public static Long listLeftPush(String key, String value) {
        return listOps.leftPush(key, value);
    }

    /**
     * 列表-左边批量存值
     *
     * @param key
     * @param values
     * @return
     */
    public static Long listLeftPushAll(String key, Collection<String> values) {
        return listOps.leftPushAll(key, values);
    }

    /**
     * 列表-右边单个存值
     *
     * @param key
     * @param value
     * @return
     */
    public static Long listRightPush(String key, String value) {
        return listOps.rightPush(key, value);
    }

    /**
     * 列表-右边批量存值
     *
     * @param key
     * @param values
     * @return
     */
    public static Long listRightPushAll(String key, Collection<String> values) {
        return listOps.rightPushAll(key, values);
    }

    /**
     * 列表-指定下标更新
     *
     * @param key
     * @param index
     * @param value
     */
    public static void listSet(String key, long index, String value) {
        // 先判断是否存在当前下标的元素
        if (listOps.size(key) > index)
            listOps.set(key, index, value);
    }

    /**
     * 列表-左边取值
     *
     * @param key
     * @return
     */
    public static String listLeftPop(String key) {
        return listOps.leftPop(key);
    }

    /**
     * 列表-右边取值
     *
     * @param key
     * @return
     */
    public static String listRightPop(String key) {
        return listOps.rightPop(key);
    }

    /**
     * 列表-指定下标取值
     *
     * @param key
     * @param index
     * @return
     */
    public static String listIndex(String key, long index) {
        return listOps.index(key, index);
    }

    /**
     * 列表-指定下标区间查看（不取值）
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static List<String> listRange(String key, long start, long end) {
        return listOps.range(key, start, end);
    }

    /**
     * 列表-查看所有列表值（不取值）
     *
     * @param key
     * @return
     */
    public static List<String> listRangeAll(String key) {
        return listOps.range(key, 0, listOps.size(key));
    }

    /**
     * 列表-指定下标移除
     *
     * @param key
     * @param index
     * @param value
     * @return
     */
    public static Long listRemove(String key, long index, String value) {
        return listOps.remove(key, index, value);
    }

    /**
     * 集合-添加一个或多个元素
     *
     * @param key
     * @param values
     * @return
     */
    public static Long setAdd(String key, String... values) {
        return setOps.add(key, values);
    }

    /**
     * 集合-移动集合元素
     *
     * @param key
     * @param value
     * @param destKey
     * @return
     */
    public static Boolean setMove(String key, String value, String destKey) {
        return setOps.move(key, value, destKey);
    }

    /**
     * 集合-判断是否存在集合元素
     *
     * @param key
     * @param value
     * @return
     */
    public static Boolean setIsMember(String key, String value) {
        return setOps.isMember(key, value);
    }

    /**
     * 集合-遍历集合元素
     *
     * @param key
     * @return
     */
    public static Set<String> setMembers(String key) {
        return setOps.members(key);
    }

    /**
     * 集合-多个集合的元素交集
     *
     * @param key
     * @param otherKey
     * @return
     */
    public static Set<String> setIntersect(String key, String otherKey) {
        return setOps.intersect(key, otherKey);
    }

    /**
     * 集合-多个集合的元素并集
     *
     * @param key
     * @param otherKey
     * @return
     */
    public static Set<String> setUnion(String key, String otherKey) {
        return setOps.union(key, otherKey);
    }

    /**
     * 集合-多个集合的元素差集
     *
     * @param key
     * @param otherKey
     * @return
     */
    public static Set<String> setDifference(String key, String otherKey) {
        return setOps.difference(key, otherKey);
    }

    /**
     * 集合-移除一个或多个元素
     *
     * @param key
     * @param values
     * @return
     */
    public static Long setRemove(String key, String... values) {
        return setOps.remove(key, values);
    }

    /**
     * 有序集合-添加
     *
     * @param key
     * @param value
     * @param score
     * @return
     */
    public static Boolean zSetAdd(String key, String value, double score) {
        return zSetOps.add(key, value, score);
    }

    /**
     * 有序集合-按下标顺序查询
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<String> zSetRange(String key, long start, long end) {
        return zSetOps.range(key, start, end);
    }

    /**
     * 有序集合-按下标倒序查询
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<String> zSetReverseRange(String key, long start, long end) {
        return zSetOps.reverseRange(key, start, end);
    }

    /**
     * 有序集合-按分数顺序查询
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static Set<String> zSetRangeByScore(String key, double min, double max) {
        return zSetOps.rangeByScore(key, min, max);
    }

    /**
     * 有序集合-按分数倒序查询
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static Set<String> zSetReverseRangeByScore(String key, double min, double max) {
        return zSetOps.reverseRangeByScore(key, min, max);
    }

    /**
     * 有序集合-顺序查询集合元素的下标
     *
     * @param key
     * @param value
     * @return
     */
    public static Long zSetRank(String key, String value) {
        return zSetOps.rank(key, value);
    }

    /**
     * 有序集合-倒序查询集合元素的下标
     *
     * @param key
     * @param value
     * @return
     */
    public static Long zSetReverseRank(String key, String value) {
        return zSetOps.reverseRank(key, value);
    }

    /**
     * 有序集合-查询集合元素的分数
     *
     * @param key
     * @param value
     * @return
     */
    public static Double zSetScore(String key, String value) {
        return zSetOps.score(key, value);
    }

    /**
     * 有序集合-批量删除
     *
     * @param key
     * @param values
     * @return
     */
    public static Long zSetRemove(String key, String... values) {
        return zSetOps.remove(key, values);
    }

    /**
     * 有序集合-按下标批量删除
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Long zSetRemoveRange(String key, long start, long end) {
        return zSetOps.removeRange(key, start, end);
    }

    /**
     * 有序集合-按分数批量删除
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static Long zSetRemoveRangeByScore(String key, double min, double max) {
        return zSetOps.removeRangeByScore(key, min, max);
    }
}