package core.storage;

import java.util.concurrent.ConcurrentHashMap;

//双向链表类型存储，Key映射到双向链表实例
public class ListStore {
    private static final ConcurrentHashMap<String, DoublyLinkedList> store = new ConcurrentHashMap<>();

    public static String lpush(String key, String value) {
        store.computeIfAbsent(key, k -> new DoublyLinkedList()).lpush(value);
        return "OK";
    }

    public static String rpush(String key, String value) {
        store.computeIfAbsent(key, k -> new DoublyLinkedList()).rpush(value);
        return "OK";
    }

    public static String lpop(String key) {
        DoublyLinkedList list = store.get(key);
        return (list == null || list.size() == 0) ? "(nil)" : list.lpop();
    }

    public static String rpop(String key) {
        DoublyLinkedList list = store.get(key);
        return (list == null || list.size() == 0) ? "(nil)" : list.rpop();
    }

    public static String range(String key, int start, int end) {
        DoublyLinkedList list = store.get(key);
        if (list == null) return "(empty)";
        return String.join(",", list.range(start, end));
    }

    public static String len(String key) {
        DoublyLinkedList list = store.get(key);
        return list == null ? "0" : String.valueOf(list.size());
    }
}