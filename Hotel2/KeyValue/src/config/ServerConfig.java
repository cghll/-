package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/*服务端配置管理器，加载配置文件并对外提供配置项访问接口，设计为：单例模式（通过静态块初始化）*/
public class ServerConfig {
    // 静态属性对象，全局唯一
    private static final Properties properties = new Properties();

    // 静态初始化块：在类加载时执行
    static {
        try (FileInputStream input = new FileInputStream("src/main/resources/server.properties")) {
            properties.load(input); // 加载配置文件到Properties对象
        } catch (IOException e) {
            throw new RuntimeException("配置文件加载失败", e); // 初始化失败则抛出异常
        }
    }
    //获取端口号
    public static int getPort() {
        return Integer.parseInt(properties.getProperty("server.port"));
    }

    //获取持久化文件路径
    public static String getPersistenceFile() {
        return properties.getProperty("persistence.file");
    }

    //获取日志文件路径
    public static String getLogFile() {
        return properties.getProperty("log.file");
    }
}