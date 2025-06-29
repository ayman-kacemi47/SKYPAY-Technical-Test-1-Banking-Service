package net.kacemi.skypaybanktest1.skypaybanktest1.repositories;

import net.kacemi.skypaybanktest1.skypaybanktest1.entites.Operation;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.AccountNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface OperationRepository {
    Operation save(Operation operation);
    List<Operation> findByAccountIdOrderByDateDesc(int accountId) throws AccountNotFoundException;
}
