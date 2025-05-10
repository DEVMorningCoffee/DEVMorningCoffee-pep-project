package DAO;

import java.sql.SQLException;

import Model.Message;

public interface MessageDAO {
    
    public Message createMessage(int posted_by, String message_text, long time_posted_epoch) throws SQLException;
}
