package net.kacemi.skypaybanktest1.skypaybanktest1.services;

import net.kacemi.skypaybanktest1.skypaybanktest1.dtos.ClientDTO;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.ClientAlreadyExistException;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.ClientNotFoundException;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.InvalidLoginException;

public interface AuthService {

    ClientDTO signIn(String fullName, String password) throws InvalidLoginException, ClientNotFoundException;

    void refreshAuthenticatedUser();

    void signOut();
    ClientDTO signUp(String fullName, String password) throws ClientAlreadyExistException;
    ClientDTO getAuthenticatedClient();
}
