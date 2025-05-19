package DAO;

import java.sql.SQLException;

import Model.Account;

public interface AccountDAO {
    
    
    public Account createAccount(String username, String password) throws SQLException;
    public Account getAccountByUsernameAndPassword(String username, String password) throws SQLException;
    public boolean checkUserExistsUsername(String username) throws SQLException;
    public boolean checkUserExistsId(int accountID) throws SQLException;
}
