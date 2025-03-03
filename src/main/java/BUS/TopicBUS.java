package BUS;

import DAO.TopicDAO;
import DTO.TopicDTO;

import java.util.List;

public class TopicBUS {
    private TopicDAO topicDAO;
    private static TopicBUS instance;

    public TopicBUS() {
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
        return topicDAO.add(topicDTO);
    }

    public int updateTopic(TopicDTO topicDTO){
        for (TopicDTO topic : topicDAO.loadTopics()){
            if (topic.getTopicID() != topicDTO.getTopicID() && topic.getTopicStatus() == 1 && topic.getTopicTitle().equals(topicDTO.getTopicTitle())){
                return -1;
            }
        }

        return topicDAO.update(topicDTO);
    }

    public int deleteTopic(int id){
        return topicDAO.delete(id);
    }

    public int deleteTopic2(int id){
        return topicDAO.delete2(id);
    }

    public TopicDTO searchTopic(String title, int id){
        return topicDAO.searchTopic(title,id);
    }

    public TopicDTO searchTopicByID(int id){
        return topicDAO.searchTopicByID(id);
    }

    public boolean Validation (int parentID, String title){

        for (TopicDTO topic : topicDAO.searchTopicsChildByTitle(title)){
            if (topic != null){
                for (TopicDTO topic1 : topicDAO.searchTopicsChild(topic.getTopicID())){
                    if (topic.getTopicTitle() != null){
                        if (topic.getTopicTitle().equals(title) ){
                            return false;
                        };
                    }
                }
            }
        }

        return true;
    }

    public boolean checkAddTopic(String title, int parentId) {
        if (topicDAO.searchTopicByID(parentId).getTopicParent() != 0){
            return false;
        }
        if(!Validation(parentId,title)){
            return false;
        }
        return true;
    }

    public boolean checkUpdateTopic(String title, int parentId){
        if (topicDAO.searchTopicByID(parentId).getTopicTitle() != null){
            if (topicDAO.searchTopicByID(parentId).getTopicTitle().equals(title)){
                return false;
            }
        }
        if (topicDAO.searchTopicByID(parentId).getTopicParent() != 0){
            return false;
        }

        if(!Validation(parentId,title)){
            return false;
        }
        return true;
    }

    public List<TopicDTO> searchTopicsChildByID(int id){
        return topicDAO.searchTopicsChild(id);
    }
}
