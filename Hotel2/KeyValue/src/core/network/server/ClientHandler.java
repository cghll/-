package core.network.server;

import core.command.CommandProcessor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*客户端连接处理线程,处理单个客户端的命令请求,生命周期：随客户端连接建立而创建，断开而销毁*/
public class ClientHandler implements Runnable {//用实现Runnable接口
    private final Socket clientSocket; // 客户端Socket对象
    private final CommandProcessor processor = new CommandProcessor(); // 命令处理器

    //构造函数socket 客户端Socket连接
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {//重写run方法
        // 使用try-with-resources自动关闭流
        try (
                // 输入流：读取客户端发送的命令,BufferedReader提高读取效率
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                // 输出流：向客户端发送响应
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {// 持续读取客户端命令直到连接断开
                String response = processor.processCommand(inputLine);// 处理命令并获取响应
                out.println(response); // 发送响应给客户端
            }
        } catch (IOException e) {
            System.err.println("客户端连接异常: " + e.getMessage());
        } finally {
            try {
                clientSocket.close(); // 确保Socket关闭
            } catch (IOException e) {
                System.err.println("关闭Socket失败: " + e.getMessage());
            }
        }
    }
}