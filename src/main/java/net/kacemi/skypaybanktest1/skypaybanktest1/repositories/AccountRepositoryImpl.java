package net.kacemi.skypaybanktest1.skypaybanktest1.repositories;

import net.kacemi.skypaybanktest1.skypaybanktest1.entites.Account;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.AccountNotFoundException;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.ClientNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    static List<Account> accountsTableDB = new ArrayList<Account>();
    private static final AtomicLong accountNumberCounter = new AtomicLong(0);

    @Override
    public Account findById(int id) throws AccountNotFoundException {
        return accountsTableDB.stream().filter(account -> account.getId() == id).findFirst().orElseThrow(()-> new AccountNotFoundException("Account not found"));
    }

    @Override
    public Account findByAccountNumber(String accountNumber) throws AccountNotFoundException {
        return accountsTableDB.stream().filter(account -> account.getAccountNumber().equals(accountNumber)).findFirst().orElseThrow(()->new AccountNotFoundException("Account not found"));
    }

    @Override
    public Account save(Account account) {
        long nextNumber = accountNumberCounter.incrementAndGet();
        account.setId((int) nextNumber);
        account.setAccountNumber("ACC-"+nextNumber);
        System.out.println("accoutn ser Account "+ account);

        accountsTableDB.add(account);
        System.out.println("nouveau table "+ accountsTableDB);
        return account;
    }

    @Override
    public void update(Account account) {
        accountsTableDB.stream().filter(account1 -> account1.getId() == account.getId()).findFirst().ifPresent(account1 -> {account1.setActive(account.isActive()) ; account1.setBalance(account.getBalance());});

    }

    @Override
    public List<Account> findAllByClientId(String clientId) throws ClientNotFoundException {
        return  accountsTableDB.stream().filter(account -> account.getClientId().equals(clientId)).collect(Collectors.toList());
    }

    @Override
    public List<Account> findAll() {
        return accountsTableDB;
    }
}
