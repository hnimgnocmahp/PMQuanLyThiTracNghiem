package BUS;

import DAO.TestCodeDAO;
import DAO.UserDAO;
import DTO.TestCodeDTO;
import DTO.UserDTO;

import java.util.List;

public class TestCodeBUS {

    private static TestCodeBUS instance;

    private TestCodeDAO testCodeDAO;

    private TestCodeBUS() {
        testCodeDAO = new TestCodeDAO();
    }

    public static TestCodeBUS getInstance() {
        if (instance == null) {
            synchronized (TestCodeBUS.class) {
                if (instance == null) {
                    instance = new TestCodeBUS();
                }
            }
        }
        return instance;
    }

    public int add(TestCodeDTO testCodeDTO) {
        int result = testCodeDAO.add(testCodeDTO);

        return result;
    }

    public int update(TestCodeDTO testCodeDTO) {
        return testCodeDAO.update(testCodeDTO);
    }

    public int delete(String id) {
        return testCodeDAO.delete(id);
    }

    public TestCodeDTO getTestCodeByID(String id) {
        return testCodeDAO.getTestCodeById(id);
    }

    public TestCodeDTO getRandomTestCode(String testCode) {
        return testCodeDAO.getRandomTestCodeByTestCode(testCode);
    }
}
