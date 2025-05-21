package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDAOImpl implements AccountDAO {
    private final Connection connection;

    public AccountDAOImpl() {
        this.connection = ConnectionUtil.getConnection(); // Establishes a database connection using utility class
    }

    public boolean checkIfAccountExist(String username) {
        String sql = "select * from account where username=?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // Returns true if a record with the username exists
        }catch(Exception e){
            System.out.println(e.getMessage());

            return false;
        }
    }

    public boolean checkIfAccountExist(int accountID) {
        String sql = "select * from account where account_id=?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountID);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // Returns true if a record with the username exists
        }catch(Exception e){
            System.out.println(e.getMessage());

            return false;
        }
    }

    public Account registerAccount(Account account) {
        String sql = "insert into account (username, password) values(?,?)";

        try{
            if(checkIfAccountExist(account.getUsername())){
                // Prevents duplicate account creation by checking if the username already exists
                throw new IllegalArgumentException("Account already exists");
            }

            // RETURN_GENERATED_KEYS allows retrieval of auto-generated ID
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                // Creates and returns a new Account object with the generated ID
                return new Account(resultSet.getInt(1), account.getUsername(), account.getPassword());
            }

            return null;

        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Account loginAccount(Account account) {
        String sql = "select * from account where username=? and password=?";

        try {
            if(!checkIfAccountExist(account.getUsername())){
                // Early exit if username doesn't exist avoids unnecessary DB lookup
                throw new IllegalArgumentException("Account does not exist");
            }

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                // Returns matching account from database
                return new Account(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
            }

            return null;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
