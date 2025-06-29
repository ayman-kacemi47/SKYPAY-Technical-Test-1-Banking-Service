package net.kacemi.skypaybanktest1.skypaybanktest1.dtos;

import lombok.Data;
import net.kacemi.skypaybanktest1.skypaybanktest1.entites.Operation;

import java.util.List;

@Data
public class ClientDTO {
    private String id;
    private String fullName;
    private List<AccountDTO> accountsDTO;
}
