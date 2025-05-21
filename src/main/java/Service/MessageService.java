package Service;

import DAO.AccountDAO;
import DAO.AccountDAOImpl;
import DAO.MessageDAO;
import DAO.MessageDAOImpl;
import Model.Message;

import java.util.List;

public class MessageService {
    MessageDAO messageDAO = new MessageDAOImpl();
    AccountDAO accountDAO = new AccountDAOImpl();

    public MessageService() {}

    public Message createMessage(Message message) throws NullPointerException, IllegalArgumentException {
        if(message == null) {
            // Validate that the message object is not null
            throw new NullPointerException("Message cannot be null");
        }

        if(message.getMessage_text().isBlank() || message.getMessage_text().length() > 255 ||
                !accountDAO.checkIfAccountExist(message.getPosted_by())) {
            // Message must not be blank, should be less than 255 characters, and the posting account must exist
            throw new IllegalArgumentException("Message text cannot be empty, less than 255 characters and account must exist");
        }

        return messageDAO.createMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessage(int messageID) throws IllegalArgumentException {
        if(!messageDAO.checkIfMessageExist(messageID)){
            // Prevent fetching a message that doesn't exist
            throw new IllegalArgumentException("Message does not exist");
        }

        return messageDAO.getMessage(messageID);
    }

    public Message deleteMessage(int messageID) throws IllegalArgumentException {
        if(!messageDAO.checkIfMessageExist(messageID)){
            // Prevent deletion of a non-existent message
            throw new IllegalArgumentException("Message does not exist");
        }

        return messageDAO.deleteMessage(messageID);
    }

    public Message updateMessage(int messageID, String messageText) throws IllegalArgumentException {
        if(!messageDAO.checkIfMessageExist(messageID)){
            throw new IllegalArgumentException("Message does not exist");
        }

        if(messageText.isBlank() || messageText.length() > 255){
            // Ensures valid message text before updating
            throw new IllegalArgumentException("Message text cannot be empty, less than 255 characters and account must exist");
        }

        return messageDAO.updateMessage(messageID, messageText);
    }

    public List<Message> accountMessages(int accountID) throws IllegalArgumentException {
        if(!accountDAO.checkIfAccountExist(accountID)){
            throw new IllegalArgumentException("Account does not exist");
        }

        return messageDAO.accountMessages(accountID);
    }
}
