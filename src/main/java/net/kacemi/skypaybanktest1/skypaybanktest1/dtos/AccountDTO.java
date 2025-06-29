package net.kacemi.skypaybanktest1.skypaybanktest1.dtos;

import lombok.Data;
import net.kacemi.skypaybanktest1.skypaybanktest1.entites.Operation;
import net.kacemi.skypaybanktest1.skypaybanktest1.enums.AccountType;

import java.util.List;

@Data
public class AccountDTO {
    private int id;
    private String accountNumber;
    private int balance;
    private boolean isActive;
    private AccountType accountType;
    private String currency;
    private List<OperationDTO> operationsDTO;
    private String clientId;
}
