package net.kacemi.skypaybanktest1.skypaybanktest1.services;

import lombok.extern.slf4j.Slf4j;
import net.kacemi.skypaybanktest1.skypaybanktest1.dtos.*;
import net.kacemi.skypaybanktest1.skypaybanktest1.entites.Account;
import net.kacemi.skypaybanktest1.skypaybanktest1.entites.Client;
import net.kacemi.skypaybanktest1.skypaybanktest1.entites.CurrentAccount;
import net.kacemi.skypaybanktest1.skypaybanktest1.entites.SavingAccount;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.AccountNotFoundException;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.ClientAlreadyExistException;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.ClientNotFoundException;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.InvalidLoginException;
import net.kacemi.skypaybanktest1.skypaybanktest1.mappers.ClientMapper;
import net.kacemi.skypaybanktest1.skypaybanktest1.mappers.CurrentAccountMapper;
import net.kacemi.skypaybanktest1.skypaybanktest1.mappers.OperationMapper;
import net.kacemi.skypaybanktest1.skypaybanktest1.mappers.SavingAccountMapper;
import net.kacemi.skypaybanktest1.skypaybanktest1.repositories.AccountRepository;
import net.kacemi.skypaybanktest1.skypaybanktest1.repositories.ClientRepository;
import net.kacemi.skypaybanktest1.skypaybanktest1.repositories.OperationRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final OperationRepositoryImpl operationRepositoryImpl;
    ClientRepository clientRepository;
    ClientMapper clientMapper;
    SavingAccountMapper savingAccountMapper;
    CurrentAccountMapper currentAccountMapper;
    OperationMapper operationMapper;
    AccountRepository accountRepository;

    private ClientDTO authenticatedClient = null;

    public AuthServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper, AccountRepository accountRepository, OperationRepositoryImpl operationRepositoryImpl) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.accountRepository = accountRepository;
        this.savingAccountMapper = new SavingAccountMapper();
        this.operationMapper = new OperationMapper();
        this.currentAccountMapper = new CurrentAccountMapper();

        this.operationRepositoryImpl = operationRepositoryImpl;
    }

    @Override
    public ClientDTO signIn(String fullName, String password) throws InvalidLoginException, ClientNotFoundException {
        Client client = clientRepository.findByLoginAndPassword(fullName, password);


        ClientDTO clientDTO= clientMapper.fromClient(client);
        List<Account> accounts =  accountRepository.findAllByClientId(clientDTO.getId()) ;


        List<AccountDTO> accountDTOS = getAccountDTOS(accounts);

        clientDTO.setAccountsDTO(accountDTOS);
        //clientDTO.setAccountsDTO(accountsDTO);
        this.authenticatedClient = clientDTO;

        return clientDTO;
    }

    private List<AccountDTO> getAccountDTOS(List<Account> accounts) {
        List<AccountDTO> accountDTOS = accounts.stream().map(account -> {
            if (account instanceof SavingAccount savingAccount) {
                SavingAccountDTO savingAccountDTO = savingAccountMapper.fromSavingAccount(savingAccount);

                try {
                    List<OperationDTO> operationDTOs = operationRepositoryImpl
                            .findByAccountIdOrderByDateDesc(savingAccountDTO.getId())
                            .stream()
                            .map(operation -> operationMapper.fromOperation(operation))
                            .toList();

                    savingAccountDTO.setOperationsDTO(operationDTOs);
                } catch (AccountNotFoundException e) {
                    throw new RuntimeException(e);
                }
                return savingAccountDTO;
            }
            else if (account instanceof CurrentAccount currentAccount) {
                CurrentAccountDTO currentAccountDTO = currentAccountMapper.fromCurrentAccount(currentAccount);


                List<OperationDTO> operationDTOs = null;
                try {
                    operationDTOs = operationRepositoryImpl
                            .findByAccountIdOrderByDateDesc(currentAccountDTO.getId())
                            .stream()
                            .map(operation -> operationMapper.fromOperation(operation))
                            .toList();
                } catch (AccountNotFoundException e) {
                    throw new RuntimeException(e);
                }

                currentAccountDTO.setOperationsDTO(operationDTOs);
                return currentAccountDTO;
            }else {
                return null;
            }

        }).filter(dto -> dto != null).toList();
        return accountDTOS;
    }


    @Override
    public void refreshAuthenticatedUser()  {
        if(authenticatedClient != null){
            List<Account> accounts = null;
            try {
                accounts = accountRepository.findAllByClientId(authenticatedClient.getId());
            } catch (ClientNotFoundException e) {
                throw new RuntimeException(e);
            }

            authenticatedClient.setAccountsDTO( getAccountDTOS(accounts));
        }
    }
    @Override
    public void signOut() {
        this.authenticatedClient = null;
    }

    @Override
    public ClientDTO signUp(String fullName, String password) throws ClientAlreadyExistException {
        log.info("Signing up");

        Client client = new Client();
        client.setId(UUID.randomUUID().toString());
        client.setFullName(fullName);
        client.setPassword(password);

        clientRepository.saveClient(client);
        ClientDTO clientDTO= clientMapper.fromClient(client);
        this.authenticatedClient = clientDTO;
        return clientDTO;
    }

    @Override
    public ClientDTO getAuthenticatedClient() {
        return this.authenticatedClient;
    }
}
