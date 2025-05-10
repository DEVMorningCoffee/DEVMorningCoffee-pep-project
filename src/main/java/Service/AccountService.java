package Service;

import java.sql.Connection;
import java.sql.SQLException;

import DAO.AccountDAO;
import DAO.AccountDAOImpl;
import Model.Account;

public class AccountService {
    // This handle the logic and error handling

    private final AccountDAO accountDAO;

    public AccountService(Connection connection){
        this.accountDAO = new AccountDAOImpl(connection);
    }

    public Account registerAccount(String username, String password) throws IllegalArgumentException, SQLException{
        // Don't need to check if exist this is handle by the Account Data Access Object
        if(username.isBlank() || password.length() < 4){
            throw new IllegalArgumentException("Client Error");
        }

        return accountDAO.createAccount(username, password);
    }

    public Account loginAccount(String username, String password) throws IllegalArgumentException , SQLException{
        // Don't need to check if exist this is handle by the Account Data Access Object
        Account account = accountDAO.getAccountByUsernameAndPassword(username, password);
        
        if(account == null){
            throw new IllegalArgumentException("Client Error");
        }

        return account;
    }
}
