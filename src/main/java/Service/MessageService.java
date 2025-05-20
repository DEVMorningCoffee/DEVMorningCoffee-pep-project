package Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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

    public List<Message> getAllMessage() throws SQLException{
        return messageDAO.retrieveAllMessage();
    }

    public Message getMessage(int messageID) throws SQLException{
        return messageDAO.retrieveMessageByID(messageID);
    }

    public Message deleteMessage(int messageID) throws SQLException{
        return messageDAO.deleteMessageByID(messageID);
    }

    public Message updateMessage(int messageID, String messageText) throws SQLException, IllegalArgumentException{
        if(messageText.isBlank() || messageText.length() > 255){
            throw new IllegalArgumentException("Message is blank or over 255");
        }

        return messageDAO.updateMessageByID(messageID, messageText);
    }
}
