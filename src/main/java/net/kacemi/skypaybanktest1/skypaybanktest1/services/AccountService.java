package net.kacemi.skypaybanktest1.skypaybanktest1.services;

import net.kacemi.skypaybanktest1.skypaybanktest1.dtos.CurrentAccountDTO;
import net.kacemi.skypaybanktest1.skypaybanktest1.dtos.SavingAccountDTO;
import net.kacemi.skypaybanktest1.skypaybanktest1.entites.Account;
import net.kacemi.skypaybanktest1.skypaybanktest1.enums.TypeDeposit;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.AccountNotFoundException;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.InsufficientBalanceException;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.SavingAccountWithdrawException;

import java.util.Optional;

public interface AccountService {
    void deposit(int accountId, int amount, Optional<String> desc, TypeDeposit typeDeposit) throws AccountNotFoundException;
    void withdraw(int accountId, int amount, Optional<String> desc) throws AccountNotFoundException, InsufficientBalanceException, SavingAccountWithdrawException;
    void printStatement();
    void transfert(int amount, int fromAccountId, int toAccountId, String desc) throws InsufficientBalanceException, SavingAccountWithdrawException, AccountNotFoundException;
    SavingAccountDTO createSavingAccount(SavingAccountDTO savingAccountDTO);
    CurrentAccountDTO createCurrentAccount(CurrentAccountDTO currentAccountDTO);
}
