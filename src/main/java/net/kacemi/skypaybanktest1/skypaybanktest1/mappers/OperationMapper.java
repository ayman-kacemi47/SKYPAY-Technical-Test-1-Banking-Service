package net.kacemi.skypaybanktest1.skypaybanktest1.mappers;


import net.kacemi.skypaybanktest1.skypaybanktest1.dtos.OperationDTO;
import net.kacemi.skypaybanktest1.skypaybanktest1.entites.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class OperationMapper {
    public Operation fromOperationDTO(OperationDTO operationDTO) {
        Operation operation = new Operation();
        BeanUtils.copyProperties(operationDTO, operation);
        return operation;
    }
    public OperationDTO fromOperation(Operation operation) {
        OperationDTO operationDTO = new OperationDTO();
        BeanUtils.copyProperties(operation, operationDTO);
        return operationDTO;
    }
}
