package BUS;

import DAO.TestDAO;
import DAO.UserDAO;
import DTO.TestDTO;
import DTO.UserDTO;

import java.util.List;

public class TestBUS {
    private static TestBUS instance;

    private TestDAO testDAO;

    private TestBUS() {
        testDAO = new TestDAO();
    }

    public static TestBUS getInstance() {
        if (instance == null) {
            synchronized (TestBUS.class) {
                if (instance == null) {
                    instance = new TestBUS();
                }
            }
        }
        return instance;
    }

    public int addTest(TestDTO test) {

        int result = testDAO.add(test);

        return result;
    }

//    public int updateUser(UserDTO user) {
//        return userDAO.update(user);
//    }
//
//    public int deleteUser(int id) {
//        return userDAO.delete(id);
//    }

    public List<TestDTO> getTests() {
        return testDAO.selectAll();
    }
}
