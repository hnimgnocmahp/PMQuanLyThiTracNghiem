package BUS;

import DAO.TestDAO;
import DAO.TestStructureDAO;
import DTO.TestDTO;
import DTO.TestStructureDTO;

import java.util.List;

public class TestStructureBUS {
    private static TestStructureBUS instance;

    private TestStructureDAO testStructureDAO;

    private TestStructureBUS() {
        testStructureDAO = new TestStructureDAO();
    }

    public static TestStructureBUS getInstance() {
        if (instance == null) {
            synchronized (TestStructureBUS.class) {
                if (instance == null) {
                    instance = new TestStructureBUS();
                }
            }
        }
        return instance;
    }

    public int addTestStructure(TestStructureDTO testStruct) {

        int result = testStructureDAO.add(testStruct);

        return result;
    }

    public List<TestStructureDTO> getAllTestStructure() {
        return testStructureDAO.selectAll();
    }

    public List<TestStructureDTO> getStructureByTestCode(String testCode) {
        return testStructureDAO.getStructureByTestCode(testCode);
    }
}
