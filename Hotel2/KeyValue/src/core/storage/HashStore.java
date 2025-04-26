package core.storage;

import java.util.concurrent.ConcurrentHashMap;

public class HashStore {
    private static final ConcurrentHashMap<String, ConcurrentHashMap<String, String>> store = new ConcurrentHashMap<>();

    public static String hset(String key, String field, String value) {
        store.computeIfAbsent(key, k -> new ConcurrentHashMap<>()).put(field, value);
        return "OK";
    }

    public static String hget(String key, String field) {
        ConcurrentHashMap<String, String> hash = store.get(key);
        return (hash == null || !hash.containsKey(field)) ? "(nil)" : hash.get(field);
    }

    public static String hdel(String key, String field) {
        ConcurrentHashMap<String, String> hash = store.get(key);
        return (hash == null || hash.remove(field) == null) ? "0" : "1";
    }
}