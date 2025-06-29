package net.kacemi.skypaybanktest1.skypaybanktest1.dtos;

import lombok.Data;
import net.kacemi.skypaybanktest1.skypaybanktest1.enums.OperationType;

import java.util.Date;

@Data
public class OperationDTO {
    private int id;
    private OperationType operationType;
    private int amount;
    private Date date;
    private String description;

}
