package BUS;

import DAO.UserDAO;
import DTO.UserDTO;

public class UserBUS {
    private static UserBUS instance;

    private UserDAO userDAO;

    private UserBUS() {
        userDAO = new UserDAO();
    }

    public static UserBUS getInstance() {
        if (instance == null) {
            synchronized (UserBUS.class) {
                if (instance == null) {
                    instance = new UserBUS();
                }
            }
        }
        return instance;
    }

    public int addUser(UserDTO user) {
        // Kiểm tra dữ liệu hợp lệ
        if (user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            return -1; // Lỗi: Dữ liệu không hợp lệ
        }

        // Kiểm tra tài khoản đã tồn tại chưa
        if (userDAO.isUsernameExists(user.getEmail())) {
            return -2; // Lỗi: Tài khoản đã tồn tại
        }

        // Thêm user vào database
        int result = userDAO.add(user);
        return result;
    }

    public int updateUser(UserDTO user) {
        return userDAO.update(user);
    }

    public int deleteUser(int id) {
        return userDAO.delete(id);
    }

    public UserDTO findUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null; // Không tìm với email trống
        }
        return userDAO.getUserByEmail(email);
    }
}
