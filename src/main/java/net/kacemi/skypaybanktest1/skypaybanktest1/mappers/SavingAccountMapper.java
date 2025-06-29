package net.kacemi.skypaybanktest1.skypaybanktest1.mappers;


import net.kacemi.skypaybanktest1.skypaybanktest1.dtos.SavingAccountDTO;
import net.kacemi.skypaybanktest1.skypaybanktest1.entites.Operation;
import net.kacemi.skypaybanktest1.skypaybanktest1.entites.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SavingAccountMapper {
    OperationMapper operationMapper = new OperationMapper();
    public SavingAccountDTO fromSavingAccount(SavingAccount account) {
        SavingAccountDTO dto = new SavingAccountDTO();

        // Champs hérités de Account
        dto.setId(account.getId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setBalance(account.getBalance());
        dto.setActive(account.isActive());
        dto.setAccountType(account.getAccountType());
        dto.setCurrency(account.getCurrency());
        dto.setClientId(account.getClientId());
        dto.setOperationsDTO(account.getOperations()== null ? new ArrayList<>() : account.getOperations().stream().map(operation -> operationMapper.fromOperation(operation) ).toList()); // à mapper si besoin

        // Spécifique Saving
        dto.setInterestRate(account.getInterestRate());

        return dto;
    }

    public SavingAccount fromSavingAccountDTO(SavingAccountDTO dto) {
        SavingAccount account = new SavingAccount();

        account.setId(dto.getId());
        account.setAccountNumber(dto.getAccountNumber());
        account.setBalance(dto.getBalance());
        account.setActive(dto.isActive());
        account.setAccountType(dto.getAccountType());
        account.setCurrency(dto.getCurrency());
        account.setClientId(dto.getClientId());
        account.setOperations( dto.getOperationsDTO()== null ? new ArrayList<>() : dto.getOperationsDTO().stream().map(operationDTO -> operationMapper.fromOperationDTO(operationDTO)).toList()); // à mapper si besoin

        account.setInterestRate(dto.getInterestRate());

        return account;
    }
}

//public class SavingAccountMapper {
//    public SavingAccount fromSavingAccountDTO(SavingAccountDTO savingAccountDTO) {
//        SavingAccount savingAccount = new SavingAccount();
//        BeanUtils.copyProperties(savingAccountDTO, savingAccount);
//        return savingAccount;
//    }
//    public SavingAccountDTO fromSavingAccount(SavingAccount savingAccount) {
//        SavingAccountDTO savingAccountDTO = new SavingAccountDTO();
//        BeanUtils.copyProperties(savingAccount, savingAccountDTO);
//        return savingAccountDTO;
//    }
//}
