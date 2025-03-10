package DAO;

import BUS.TopicBUS;
import DTO.TopicDTO;
import DTO.UserDTO;
import util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TopicDAO {

    public List<TopicDTO> loadTopics(){
        ArrayList<TopicDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM topics";

        try{
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement st =  connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();


            while (rs.next()){
                TopicDTO topic = new TopicDTO();
                topic.setTopicID(rs.getInt("tpID"));
                topic.setTopicTitle(rs.getString("tpTitle"));
                topic.setTopicParent(rs.getInt("tpParent"));
                topic.setTopicStatus(rs.getInt("tpStatus"));
                list.add(topic);
            }
            JDBCUtil.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public int add(TopicDTO topicDTO){
        int result = 0;
        String sql = "INSERT INTO topics (tpTitle, tpParent, tpStatus) VALUES (?, ?, ?) ";

        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, topicDTO.getTopicTitle());
            ps.setInt(2, topicDTO.getTopicParent());
            ps.setInt(3, 1);

            result = ps.executeUpdate();
            JDBCUtil.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public int update(TopicDTO topicDTO){
        int result = 0;
        String sql = "UPDATE topics SET tpTitle=?, tpParent=? WHERE tpID = ?";
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, topicDTO.getTopicTitle());
            ps.setInt(2, topicDTO.getTopicParent());
            ps.setInt(3, topicDTO.getTopicID());

            result = ps.executeUpdate();
            JDBCUtil.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public int delete(int id){
        int result = 0;
        String sql = "UPDATE topics SET tpStatus = ? WHERE tpID = ?";

        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1,0);
            ps.setInt(2, id);

            result = ps.executeUpdate();
            JDBCUtil.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public int delete2(int topicID) {
        int ketqua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "DELETE FROM topics WHERE tpID=?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setInt(1, topicID);

            ketqua = preparedStatement.executeUpdate();

            JDBCUtil.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketqua;
    }

    public boolean isTitleIsExists(String title){
        String sql = "SELECT * FROM topics WHERE tpTitle = ? AND tpStatus = 1 ";

        try {
            Connection c =  JDBCUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1,title);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
            JDBCUtil.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public TopicDTO searchTopic(String title, int id){
        TopicDTO topicDTO = new TopicDTO();
        String sql = "SELECT * FROM topics WHERE tpTitle = ? AND tpID = ?";

        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1,title);
            ps.setInt(2,id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                topicDTO.setTopicID(rs.getInt("tpID"));
                topicDTO.setTopicTitle(rs.getString("tpTitle"));
                topicDTO.setTopicParent(rs.getInt("tpParent"));
                topicDTO.setTopicStatus(rs.getInt("tpStatus"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return topicDTO;
    }

    public TopicDTO searchTopicByID( int id){
        TopicDTO topicDTO = new TopicDTO();
        String sql = "SELECT * FROM topics WHERE tpID = ?";

        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                topicDTO.setTopicID(rs.getInt("tpID"));
                topicDTO.setTopicTitle(rs.getString("tpTitle"));
                topicDTO.setTopicParent(rs.getInt("tpParent"));
                topicDTO.setTopicStatus(rs.getInt("tpStatus"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return topicDTO;
    }

    public List<TopicDTO> searchTopicsChild(int parentID){
        ArrayList<TopicDTO> topicDTOList = new ArrayList<>();
        String sql = "SELECT * FROM topics WHERE tpParent = ?";

        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1,parentID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                TopicDTO topicDTO = new TopicDTO();

                topicDTO.setTopicID(rs.getInt("tpID"));
                topicDTO.setTopicTitle(rs.getString("tpTitle"));
                topicDTO.setTopicParent(rs.getInt("tpParent"));
                topicDTO.setTopicStatus(rs.getInt("tpStatus"));

                topicDTOList.add(topicDTO);
            }
            JDBCUtil.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return topicDTOList;
    }

    public List<TopicDTO> getTopicsChild(int parentID){
        ArrayList<TopicDTO> topicDTOList = new ArrayList<>();
        String sql = "SELECT * FROM topics WHERE (tpID = ? OR tpParent = ?) AND tpStatus = 1 ";

        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1,parentID);
            ps.setInt(2,parentID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                TopicDTO topicDTO = new TopicDTO();

                topicDTO.setTopicID(rs.getInt("tpID"));
                topicDTO.setTopicTitle(rs.getString("tpTitle"));
                topicDTO.setTopicParent(rs.getInt("tpParent"));
                topicDTO.setTopicStatus(rs.getInt("tpStatus"));

                topicDTOList.add(topicDTO);
            }
            JDBCUtil.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return topicDTOList;
    }

    public List<TopicDTO> getTopicWithParentNull() {
        ArrayList<TopicDTO> topicDTOList = new ArrayList<>();
        String sql = "SELECT * FROM topics WHERE tpParent = 0 AND tpStatus = 1 ";

        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                TopicDTO topicDTO = new TopicDTO();

                topicDTO.setTopicID(rs.getInt("tpID"));
                topicDTO.setTopicTitle(rs.getString("tpTitle"));
                topicDTO.setTopicParent(rs.getInt("tpParent"));
                topicDTO.setTopicStatus(rs.getInt("tpStatus"));

                topicDTOList.add(topicDTO);
            }
            JDBCUtil.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return topicDTOList;
    }

    public List<TopicDTO> searchTopicsChildByTitle(String title){
        ArrayList<TopicDTO> topicDTOList = new ArrayList<>();
        String sql = "SELECT * FROM topics WHERE tpTitle = ?";

        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1,title);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                TopicDTO topicDTO = new TopicDTO();

                topicDTO.setTopicID(rs.getInt("tpID"));
                topicDTO.setTopicTitle(rs.getString("tpTitle"));
                topicDTO.setTopicParent(rs.getInt("tpParent"));
                topicDTO.setTopicStatus(rs.getInt("tpStatus"));

                topicDTOList.add(topicDTO);
            }
            JDBCUtil.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return topicDTOList;
    }

    public List<TopicDTO> getAll() {
        List<TopicDTO> topicList = new ArrayList<TopicDTO>();
        String sql = "SELECT * FROM topics";
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                TopicDTO topicDTO = new TopicDTO();
                topicDTO.setTopicID(resultSet.getInt("tpID"));
                topicDTO.setTopicTitle(resultSet.getString("tpTitle"));
                topicDTO.setTopicParent(resultSet.getInt("tpParent"));
                topicDTO.setTopicStatus(resultSet.getInt("tpStatus"));

                topicList.add(topicDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topicList;
    }

    public TopicDTO getTopicByTitle(String title) {
        TopicDTO topic = null; // Chỉ cần một đối tượng, không phải danh sách
        String sql = "SELECT * FROM topics WHERE tpTitle = ?";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, title); // Gán giá trị vào câu lệnh SQL
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) { // Lấy kết quả đầu tiên nếu có
                topic = new TopicDTO();
                topic.setTopicID(resultSet.getInt("tpID"));
                topic.setTopicTitle(resultSet.getString("tpTitle"));
                topic.setTopicParent(resultSet.getInt("tpParent"));
                topic.setTopicStatus(resultSet.getInt("tpStatus"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topic;
    }

    public List<TopicDTO> getTopicWithParentNotNull() {
        ArrayList<TopicDTO> topicDTOList = new ArrayList<>();
        String sql = "SELECT * FROM topics WHERE tpParent != 0 AND tpStatus = 1 ";

        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                TopicDTO topicDTO = new TopicDTO();

                topicDTO.setTopicID(rs.getInt("tpID"));
                topicDTO.setTopicTitle(rs.getString("tpTitle"));
                topicDTO.setTopicParent(rs.getInt("tpParent"));
                topicDTO.setTopicStatus(rs.getInt("tpStatus"));

                topicDTOList.add(topicDTO);
            }
            JDBCUtil.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return topicDTOList;
    }


}
