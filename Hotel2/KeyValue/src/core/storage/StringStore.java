package core.storage;

import java.util.concurrent.ConcurrentHashMap;

//字符串类型存储实现,使用ConcurrentHashMap保证并发安全
public class StringStore {
    // 核心存储结构：线程安全的哈希表
    private static final ConcurrentHashMap<String, String> store = new ConcurrentHashMap<>();

    // 获取存储对象的引用（用于持久化）
    public static ConcurrentHashMap<String, String> getStore() {
        return store;
    }
    // 加载持久化数据（启动时调用）
    public static void loadData(ConcurrentHashMap<String, String> data) {
        store.clear();      // 清空现有数据
        store.putAll(data); // 加载持久化数据
    }

    public static String set(String key, String value) {//存储键和值
        store.put(key, value);
        return "OK";
    }

    public static String get(String key) {//获取键对应的值
        return store.getOrDefault(key, "(nil)");
    }

    public static String del(String key) {//删除键值对
        return store.remove(key) != null ? "1" : "0";
    }
}