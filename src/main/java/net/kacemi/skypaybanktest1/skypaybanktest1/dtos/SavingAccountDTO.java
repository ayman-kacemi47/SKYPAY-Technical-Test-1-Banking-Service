package net.kacemi.skypaybanktest1.skypaybanktest1.dtos;

import lombok.Data;
import net.kacemi.skypaybanktest1.skypaybanktest1.entites.Operation;

import java.util.List;

@Data
public class SavingAccountDTO extends AccountDTO {
    private double interestRate;

}
