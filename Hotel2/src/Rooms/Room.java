package Rooms;

import java.io.Serial;
import java.io.Serializable;

public class Room implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int roomID;
    private double prices;
    private boolean isBook;
    private String roomType;

    public Room() {
    }

    public Room(int roomID, double prices, boolean isBook, String roomType) {
        this.roomID = roomID;
        this.prices = prices;
        this.isBook = isBook;
        this.roomType = roomType;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public double getPrices() {
        return prices;
    }

    public void setPrices(double prices) {
        this.prices = prices;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public boolean isBook() {
        return isBook;
    }

    public void setBook(boolean book) {
        isBook = book;
    }
}
