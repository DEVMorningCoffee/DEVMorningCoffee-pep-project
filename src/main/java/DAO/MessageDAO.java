package DAO;

import java.sql.SQLException;
import java.util.List;

import Model.Message;

public interface MessageDAO {
    
    public Message uploadMessage(int postedBy, String messageText, long timePostedEpoch) throws SQLException;
    public Message getMessage(int postedBy, String messageText, long timePostedEpoch) throws SQLException;
    public List<Message> retrieveAllMessage() throws SQLException;
    public Message retrieveMessageByID(int messageID) throws SQLException;
    public Message deleteMessageByID(int messageID) throws SQLException;
    public Message updateMessageByID(int messageID, String messageText) throws SQLException;
}
