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
        return userDAO.add(user);
    }

    public int updateUser(UserDTO user) {
        return userDAO.update(user);
    }

    public int deleteUser(int id) {
        return userDAO.delete(id);
    }
}
