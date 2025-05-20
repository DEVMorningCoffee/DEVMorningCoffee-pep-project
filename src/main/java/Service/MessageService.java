package Service;

import DAO.AccountDAO;
import DAO.AccountDAOImpl;
import DAO.MessageDAO;
import DAO.MessageDAOImpl;
import Model.Message;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MessageService {
    private final MessageDAO messageDAO;
    private final AccountDAO accountDAO;
    
    public MessageService(Connection connection) {
        this.messageDAO = new MessageDAOImpl(connection);
        this.accountDAO = new AccountDAOImpl(connection);
    }
    
    public Message createMessage(int accountID, String messageText, long timePostedEpoch) throws IllegalArgumentException, SQLException {
        if (messageText == null || messageText.isBlank() || messageText.length() > 255) {
            throw new IllegalArgumentException("Message cannot be blank or over 255 characters.");
        }
        if (!accountDAO.checkUserExistsId(accountID)) {
            throw new IllegalArgumentException("User with provided ID does not exist.");
        }
        return messageDAO.uploadMessage(accountID, messageText, timePostedEpoch);
    }
    
    public List<Message> getAllMessage() throws SQLException {
        return messageDAO.retrieveAllMessage();
    }
    
    public Message getMessage(int messageID) throws SQLException {
        return messageDAO.retrieveMessageByID(messageID);
    }
    
    public Message deleteMessage(int messageID) throws SQLException {
        // First check if the message exists, but don't throw an exception if it doesn't
        Message existingMessage = messageDAO.retrieveMessageByID(messageID);
        if (existingMessage == null) {
            return null; // Return null to indicate message not found
        }
        
        // Message exists, proceed with deletion
        try {
            return messageDAO.deleteMessageByID(messageID);
        } catch (SQLException e) {
            throw new SQLException("Error during message deletion: " + e.getMessage());
        }
    }
    
    public Message updateMessage(int messageID, String messageText) throws SQLException, IllegalArgumentException {
        if (messageText == null || messageText.isBlank() || messageText.length() > 255) {
            throw new IllegalArgumentException("Message cannot be blank or over 255 characters.");
        }
        Message existingMessage = messageDAO.retrieveMessageByID(messageID);
        if (existingMessage == null) {
            return null; // Return null to indicate message not found
        }
        return messageDAO.updateMessageByID(messageID, messageText);
    }
    
    public List<Message> getAllMessagesByID(int accountID) throws SQLException {
        if (!accountDAO.checkUserExistsId(accountID)) {
            throw new IllegalArgumentException("User with provided ID does not exist.");
        }
        return messageDAO.retrieveMessageByAccountID(accountID);
    }
}