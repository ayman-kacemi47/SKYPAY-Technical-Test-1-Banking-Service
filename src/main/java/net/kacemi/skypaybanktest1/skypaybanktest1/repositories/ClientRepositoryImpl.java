package net.kacemi.skypaybanktest1.skypaybanktest1.repositories;

import net.kacemi.skypaybanktest1.skypaybanktest1.entites.Account;
import net.kacemi.skypaybanktest1.skypaybanktest1.entites.Client;
import net.kacemi.skypaybanktest1.skypaybanktest1.entites.Operation;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.AccountNotFoundException;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.ClientAlreadyExistException;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.InvalidLoginException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class ClientRepositoryImpl implements ClientRepository {
    static List<Client> clientsTableDB=new ArrayList<Client>();

    @Override
    public Client findByLoginAndPassword(String fullName, String password) throws InvalidLoginException {
        Client clientRes = clientsTableDB.stream().filter(client -> client.getFullName().equals(fullName) && client.getPassword().equals(password)).findFirst().orElseThrow(()->  new InvalidLoginException("Invalid login or password"));
        List<Account> clientAccounts = AccountRepositoryImpl.accountsTableDB.stream().filter((account -> account.getClientId().equals(clientRes.getId()))).toList();
        clientAccounts.forEach(account -> {
           List<Operation> accountOperations = OperationRepositoryImpl.operationsTableDB.stream().filter(operation -> operation.getAccountId() ==account.getId() ).toList();
           account.setOperations(accountOperations);
        });
        clientRes.setAccounts(clientAccounts);
        return clientRes;
    }

    @Override
    public Client saveClient(Client client) throws ClientAlreadyExistException {
        Optional<Client> existingClient = clientsTableDB.stream()
                .filter(c -> c.getFullName().equals(client.getFullName()))
                .findFirst();

        if (existingClient.isPresent()) {
            throw new ClientAlreadyExistException("Client already exists with '" + existingClient.get().getFullName() + "'");
        }

        clientsTableDB.add(client);
        return client;
    }

    @Override
    public Client findByAccountNumber(String accountNumber) throws AccountNotFoundException {

        return clientsTableDB.stream().filter(client -> client.getAccounts().stream().filter(account -> account.getAccountNumber().equals(accountNumber)).findFirst().isPresent()).findFirst().orElseThrow(()-> new AccountNotFoundException("No client found with account number: " + accountNumber));
    }
}
