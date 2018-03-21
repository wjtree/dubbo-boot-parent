package com.app.core.util;

import com.whalin.MemCached.MemCachedClient;
import lombok.extern.log4j.Log4j2;

import java.util.Date;

@Log4j2
public class MemCachedUtil {
    /**
     * create a static client as most installs only need a single instance
     */
    private static MemCachedClient mcc = new MemCachedClient();

    /**
     * 向缓存添加新的键值对
     * <p>如果键已经存在，则之前的值将被替换</p>
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean set(String key, Object value) {
        return setExp(key, value, null);
    }

    /**
     * 向缓存添加新的键值对
     * <p>如果键已经存在，则之前的值将被替换</p>
     *
     * @param key
     * @param value
     * @param expire 过期时间
     * @return
     */
    public static boolean set(String key, Object value, Date expire) {
        return setExp(key, value, expire);
    }

    /**
     * 向缓存添加新的键值对
     * <p>如果键已经存在，则之前的值将被替换</p>
     *
     * @param key
     * @param value
     * @param expire 过期时间
     * @return
     */
    private static boolean setExp(String key, Object value, Date expire) {
        boolean flag = false;
        try {
            flag = mcc.set(key, value, expire);
        } catch (Exception e) {
            log.error("Memcached set命令出错", e);
        }
        return flag;
    }

    /**
     * 向缓存添加新的键值对
     * <p>仅当缓存中不存在键时添加</p>
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean add(String key, Object value) {
        return addExp(key, value, null);
    }

    /**
     * 向缓存添加新的键值对
     * <p>仅当缓存中不存在键时添加</p>
     *
     * @param key
     * @param value
     * @param expire 过期时间
     * @return
     */
    public static boolean add(String key, Object value, Date expire) {
        return addExp(key, value, expire);
    }

    /**
     * 向缓存添加新的键值对
     * <p>仅当缓存中不存在键时添加</p>
     *
     * @param key
     * @param value
     * @param expire 过期时间
     * @return
     */
    private static boolean addExp(String key, Object value, Date expire) {
        boolean flag = false;
        try {
            flag = mcc.add(key, value, expire);
        } catch (Exception e) {
            log.error("Memcached add命令出错", e);
        }
        return flag;
    }

    /**
     * 替换缓存键的值
     * <p>仅当键已经存在时替换</p>
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean replace(String key, Object value) {
        return replaceExp(key, value, null);
    }

    /**
     * 替换缓存键的值
     * <p>仅当键已经存在时替换</p>
     *
     * @param key
     * @param value
     * @param expire 过期时间
     * @return
     */
    public static boolean replace(String key, Object value, Date expire) {
        return replaceExp(key, value, expire);
    }

    /**
     * 替换缓存键的值
     * <p>仅当键已经存在时替换</p>
     *
     * @param key
     * @param value
     * @param expire 过期时间
     * @return
     */
    private static boolean replaceExp(String key, Object value, Date expire) {
        boolean flag = false;
        try {
            flag = mcc.replace(key, value, expire);
        } catch (Exception e) {
            log.error("Memcached replace命令出错", e);
        }
        return flag;
    }

    /**
     * 检索缓存键的值
     *
     * @param key
     * @return
     */
    public static Object get(String key) {
        Object obj = null;
        try {
            obj = mcc.get(key);
        } catch (Exception e) {
            log.error("Memcached get命令出错", e);
        }
        return obj;
    }

    /**
     * 删除缓存
     *
     * @param key
     * @return
     */
    public static boolean delete(String key) {
        boolean flag = false;
        try {
            flag = mcc.delete(key);
        } catch (Exception e) {
            log.error("Memcached delete命令出错", e);
        }
        return flag;
    }

    /**
     * 清理缓存
     *
     * @return
     */
    public static boolean flashAll() {
        boolean flag = false;
        try {
            flag = mcc.flushAll();
        } catch (Exception e) {
            log.error("Memcached flushAll命令出错", e);
        }
        return flag;
    }
}