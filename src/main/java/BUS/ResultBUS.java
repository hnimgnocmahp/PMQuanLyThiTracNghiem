package BUS;

import DAO.ResultDAO;
import DAO.UserDAO;
import DTO.ResultDTO;
import DTO.UserDTO;

public class ResultBUS {
    private static ResultBUS instance;

    private ResultDAO resultDAO;

    private ResultBUS() {
        resultDAO = new ResultDAO();
    }

    public static ResultBUS getInstance() {
        if (instance == null) {
            synchronized (ResultBUS.class) {
                if (instance == null) {
                    instance = new ResultBUS();
                }
            }
        }
        return instance;
    }

    public int addResult(ResultDTO resultDTO) {
        int result = resultDAO.add(resultDTO);

        return result;
    }
}
