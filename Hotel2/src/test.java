import Data.DataSave;
import Menu.Admin;
import Menu.Customer;
import Rooms.Room;
import Users.User;
import Users.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        DataSave data = new DataSave();
        ArrayList<User> users = data.getUsers();
        ArrayList<Room> rooms = data.getRooms();
        // 初始化管理员账号（如果不存在）
        if (users.stream().noneMatch(u -> u.getRole() == UserRole.ADMIN)) {
            users.add(new User("admin1234", "admin", UserRole.ADMIN, 0.0, 10));
            data.saveUsers(users);
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("**********欢迎使用旅馆小帮手*********");
            System.out.println("--------1. 登录--------");
            System.out.println("--------2. 注册--------");
            System.out.println("--------0. 退出--------");
            System.out.print("请输入操作数： ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    login(users, rooms, data);
                    break;
                case 2:
                    register(users, data);
                    break;
                case 0:
                    data.saveUsers(users);
                    data.saveRooms(rooms);
                    System.out.println("谢谢使用，再见！");
                    return;
                default:
                    System.out.println("无效操作数！");
            }
        }
    }

    private static void login( ArrayList<User> users,  ArrayList<Room> rooms, DataSave data) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入登录用户名：");
        String username = scanner.nextLine();
        System.out.println("请输入密码：");
        String password = scanner.nextLine();
        boolean flag = false;
        for (User user : users) {
            if(user.getUsername().equals(username)) {
                flag = true;
                if(user.getPassword().equals(password)) {
                    System.out.println("登录成功！");
                    if (user.getRole() == UserRole.ADMIN) {
                        Admin.AdminMenu(user, rooms, data);
                    } else {
                        Customer.CustomerMenu(user, rooms, data);
                    }

                }else {
                    System.out.println("密码错误");;
                }
                break;
            }
        }
        if(!flag) {
            System.out.println("没有这个用户名");
            return;
        }


    }

    private static void register( ArrayList<User> users, DataSave data) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入注册用户名：");
        String username = scanner.nextLine();
        if (users.stream().anyMatch(u -> u.getUsername().equals(username))) {
            System.out.println("用户名已存在！");
            return;
        }

        System.out.print("请输入密码：");
        String password = scanner.nextLine();
        users.add(new User( password,username, UserRole.MEMBER, 0.0, 10));
        data.saveUsers(users);
        System.out.println("注册成功！");
    }
}
