package Data;

import Users.User;
import Users.UserRole;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class DataSave {
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;

    public DataSave() throws IOException {
        // 从配置文件读取数据库地址和端口
        String host = "localhost";
        int port = 12345;
        this.socket = new Socket(host, port);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    // 用户数据存储示例
    public void saveUsers(ArrayList<User> users) {
        for (User user : users) {
            String command = String.format("set user::%s %s",
                    user.getUsername(),
                    serializeUser(user));
            sendCommand(command);
        }
    }

    public ArrayList<User> getUsers() {
        sendCommand("keys user::*");
        try {
            String[] keys = in.readLine().split(" ");
            ArrayList<User> users = new ArrayList<>();
            for (String key : keys) {
                sendCommand("get " + key);
                users.add(deserializeUser(in.readLine()));
            }
            return users;
        } catch (IOException e) {
            throw new RuntimeException("数据库操作失败", e);
        }
    }

    // 序列化方法
    private String serializeUser(User user) {
        return String.join("|",
                user.getUsername(),
                user.getPassword(),
                user.getRole().name(),
                String.valueOf(user.getSalary()),
                String.valueOf(user.getDiscount())
        );
    }

    private User deserializeUser(String data) {
        String[] parts = data.split("\\|");
        return new User(
                parts[1], // password
                parts[0], // username
                UserRole.valueOf(parts[2]), // role
                Double.parseDouble(parts[3]), // salary
                Integer.parseInt(parts[4]) // discount
        );
    }

    private String sendCommand(String command) throws IOException {
        out.println(command);
        return in.readLine();
    }
}