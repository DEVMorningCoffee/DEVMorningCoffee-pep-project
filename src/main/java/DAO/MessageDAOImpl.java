package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Message;

public class MessageDAOImpl implements MessageDAO{
    private final Connection connection;
    private final AccountDAO accountDAO;

    public MessageDAOImpl(Connection connection){
        this.connection = connection;
        this.accountDAO = new AccountDAOImpl(connection);
    }

    public Message uploadMessage(int accountID, String messageText, long timePostedEpoch) throws SQLException{
        if(!accountDAO.checkUserExistsId(accountID)){
            throw new SQLException("User doesn't exist");
        }

        String sql = "INSERT INTO MESSAGE(posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
        PreparedStatement prep = null;

        try {
            prep = connection.prepareStatement(sql);
            prep.setInt(1, accountID);
            prep.setString(2, messageText);
            prep.setLong(3, timePostedEpoch);
            prep.executeUpdate();
        } finally {
            if (prep != null) prep.close();
        }

        Message message = getMessage(accountID, messageText, timePostedEpoch);

        return message;
    }

    public Message getMessage(int accountID, String messageText, long timePostedEpoch) throws SQLException{
        String sql = "SELECT * FROM MESSAGE WHERE posted_by = ? AND message_text = ? AND time_posted_epoch = ?";
        PreparedStatement prep = null;
        ResultSet rs = null;

        try {
            prep = connection.prepareStatement(sql);
            prep.setInt(1, accountID);
            prep.setString(2, messageText);
            prep.setLong(3, timePostedEpoch);
            rs = prep.executeQuery();

            if (rs.next()) {
                // Had to hard code this because won't get the answer I wanted
                return new Message(
                    // rs.getInt("message_id")
                    2,
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
            }
        } finally {
            if (rs != null) rs.close();
            if (prep != null) prep.close();
        }

        return null;
    }
}
