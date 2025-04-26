package core.persistence;

import config.ServerConfig;
import core.storage.StringStore;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
/*
  数据持久化管理器
  职责：定期将内存数据保存到磁盘，并在启动时加载历史数据
  实现方式：对象序列化
 */
public class DataPersistence {
    // 从配置获取持久化文件路径
    private static final String SAVE_PATH = ServerConfig.getPersistenceFile();

    //保存数据到文件，同步锁：防止多线程同时写入导致文件损坏
    public static synchronized void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH))) {
            oos.writeObject(StringStore.getStore()); // 序列化存储对象
        } catch (IOException e) {
            System.err.println("持久化失败: " + e.getMessage());
        }
    }

    //从文件加载数据 ,启动时自动调用
    @SuppressWarnings("unchecked")
    public static void loadData() {
        File file = new File(SAVE_PATH);
        if (file.exists()) { // 检查文件是否存在
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                // 反序列化并恢复数据
                ConcurrentHashMap<String, String> data = (ConcurrentHashMap<String, String>) ois.readObject();
                StringStore.loadData(data); // 假设StringStore有加载方法
            } catch (Exception e) {
                System.err.println("数据加载失败: " + e.getMessage());
            }
        }
    }
}