package Users;

import java.io.Serial;
import java.io.Serializable;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private UserRole role;
    private double salary;
    private int discount;

    public User() {}

    public User(String password, String username, UserRole role, double salary, int discount) {
        this.password = password;
        this.username = username;
        this.role = role;
        this.salary = salary;
        this.discount = discount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getDiscount() {
        return discount;
    }
}
