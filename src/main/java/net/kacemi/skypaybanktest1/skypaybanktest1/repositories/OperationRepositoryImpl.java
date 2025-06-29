package net.kacemi.skypaybanktest1.skypaybanktest1.repositories;

import net.kacemi.skypaybanktest1.skypaybanktest1.entites.Operation;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.AccountNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class OperationRepositoryImpl implements OperationRepository {

    static List<Operation> operationsTableDB = new ArrayList<Operation>();
    private static final AtomicLong accountNumberCounter = new AtomicLong(0);

    @Override
    public Operation save(Operation operation) {
        int id = (int) accountNumberCounter.incrementAndGet();
        operation.setId(id);
        operationsTableDB.add(operation);
        return operation;
    }

    @Override
    public List<Operation> findByAccountIdOrderByDateDesc(int accountId) throws AccountNotFoundException {
        return operationsTableDB.stream()
                .filter(operation -> operation.getAccountId() == accountId)
                .sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate())) // tri décroissant par date
                .collect(Collectors.toList());
    }

}
