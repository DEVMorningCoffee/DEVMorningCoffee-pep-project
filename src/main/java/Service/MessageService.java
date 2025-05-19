package Service;

import java.sql.Connection;
import java.sql.SQLException;

import DAO.MessageDAO;
import DAO.MessageDAOImpl;
import Model.Message;

public class MessageService {
    private final MessageDAO messageDAO;

    public MessageService(Connection connection){
        this.messageDAO = new MessageDAOImpl(connection);
    }

    public Message createMessage(int accountID, String messageText, long timePostedEpoch) throws IllegalArgumentException, SQLException{
        if(messageText.isBlank() || messageText.length() > 255){
            throw new IllegalArgumentException("Message is blank or over 255");
        }

        return messageDAO.uploadMessage(accountID, messageText, timePostedEpoch);
    }
}
