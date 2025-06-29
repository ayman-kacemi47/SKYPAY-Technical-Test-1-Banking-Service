package net.kacemi.skypaybanktest1.skypaybanktest1.entites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kacemi.skypaybanktest1.skypaybanktest1.enums.OperationType;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
    private int id;
    private OperationType operationType;
    private int amount;
    private int accountId;
    private Date date;
    private String description;


}
