package net.kacemi.skypaybanktest1.skypaybanktest1.mappers;


import net.kacemi.skypaybanktest1.skypaybanktest1.dtos.ClientDTO;
import net.kacemi.skypaybanktest1.skypaybanktest1.entites.Client;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ClientMapper {
    public Client fromClientDTO(ClientDTO currentAccountDTO) {
        Client currentAccount = new Client();
        BeanUtils.copyProperties(currentAccountDTO, currentAccount);
        return currentAccount;
    }
    public ClientDTO fromClient(Client currentAccount) {
        ClientDTO currentAccountDTO = new ClientDTO();
        BeanUtils.copyProperties(currentAccount, currentAccountDTO);
        return currentAccountDTO;
    }
}
