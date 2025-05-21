package DAO;

import Model.Message;

import java.util.List;

public interface MessageDAO {

    public Message createMessage(Message message);
    public List<Message> getAllMessages();
    public Message getMessage(int messageID);
    public boolean checkIfMessageExist(int messageID);
    public Message deleteMessage(int messageID);
    public Message updateMessage(int messageID, String messageText);
    public List<Message> accountMessages(int accountID);
}
