package net.kacemi.skypaybanktest1.skypaybanktest1.mappers;


import net.kacemi.skypaybanktest1.skypaybanktest1.dtos.CurrentAccountDTO;
import net.kacemi.skypaybanktest1.skypaybanktest1.entites.CurrentAccount;
import net.kacemi.skypaybanktest1.skypaybanktest1.entites.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CurrentAccountMapper {
    OperationMapper operationMapper = new OperationMapper();

    public CurrentAccountDTO fromCurrentAccount(CurrentAccount account) {
        CurrentAccountDTO dto = new CurrentAccountDTO();

        dto.setId(account.getId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setBalance(account.getBalance());
        dto.setActive(account.isActive());
        dto.setAccountType(account.getAccountType());
        dto.setCurrency(account.getCurrency());
        dto.setClientId(account.getClientId());
        dto.setOperationsDTO(account.getOperations() == null ? new ArrayList<>() :account.getOperations().stream().map(operation -> operationMapper.fromOperation(operation)).toList()); // à mapper si besoin

        dto.setOverDraft(account.getOverDraft());

        return dto;
    }

    public CurrentAccount fromCurrentAccountDTO(CurrentAccountDTO dto) {
        CurrentAccount account = new CurrentAccount();

        account.setId(dto.getId());
        account.setAccountNumber(dto.getAccountNumber());
        account.setBalance(dto.getBalance());
        account.setActive(dto.isActive());
        account.setAccountType(dto.getAccountType());
        account.setCurrency(dto.getCurrency());
        account.setClientId(dto.getClientId());
        account.setOperations(dto.getOperationsDTO() == null ? new ArrayList<>() : dto.getOperationsDTO().stream().map(operationDTO -> operationMapper.fromOperationDTO(operationDTO)).toList()); // à mapper si besoin

        account.setOverDraft(dto.getOverDraft());

        return account;
    }
}

//public class CurrentAccountMapper {
//    public CurrentAccount fromCurrentAccountDTO(CurrentAccountDTO currentAccountDTO) {
//        CurrentAccount currentAccount = new CurrentAccount();
//        BeanUtils.copyProperties(currentAccountDTO, currentAccount);
//        return currentAccount;
//    }
//    public CurrentAccountDTO fromCurrentAccount(CurrentAccount currentAccount) {
//        CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
//        BeanUtils.copyProperties(currentAccount, currentAccountDTO);
//        return currentAccountDTO;
//    }
//}
