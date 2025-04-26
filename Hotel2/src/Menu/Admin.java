package Menu;

import Data.DataSave;
import Rooms.Room;
import Users.User;
import Data.DataSave;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin {
    public static void AdminMenu(User admin,  ArrayList<Room> rooms, DataSave data) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("**********欢迎使用旅馆小帮手**********");
            System.out.println("欢迎管理员");
            System.out.println("-------1. 查看所有房间-------");
            System.out.println("-------2. 添加房间类型-------");
            System.out.println("-------3. 删除房间类型-------");
            System.out.println("-------4. 退出登录-------");
            System.out.print("请输入操作数： ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    displayRooms(rooms);
                    break;
                case 2:
                    addRoom(rooms, data);
                    break;
                case 3:
                    deleteRoomType(rooms, data);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("无效选项！");
            }
        }
    }

    private static void displayRooms( ArrayList<Room> rooms) {
        System.out.println("房间列表：");
        for (Room room : rooms) {
            System.out.printf("房号："+room.getRoomID()+"类型："+ room.getRoomType()+ "价格："+room.getPrices()+
                    "是否可订："+room.isBook());
        }
    }

    private static void addRoom( ArrayList<Room> rooms, DataSave data) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入房间号：");
        int roomNumber= scanner.nextInt();
        for (Room room : rooms) {
            if(room.getRoomID()==roomNumber) {
                System.out.println("该房间已存在！");
                return;
            }
        }
        System.out.print("请输入房间类型名称：");
        String typeName = scanner.next();
        System.out.print("请输入价格：");
        double price = scanner.nextDouble();


        rooms.add(new Room(roomNumber, price,true,typeName));
        data.saveRooms(rooms);
        System.out.println("添加成功！");
    }

    private static void deleteRoomType(ArrayList<Room> rooms, DataSave data) {
        Scanner scanner=new Scanner(System.in);
        System.out.print("请输入要删除的房间号：");
        int roomNumber = scanner.nextInt();
        boolean removed = rooms.removeIf(r -> r.getRoomID()==(roomNumber));
        if (removed) {
            data.saveRooms(rooms);
            System.out.println("删除成功！");
        } else {
            System.out.println("房间类型不存在！");
        }
    }
}
