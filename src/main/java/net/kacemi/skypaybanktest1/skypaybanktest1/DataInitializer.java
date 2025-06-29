package net.kacemi.skypaybanktest1.skypaybanktest1;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.kacemi.skypaybanktest1.skypaybanktest1.dtos.*;
import net.kacemi.skypaybanktest1.skypaybanktest1.entites.Operation;
import net.kacemi.skypaybanktest1.skypaybanktest1.enums.AccountType;
import net.kacemi.skypaybanktest1.skypaybanktest1.enums.TypeDeposit;
import net.kacemi.skypaybanktest1.skypaybanktest1.services.AccountService;
import net.kacemi.skypaybanktest1.skypaybanktest1.services.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component @AllArgsConstructor
@Order(1)
public class DataInitializer implements CommandLineRunner {
    private final AuthService authService;
    private final AccountService accountService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- Initializing seed data ---");
        authService.signUp("ayman_kacemi","12345678");
        ClientDTO skypayClient = authService.signUp("skypay","12345678");
        CurrentAccountDTO skypayCurrentAccountDTO  = new CurrentAccountDTO();
        skypayCurrentAccountDTO.setAccountType(AccountType.CA);
        skypayCurrentAccountDTO.setBalance(1500);
        skypayCurrentAccountDTO.setActive(true);
        skypayCurrentAccountDTO.setOverDraft(500);
        skypayCurrentAccountDTO.setCurrency("MAD");
        skypayCurrentAccountDTO.setClientId(skypayClient.getId());
        skypayCurrentAccountDTO = accountService.createCurrentAccount(skypayCurrentAccountDTO);

        try {

        ClientDTO clientDTO =  authService.signIn("ayman_kacemi","12345678");
        SavingAccountDTO savingAccountDTO  = new SavingAccountDTO();
        savingAccountDTO.setAccountType(AccountType.SA);
        savingAccountDTO.setBalance(1500);
        savingAccountDTO.setActive(true);
        savingAccountDTO.setInterestRate(2.5/100);
        savingAccountDTO.setCurrency("MAD");
        savingAccountDTO.setClientId(clientDTO.getId());

        accountService.createSavingAccount(savingAccountDTO);


            CurrentAccountDTO currentAccountDTO  = new CurrentAccountDTO();
            currentAccountDTO.setAccountType(AccountType.CA);
            currentAccountDTO.setActive(true);
            currentAccountDTO.setBalance(1500);
            currentAccountDTO.setOverDraft(500);
            currentAccountDTO.setCurrency("MAD");
            currentAccountDTO.setClientId(clientDTO.getId());
            currentAccountDTO = accountService.createCurrentAccount(currentAccountDTO);


            accountService.transfert(200, currentAccountDTO.getId(), skypayCurrentAccountDTO.getId(),"200 "+currentAccountDTO.getCurrency()+"to "+skypayClient.getFullName()+" for the IPTV subscription." );


            for (int i = 0; i < 10 ; i++){
                double random = Math.random()*10;
              if( random > 8){
                  accountService.deposit(currentAccountDTO.getId(), (int)(random * 1000), Optional.empty(), TypeDeposit.INTERNAL
                  );
                  accountService.deposit(savingAccountDTO.getId(), (int)(random*500),Optional.empty(), TypeDeposit.INTERNAL);
              }else{
                  accountService.withdraw(currentAccountDTO.getId(), (int)(random*15),Optional.empty());
              }

            }
            authService.refreshAuthenticatedUser();

            authService.signOut();

            System.out.println("account1: ayman_kacemi , 12345678 ");
            System.out.println("account2: skypay , 12345678 ");

            System.out.println();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
