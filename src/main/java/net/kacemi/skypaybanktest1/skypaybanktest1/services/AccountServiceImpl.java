package net.kacemi.skypaybanktest1.skypaybanktest1.services;

import lombok.AllArgsConstructor;
import net.kacemi.skypaybanktest1.skypaybanktest1.dtos.*;
import net.kacemi.skypaybanktest1.skypaybanktest1.entites.Account;
import net.kacemi.skypaybanktest1.skypaybanktest1.entites.CurrentAccount;
import net.kacemi.skypaybanktest1.skypaybanktest1.entites.Operation;
import net.kacemi.skypaybanktest1.skypaybanktest1.entites.SavingAccount;
import net.kacemi.skypaybanktest1.skypaybanktest1.enums.AccountType;
import net.kacemi.skypaybanktest1.skypaybanktest1.enums.OperationType;
import net.kacemi.skypaybanktest1.skypaybanktest1.enums.TypeDeposit;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.AccountNotFoundException;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.InsufficientBalanceException;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.SavingAccountWithdrawException;
import net.kacemi.skypaybanktest1.skypaybanktest1.mappers.CurrentAccountMapper;
import net.kacemi.skypaybanktest1.skypaybanktest1.mappers.SavingAccountMapper;
import net.kacemi.skypaybanktest1.skypaybanktest1.repositories.AccountRepository;
import net.kacemi.skypaybanktest1.skypaybanktest1.repositories.AccountRepositoryImpl;
import net.kacemi.skypaybanktest1.skypaybanktest1.repositories.OperationRepository;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private CurrentAccountMapper currentAccountMapper;
    private SavingAccountMapper savingAccountMapper;
    private AuthService authService;
    private OperationRepository operationRepository;

    @Override
    public void deposit(int accountId, int amount, Optional<String> desc, TypeDeposit typeDeposit) throws AccountNotFoundException {

        Account account;
        try {
            if(typeDeposit == TypeDeposit.INTERNAL){

         AccountDTO accountDTO=  authService.getAuthenticatedClient().getAccountsDTO().stream().filter(accountDTOItem -> accountDTOItem.getId() == accountId).findFirst().orElseThrow(()->new AccountNotFoundException("No coressponding account with the id: "+accountId));
                if(accountDTO.getAccountType() == AccountType.CA){
                     account=   currentAccountMapper.fromCurrentAccountDTO((CurrentAccountDTO) accountDTO);
                }else {
                      account =   savingAccountMapper.fromSavingAccountDTO((SavingAccountDTO) accountDTO);

                }
            }else{

           account= accountRepository.findById(accountId);

            }
        } catch (AccountNotFoundException e) {
            System.out.println("Error account not found");
            return;
        }
        Operation operation = new Operation();
        operation.setAccountId(accountId);
        operation.setOperationType(OperationType.CREDIT);
        operation.setDate(new Date());
        operation.setDescription(desc.orElse(amount+" MAD deposit") );
        operation.setAmount(amount);
        account.setBalance(account.getBalance() + amount);
        accountRepository.update(account);
        operationRepository.save(operation);
        authService.refreshAuthenticatedUser();
    }

    @Override
    public void withdraw(int accountId, int amount, Optional<String> desc) throws AccountNotFoundException, InsufficientBalanceException, SavingAccountWithdrawException {
          AccountDTO accountDTO =  authService.getAuthenticatedClient().getAccountsDTO().stream().filter(accountDTOItem -> accountDTOItem.getId() == accountId).findFirst().orElseThrow(()->new AccountNotFoundException("No coressponding accout with the id: "+accountId));
        if(accountDTO instanceof SavingAccountDTO){
            throw  new SavingAccountWithdrawException("Error, you can't withdraw from saving account");
        }else{
            CurrentAccountDTO currentAccountDTO = (CurrentAccountDTO) accountDTO;

             if(currentAccountDTO.getBalance()+currentAccountDTO.getOverDraft() < amount){
                 throw new InsufficientBalanceException("Insufficient balance");
             }else{
        Operation operation = new Operation();
        operation.setAccountId(accountId);
        operation.setOperationType(OperationType.DEBIT);
        operation.setDate(new Date());
        operation.setDescription(desc.orElse(amount+" MAD withdrawal") );

        operation.setAmount(amount);
                 authService.refreshAuthenticatedUser();
          Account account = currentAccountMapper.fromCurrentAccountDTO(currentAccountDTO);
          account.setBalance(account.getBalance() - amount);
        accountRepository.update(account);
        operationRepository.save(operation);
                 authService.refreshAuthenticatedUser();
             }

        }

    }

    @Override
    public void printStatement() {
        ClientDTO clientDTO = authService.getAuthenticatedClient();
        System.out.println("\n\n-------- Print Statement for " + clientDTO.getFullName() + " --------");

        List<AccountDTO> accountsDTOS = clientDTO.getAccountsDTO();

        showAccountsWithOperations(accountsDTOS);
    }

    public static void showAccountsWithOperations(List<AccountDTO> accountsDTOS) {
        if (accountsDTOS == null || accountsDTOS.isEmpty()) {
            System.out.println("\tNo accounts found");
            return;
        }
        // Corps du tableau
        for (AccountDTO accountDTO : accountsDTOS) {
        // En-tête du tableau
        System.out.printf("\n\n%-5s | %-15s | %-10s | %-8s | %-4s | %-10s| %-10s| %-10s%n",
                "ID", "Account Number", "Balance", "Active", "Type","Overdraft", "Inrestrate", "Currency");
        System.out.println("----------------------------------------------------------------------");
            System.out.printf("%-5d | %-15s | %-10d | %-8s | %-4s | %-10s | %-10s| %-10s%n",
                    accountDTO.getId(),
                    accountDTO.getAccountNumber(),
                    accountDTO.getBalance(),
                    accountDTO.isActive() ? "Yes" : "No",
                    accountDTO.getAccountType(),
                    accountDTO.getAccountType() == AccountType.CA ? ((CurrentAccountDTO)accountDTO).getOverDraft() : "N/A",
                    accountDTO.getAccountType() == AccountType.SA ? ((SavingAccountDTO)accountDTO).getInterestRate() : "N/A",

                    accountDTO.getCurrency());
            System.out.println("\n-------- List of operations ---------");

            if (accountDTO.getOperationsDTO() == null || accountDTO.getOperationsDTO().isEmpty()) {
                System.out.println("\tNo operations found for account " + accountDTO.getAccountNumber());
                System.out.println("\n\n\n");


            }else{
                System.out.printf("%-5s | %-15s | %-10s | %-30s | %-30s%n",
                        "ID", "Type", "Amount", "Date", "Description");
                System.out.println("---------------------------------------------------------------------------------------------");

                for (OperationDTO operationDTO : accountDTO.getOperationsDTO()) {

                    System.out.printf("%-5d | %-15s | %-10d | %-20s | %-30s%n",
                            operationDTO.getId(),
                            operationDTO.getOperationType(),
                            operationDTO.getAmount(),
                            operationDTO.getDate(),
                            operationDTO.getDescription() != null ? operationDTO.getDescription() : "N/A");
                }
                System.out.println("\n\n\n");
            }


        }
    }
    public static void showAccounts(List<AccountDTO> accountsDTOS) {
        // En-tête du tableau
        System.out.printf("%-5s | %-15s | %-10s | %-8s | %-12s | %-10s%n",
                "ID", "Account Number", "Balance", "Active", "Account Type", "Currency");
        System.out.println("----------------------------------------------------------------------");
        if (accountsDTOS == null || accountsDTOS.isEmpty()) {
            System.out.println("\tNo accounts found");
            return;
        }
        // Corps du tableau
        for (AccountDTO accountDTO : accountsDTOS) {
            System.out.printf("%-5d | %-15s | %-10d | %-8s | %-12s | %-10s%n",
                    accountDTO.getId(),
                    accountDTO.getAccountNumber(),
                    accountDTO.getBalance(),
                    accountDTO.isActive() ? "Yes" : "No",
                    accountDTO.getAccountType(),
                    accountDTO.getCurrency());


        }
    }


    @Override
    public void transfert(int amount, int fromAccountId, int toAccountId, String desc) throws InsufficientBalanceException, SavingAccountWithdrawException, AccountNotFoundException{
        this.withdraw(fromAccountId, amount, Optional.of(desc));
        this.deposit(toAccountId, amount, Optional.of(desc), TypeDeposit.EXTERNAL);
    }

    @Override
    public SavingAccountDTO createSavingAccount(SavingAccountDTO savingAccountDTO) {
        SavingAccount savingAccount=(SavingAccount) accountRepository.save(savingAccountMapper.fromSavingAccountDTO(savingAccountDTO));

        authService.refreshAuthenticatedUser();
        return savingAccountMapper.fromSavingAccount(savingAccount);
    }

    @Override
    public CurrentAccountDTO createCurrentAccount(CurrentAccountDTO currentAccountDTO) {
        CurrentAccount currentAccount = (CurrentAccount) accountRepository.save(currentAccountMapper.fromCurrentAccountDTO(currentAccountDTO));
       authService.refreshAuthenticatedUser();
        return currentAccountMapper.fromCurrentAccount(currentAccount);
    }
}
