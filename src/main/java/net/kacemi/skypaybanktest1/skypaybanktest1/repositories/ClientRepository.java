package net.kacemi.skypaybanktest1.skypaybanktest1.repositories;

import net.kacemi.skypaybanktest1.skypaybanktest1.entites.Client;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.AccountNotFoundException;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.ClientAlreadyExistException;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.InvalidLoginException;
import org.springframework.stereotype.Repository;

public interface ClientRepository {
    Client findByLoginAndPassword(String fullName, String password) throws InvalidLoginException;
    Client saveClient(Client client) throws ClientAlreadyExistException;
    Client findByAccountNumber(String accountNumber) throws AccountNotFoundException;

}
