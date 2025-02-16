package BUS;

import DAO.UserDAO;
import DTO.UserDTO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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
        // Kiểm tra tài khoản đã tồn tại chưa
        if (userDAO.isUsernameExists(user.getUserName())) {
            return -1;
        } else if (userDAO.isEmailExists(user.getUserEmail())) {
            return -2;
        }

        // Mã hóa mật khẩu trước khi gửi xuống DAO
        String hashedPassword = hashMD5(user.getUserPassword());
        user.setUserPassword(hashedPassword);
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

    public UserDTO findUserByUserName(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            return null;
        }
        return userDAO.getUserByUserName(userName);
    }

    public static String hashMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public List<UserDTO> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public List<UserDTO> searchUsers(String key) {
        return userDAO.searchUsers(key);
    }
}
