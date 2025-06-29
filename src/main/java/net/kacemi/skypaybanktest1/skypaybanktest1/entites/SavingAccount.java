package net.kacemi.skypaybanktest1.skypaybanktest1.entites;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor
@NoArgsConstructor
public class SavingAccount extends Account {
    private double interestRate;
}
