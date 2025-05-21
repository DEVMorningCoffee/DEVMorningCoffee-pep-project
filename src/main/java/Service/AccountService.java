package Service;

import DAO.AccountDAO;
import DAO.AccountDAOImpl;
import Model.Account;

public class AccountService {
    AccountDAO accountDAO = new AccountDAOImpl();
    public AccountService(){};


    public Account registerAccount(Account account)throws Exception{
        if(account == null){
            throw new Exception("Account is null");
        }

        if(account.getUsername().isBlank() || account.getPassword().length() < 4){
            throw new Exception("Invalid account username or password");
        }

        return accountDAO.registerAccount(account);
    }

    public Account loginAccount(Account account) throws Exception{
        if(account == null){
            throw new Exception("Account is null");
        }

        if(account.getUsername().isBlank() || account.getPassword().length() < 4){
            throw new Exception("Invalid account username or password");
        }

        return accountDAO.loginAccount(account);
    }
}
