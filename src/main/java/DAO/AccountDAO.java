package DAO;

import Model.Account;

public interface AccountDAO {
    public boolean checkIfAccountExist(String username);
    public boolean checkIfAccountExist(int accountID);
    public Account registerAccount(Account account);
    public Account loginAccount(Account account);

}
