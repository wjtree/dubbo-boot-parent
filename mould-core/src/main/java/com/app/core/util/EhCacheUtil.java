package com.app.core.util;

import org.ehcache.Cache;
import org.ehcache.Cache.Entry;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class EhCacheUtil {
    /**
     * 缓存管理器
     */
    private static CacheManager cacheManager = null;

    /**
     * 无到期时间的缓存域
     */
    public static Cache<String, String> noExpiryCache = null;

    /**
     * 有到期时间的缓存域，有效期：10分钟
     */
    public static Cache<String, String> expiryCache = null;

    static {
        // 获取ehcache配置文件路径
        URL url = EhCacheUtil.class.getResource("/config/ehcache.xml");
        // 加载ehcache配置文件
        Configuration xmlConfig = new XmlConfiguration(url);
        // 获取ehcache管理器
        cacheManager = CacheManagerBuilder.newCacheManager(xmlConfig);
        // 初始化ehcache管理器
        cacheManager.init();

        // 获取无到期时间的缓存域
        noExpiryCache = getCache("noExpiryCache", String.class, String.class);
        // 获取有到期时间的缓存域，有效期：10分钟
        expiryCache = getCache("noExpiryCache", String.class, String.class);
    }

    /**
     * 获取EhCahce缓存域
     *
     * @param alias     缓存域名称
     * @param keyType   键类型
     * @param valueType 值类型
     * @return
     */
    public static <K, V> Cache<K, V> getCache(String alias, Class<K> keyType, Class<V> valueType) {
        return cacheManager.getCache(alias, keyType, valueType);
    }

    /**
     * 获取缓存域中的所有键值对
     *
     * @return
     */
    public static <K, V> Map<K, V> iterator(Cache<K, V> cache) {
        Map<K, V> map = null;
        if (cache != null) {
            map = new HashMap<K, V>();
            // 循环取出缓存域中的键和值
            for (Entry<K, V> entry : cache)
                map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}