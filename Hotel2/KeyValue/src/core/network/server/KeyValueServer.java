package core.network.server;

import config.ServerConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*数据库服务端主程序,启动服务并处理客户端连接请求,通过线程池处理并发连接*/
public class KeyValueServer {
    public static void main(String[] args) {
        // 从配置获取端口
        int port = ServerConfig.getPort();
        // 创建缓存线程池 (自动管理线程数量)
        ExecutorService threadPool = Executors.newCachedThreadPool();
        try (ServerSocket ss = new ServerSocket(port)) { // 自动关闭资源
            System.out.println("服务器已启动，监听端口: " + port);
            // 无限循环接受客户端连接
            while (true) {
                Socket clientSocket = ss.accept(); // 阻塞等待客户端连接
                threadPool.execute(new ClientHandler(clientSocket)); // 提交任务到线程池
            }
        } catch (IOException e) {
            System.err.println("服务器启动失败: " + e.getMessage());
        }
    }
}