package core.storage;

import java.util.ArrayList;
import java.util.List;

/*
  线程安全的双向链表,支持左右插入/删除,可作为队列或栈使用
 */
public class DoublyLinkedList {
    private final Node head; // 虚拟头节点
    private final Node tail; // 虚拟尾节点
    private int size;

    private static class Node {
        String value;
        Node prev;
        Node next;

        Node(String value) {
            this.value = value;
        }
    }

    public DoublyLinkedList() {
        head = new Node(null);
        tail = new Node(null);
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

   //左端插入
    public synchronized void lpush(String value) {
        Node newNode = new Node(value);
        newNode.next = head.next;
        newNode.prev = head;
        head.next.prev = newNode;
        head.next = newNode;
        size++;
    }

   //右端插入
    public synchronized void rpush(String value) {
        Node newNode = new Node(value);
        newNode.prev = tail.prev;
        newNode.next = tail;
        tail.prev.next = newNode;
        tail.prev = newNode;
        size++;
    }

    //左端弹出
    public synchronized String lpop() {
        if (size == 0) return null;
        Node first = head.next;
        head.next = first.next;
        first.next.prev = head;
        size--;
        return first.value;
    }

    //右端弹出
    public synchronized String rpop() {
        if (size == 0) return null;
        Node last = tail.prev;
        tail.prev = last.prev;
        last.prev.next = tail;
        size--;
        return last.value;
    }

    //获取指定范围数据
    public synchronized List<String> range(int start, int end) {
        List<String> result = new ArrayList<>();
        Node current = head.next;
        for (int i = 0; i <= end && current != tail; i++) {
            if (i >= start) result.add(current.value);
            current = current.next;
        }
        return result;
    }

    public synchronized int size() {
        return size;
    }
}