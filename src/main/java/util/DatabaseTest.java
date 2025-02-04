package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseTest {
    public static void main(String[] args) {
        // Địa chỉ kết nối MySQL
        String url = "jdbc:mysql://localhost:3306/tracnghiem"; // Cổng và tên cơ sở dữ liệu
        String username = "root"; // Tên người dùng
        String password = ""; // Mật khẩu (trống nếu bạn chưa thay đổi mật khẩu mặc định)

        try {
            // Kết nối đến cơ sở dữ liệu
            Connection connection = DriverManager.getConnection(url, username, password);
            if (connection != null) {
                System.out.println("Kết nối thành công!");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối: " + e.getMessage());
        }
    }
}

