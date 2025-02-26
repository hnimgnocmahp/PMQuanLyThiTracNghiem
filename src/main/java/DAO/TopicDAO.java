package DAO;

import DTO.TopicDTO;
import util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TopicDAO {

    private static TopicDAO instance;

    private TopicDAO() {}

    public static TopicDAO getInstance() {
        if (instance == null) {
            synchronized (TopicDAO.class) {
                if (instance == null) {
                    instance = new TopicDAO();
                }
            }
        }
        return instance;
    }
    public String getTopicNameByID(int topicID) {
        String topicName = "Không xác định"; // Giá trị mặc định nếu không tìm thấy
        String sql = "SELECT tpTitle FROM topics WHERE tpID = ?"; // Sửa lại cột thành tpTitle

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, topicID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                topicName = rs.getString("tpTitle"); // Đổi thành tpTitle
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topicName;
    }


    public List<TopicDTO> getAllTopics() {
        List<TopicDTO> topicList = new ArrayList<>();
        String sql = "SELECT tpID, tpTitle, tpParent, tpStatus FROM topics WHERE tpStatus = 1";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("tpID");
                String title = rs.getString("tpTitle"); // 🔥 Kiểm tra nếu bị null
                int parent = rs.getInt("tpParent");
                int status = rs.getInt("tpStatus");

                if (title == null) { // Nếu bị null thì đặt giá trị mặc định
                    title = "Không có tên";
                }

                topicList.add(new TopicDTO(id, title, parent, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topicList;
    }

}
