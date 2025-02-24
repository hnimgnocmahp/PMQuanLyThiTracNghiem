package DAO;

import DTO.UserDTO;
import util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class  UserDAO {

    public int add(UserDTO user) {
        int ketQua = 0;
        String sql = "INSERT INTO users (userID, userName, userEmail, userPassword, userFullName, isAdmin) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, user.getUserID());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getUserEmail());
            preparedStatement.setString(4, user.getUserPassword());
            preparedStatement.setString(5, user.getUserFullName());
            preparedStatement.setInt(6, user.getIsAdmin());

            ketQua = preparedStatement.executeUpdate();
            JDBCUtil.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public int update(UserDTO user) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE users SET userName=?, userEmail=?, userPassword=?, userFullName=?, isAdmin=? WHERE userID=?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);


            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getUserEmail());
            preparedStatement.setString(3, user.getUserPassword());
            preparedStatement.setString(4, user.getUserFullName());
            preparedStatement.setInt(5, user.getIsAdmin());
            preparedStatement.setInt(6, user.getUserID());

            ketQua = preparedStatement.executeUpdate();

            JDBCUtil.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    public int delete(int userID) {
        int ketqua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "DELETE FROM users WHERE userID=?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setInt(1, userID);

            ketqua = preparedStatement.executeUpdate();

            JDBCUtil.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketqua;
    }

    public UserDTO getUserById(int userId) {
        String sql = "SELECT userID, userName, userEmail, userFullName, isAdmin FROM users WHERE userID = ?";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new UserDTO(
                            resultSet.getInt("userID"),
                            resultSet.getString("userName"),
                            resultSet.getString("userEmail"),
                            null, // Không lấy mật khẩu
                            resultSet.getString("userFullName"),
                            resultSet.getInt("isAdmin")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy thông tin User: " + e.getMessage());
        }
        return null; // Trả về null nếu không tìm thấy
    }

    public UserDTO getUserByEmail(String email) {
        UserDTO user = null;
        String sql = "SELECT * FROM users WHERE userEmail = ?";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new UserDTO();
                    user.setUserID(resultSet.getInt("userID"));
                    user.setUserName(resultSet.getString("userName"));
                    user.setUserEmail(resultSet.getString("userEmail"));
                    user.setUserPassword(resultSet.getString("userPassword"));
                    user.setUserFullName(resultSet.getString("userFullName"));
                    user.setIsAdmin(resultSet.getInt("isAdmin"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public UserDTO getUserByUserName(String userName) {
        UserDTO user = null;
        String sql = "SELECT * FROM users WHERE userName = ?";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, userName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new UserDTO();
                    user.setUserID(resultSet.getInt("userID"));
                    user.setUserName(resultSet.getString("userName"));
                    user.setUserEmail(resultSet.getString("userEmail"));
                    user.setUserPassword(resultSet.getString("userPassword"));
                    user.setUserFullName(resultSet.getString("userFullName"));
                    user.setIsAdmin(resultSet.getInt("isAdmin"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean isUsernameExists(String userName) {
        String sql = "SELECT COUNT(*) FROM users WHERE userName = ?";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, userName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE userEmail = ?";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<UserDTO> getAllUsers() {
        List<UserDTO> users = new ArrayList<UserDTO>();
        String sql = "SELECT * FROM users WHERE isAdmin = 1";
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                UserDTO user = new UserDTO();
                user.setUserID(resultSet.getInt("userID"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserEmail(resultSet.getString("userEmail"));
                user.setUserPassword(resultSet.getString("userPassword"));
                user.setUserFullName(resultSet.getString("userFullName"));
                user.setIsAdmin(resultSet.getInt("isAdmin"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<UserDTO> searchUsers(String key) {
        List<UserDTO> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE (userName LIKE ? OR userEmail LIKE ? OR userFullName LIKE ?) AND isAdmin = 1";
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            String search = "%" + key + "%";
            preparedStatement.setString(1, search);
            preparedStatement.setString(2, search);
            preparedStatement.setString(3, search);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    UserDTO user = new UserDTO();
                    user.setUserID(resultSet.getInt("userID"));
                    user.setUserName(resultSet.getString("userName"));
                    user.setUserEmail(resultSet.getString("userEmail"));
                    user.setUserPassword(resultSet.getString("userPassword"));
                    user.setUserFullName(resultSet.getString("userFullName"));
                    user.setIsAdmin(resultSet.getInt("isAdmin"));

                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    // Kiểm tra mật khẩu cũ
    public boolean checkPassword(String userName, String password) {
        String sql = "SELECT userPassword FROM users WHERE userName = ?";
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("userPassword");
                    return storedPassword.equals(password); // So sánh mật khẩu đã mã hóa
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật mật khẩu mới
    public boolean updatePassword(String userName, String newPassword) {
        String sql = "UPDATE users SET userPassword = ? WHERE userName = ?";
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, userName);
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}

