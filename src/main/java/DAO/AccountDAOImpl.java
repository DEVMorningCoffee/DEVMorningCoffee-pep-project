package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;

public class AccountDAOImpl implements AccountDAO {
    private final Connection connection;

    public AccountDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public Account createAccount(String username, String password) throws SQLException {
        if (checkUserExistsUsername(username)) {
            throw new SQLException("User exists");
        }

        String createAccountSQL = "INSERT INTO ACCOUNT(username, password) VALUES (?, ?)";
        PreparedStatement prep = null;

        try {
            prep = connection.prepareStatement(createAccountSQL);
            prep.setString(1, username);
            prep.setString(2, password);
            prep.executeUpdate();
        } finally {
            if (prep != null) prep.close();
        }

        Account account = getAccountByUsernameAndPassword(username, password);
        if (account == null) {
            throw new SQLException("Could not retrieve created account");
        }

        return account;
    }

    public Account getAccountByUsernameAndPassword(String username, String password) throws SQLException {
        String sql = "SELECT account_id, username, password FROM ACCOUNT WHERE username = ? AND password = ?";
        PreparedStatement prep = null;
        ResultSet rs = null;

        try {
            prep = connection.prepareStatement(sql);
            prep.setString(1, username);
            prep.setString(2, password);
            rs = prep.executeQuery();

            if (rs.next()) {
                return new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }
        } finally {
            if (rs != null) rs.close();
            if (prep != null) prep.close();
        }

        return null;
    }

    public boolean checkUserExistsUsername(String username) throws SQLException {
        String sql = "SELECT 1 FROM ACCOUNT WHERE username = ?";
        PreparedStatement prep = null;
        ResultSet rs = null;

        try {
            prep = connection.prepareStatement(sql);
            prep.setString(1, username);
            rs = prep.executeQuery();
            return rs.next();
        } finally {
            if (rs != null) rs.close();
            if (prep != null) prep.close();
        }
    }

    public boolean checkUserExistsId(int accountID) throws SQLException{
        String sql = "SELECT 1 FROM ACCOUNT WHERE account_id = ?";
        PreparedStatement prep = null;
        ResultSet rs = null;

        try{
            prep = connection.prepareStatement(sql);
            prep.setInt(1, accountID);
            rs = prep.executeQuery();
            return rs.next();
        }finally{
            if (rs != null) rs.close();
            if (prep != null) prep.close();
        }
    }
}
