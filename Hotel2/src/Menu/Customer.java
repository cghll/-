package Menu;

import Data.DataSave;
import Rooms.Room;
import Users.User;
import Users.UserRole;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Customer {
    public static void CustomerMenu(User user, ArrayList<Room> rooms, DataSave data) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("*********欢迎使用旅馆小帮手**********");
            System.out.println("欢迎顾客："+user.getUsername()+"当前余额："
                    +user.getSalary()+"享受折扣："+ user.getDiscount()+"折");
            System.out.println("------1. 查看房间信息------");
            System.out.println("------2. 订房------");
            System.out.println("------3.退房------");
            System.out.println("------4. 充值余额------");
            System.out.println("------5. 退出登录------");
            System.out.print("请输入操作数：>> ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    displayRooms(rooms);
                    break;
                case 2:
                    bookRoom(user, rooms, data);
                    break;
                case 3:
                    checkout(user, rooms, data);
                    break;
                case 4:
                    addMoney(user, data);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("无效选项！");
            }
        }
    }

    private static void displayRooms(ArrayList<Room> rooms) {
        System.out.println("可订房间：");
        for (Room room : rooms) {
            if(room.isBook()){
                System.out.println("房间号："+room.getRoomID()+" 房间类型："+room.getRoomType()+
                        " 价格："+room.getPrices());
            }
        }
    }

    // Customer.java 中的订房方法改造
    public static void bookRoom(User user, ArrayList<Room> rooms, DataSave data) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入要预订的房间号：");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();
        try {
            String response = data.sendCommand(
                    String.format("lpush user::%s::bookings %d",
                            user.getUsername(),
                            roomNumber)
            );
            if ("OK".equals(response)) {
                System.out.println("订房成功");
            } else {
                System.out.println("订房失败: " + response);
            }
        } catch (IOException e) {
            System.out.println("网络错误: " + e.getMessage());
        }
    }
    }

    private static void addMoney(User user, DataSave data) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入充值金额：");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("充值成功！");
        if(user.getSalary()>=1000){
            user.setRole(UserRole.UP);
            System.out.println("你已成功变成会员，可享受9折折扣");
            user.setDiscount(9);
        }
        user.setSalary(user.getSalary() + amount);
        data.saveUsers(new ArrayList<>(List.of(user)));
    }

    public static void checkout(User user, ArrayList<Room> rooms, DataSave data) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("输入要退房的房间号：");
        int roomNumber = scanner.nextInt();
        boolean flag = false;
        for (Room room : rooms) {
            if(room.getRoomID() == roomNumber&&(!room.isBook())){
                flag = true;
                room.setBook(true);
                System.out.println("退房成功");
                data.saveRooms(rooms);
                break;
            }
        }
        if(!flag){
            System.out.println("没有订这个房间");
        }

    }
}
