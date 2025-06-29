package net.kacemi.skypaybanktest1.skypaybanktest1.entites;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kacemi.skypaybanktest1.skypaybanktest1.enums.AccountType;

import java.util.List;

@Data @NoArgsConstructor
@AllArgsConstructor
public abstract class Account {
    private int id;
    private String accountNumber;
    private int balance;
    private boolean isActive;
    private AccountType accountType;
    private String currency;
    private List<Operation> operations;
    private String clientId;

}
