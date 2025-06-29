package net.kacemi.skypaybanktest1.skypaybanktest1.dtos;

import lombok.Data;

@Data
public class CurrentAccountDTO extends AccountDTO {
    private int overDraft;

}
