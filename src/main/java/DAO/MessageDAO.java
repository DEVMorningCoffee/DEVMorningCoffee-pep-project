package DAO;

import java.sql.SQLException;

import Model.Message;

public interface MessageDAO {
    
    public Message uploadMessage(int postedBy, String messageText, long timePostedEpoch) throws SQLException;
    public Message getMessage(int postedBy, String messageText, long timePostedEpoch) throws SQLException;
}
