package util;

import java.sql.*;

public class JDBCUtil {
    private static final String URL = ConfigUtil.getProperty("db.url");
    private static final String USER = ConfigUtil.getProperty("db.user");
    private static final String PASSWORD = ConfigUtil.getProperty("db.password");
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void close(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
