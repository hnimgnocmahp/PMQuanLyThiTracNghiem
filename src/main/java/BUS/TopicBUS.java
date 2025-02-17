package BUS;

import DAO.TopicDAO;
import DTO.TopicDTO;

import java.util.List;

public class TopicBUS {
    private TopicDAO topicDAO;
    private static TopicBUS instance;

    private TopicBUS() {
        topicDAO = new TopicDAO();
    }

    public static TopicBUS getInstance(){
        if (instance == null) {
            synchronized (TopicBUS.class) {
                if (instance == null) {
                    instance = new TopicBUS();
                }
            }
        }
        return instance;
    }

    public List<TopicDTO> loadTopics(){
        return topicDAO.loadTopics();
    }

    public int addTopic(TopicDTO topicDTO){
        if (topicDAO.isTitleIsExists(topicDTO.getTopicTitle())){
            return -1;
        }
        else{
            return topicDAO.add(topicDTO);
        }
    }

    public int updateTopic(TopicDTO topicDTO){
        return topicDAO.update(topicDTO);
    }

    public int deleteTopic(int id){
        return topicDAO.delete(id);
    }

    public int deleteTopic2(int id){
        return topicDAO.delete2(id);
    }


}
