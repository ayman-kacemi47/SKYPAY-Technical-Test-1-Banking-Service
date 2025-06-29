package net.kacemi.skypaybanktest1.skypaybanktest1.repositories;

import net.kacemi.skypaybanktest1.skypaybanktest1.entites.Account;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.AccountNotFoundException;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.ClientNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AccountRepository {
    Account findById(int id) throws AccountNotFoundException;
    Account findByAccountNumber(String accountNumber) throws AccountNotFoundException;
    Account save(Account account);
    void update(Account account);
    List<Account> findAllByClientId(String clientId) throws ClientNotFoundException;
    List<Account> findAll();


}
