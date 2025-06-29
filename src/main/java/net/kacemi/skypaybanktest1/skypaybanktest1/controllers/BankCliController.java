package net.kacemi.skypaybanktest1.skypaybanktest1.controllers;


import lombok.Data;
import net.kacemi.skypaybanktest1.skypaybanktest1.dtos.AccountDTO;
import net.kacemi.skypaybanktest1.skypaybanktest1.dtos.ClientDTO;
import net.kacemi.skypaybanktest1.skypaybanktest1.dtos.CurrentAccountDTO;
import net.kacemi.skypaybanktest1.skypaybanktest1.dtos.SavingAccountDTO;
import net.kacemi.skypaybanktest1.skypaybanktest1.enums.AccountType;
import net.kacemi.skypaybanktest1.skypaybanktest1.enums.TypeDeposit;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.AccountNotFoundException;
import net.kacemi.skypaybanktest1.skypaybanktest1.exceptions.InsufficientBalanceException;
import net.kacemi.skypaybanktest1.skypaybanktest1.services.AccountService;
import net.kacemi.skypaybanktest1.skypaybanktest1.services.AccountServiceImpl;
import net.kacemi.skypaybanktest1.skypaybanktest1.services.AuthService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component @Data
public class BankCliController {
    private final AuthService authService;
    private final AccountService accountService;
    private final Scanner scanner = new Scanner(System.in);



    // This is the main loop of your entire application
    public void runMainLoop() {
        System.out.println("Welcome to Skypay Bank");

        while (true) {
            ClientDTO authenticatedUser = authService.getAuthenticatedClient();

            if (authenticatedUser == null) {
                showLoggedOutMenu();
            } else {

                showLoggedInMenu(authenticatedUser);
            }
        }
    }

    private void showLoggedOutMenu() {
        System.out.println("\n\n-------- MAIN MENU --------");
        System.out.println("1. Login");
        System.out.println("2. Sign up");
        System.out.println("0. Exit");
        System.out.print("Your choice: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                handleLogin();
                break;
            case "2":
                handleSignUp();
                break;
            case "0":
                System.out.println("Thank you for using Skypay Bank. Goodbye!");
                System.exit(0);
                break;
            default:
                System.err.println("Invalid choice. Please try again.");
        }
    }

    private void showLoggedInMenu(ClientDTO authenticatedUser) {
        System.out.println("\n\n-------- " + authenticatedUser.getFullName() + "'s Space --------");
        System.out.println("1. Create Bank Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Make a Transfer");
        System.out.println("5. Print Statments");
        System.out.println("0. Sign Out");
        System.out.print("Your choice: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                handleCreateAccount();
                break;
            case "2":

                handleDeposit();
                break;
            case "3":
                handleWithdraw();
                break;
            case "4":
                handleTransfer();
                break;
            case "5":
                handlePrintStatments();
                break;
            case "0":
                authService.signOut();
                System.out.println("You have been signed out successfully.");
                break;
            default:
                System.err.println("Invalid choice. Please try again.");
        }
    }

    private void handleWithdraw() {
        ClientDTO authenticatedUser = authService.getAuthenticatedClient();
        System.out.println("\n\n--------- WITHDRAW --------");
        List<AccountDTO> accountsDTO = authenticatedUser.getAccountsDTO();
        if (accountsDTO == null || accountsDTO.isEmpty()) {
            System.out.println("Please create account to procead !! ");
            return;
        }
        System.out.println("\nACCOUNTS LIST: \n\n");
        AccountServiceImpl.showAccounts(accountsDTO);
        System.out.println("*****************************\n\n");
        System.out.println("Amount: ");
        int ammount = Integer.parseInt(scanner.nextLine());
        System.out.println("Account ID: ");
        int accountId = Integer.parseInt(scanner.nextLine());

        try {
            accountService.withdraw(accountId, ammount, Optional.empty());
        } catch (Exception e) {
//            throw new RuntimeException(e);
            //e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
            return;
        }

        System.out.println("\n");
    }

    private void handleTransfer() {
        System.out.println("\n\n--------- TRANSFER --------");
        if(authService.getAuthenticatedClient().getAccountsDTO() == null || authService.getAuthenticatedClient().getAccountsDTO().isEmpty()) {
            System.out.println("Create a account first.");
            return;
        }

        AccountServiceImpl.showAccounts(authService.getAuthenticatedClient().getAccountsDTO());

        System.out.println("1-amount: ");
        int amount =  Integer.parseInt(scanner.nextLine());
        System.out.println("2-Choose Account ID: ");
        int accountId =  Integer.parseInt(scanner.nextLine());
        System.out.println("3-Reciever Account ID: ");
        int recieverAccountId =  Integer.parseInt(scanner.nextLine());
        System.out.println("4-Description to memorize tranfer: ");
        String desc =  scanner.nextLine();

        try {
            accountService.transfert(amount, accountId, recieverAccountId, desc);
            System.out.println("Transfer completed.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }



    }

    private void handleDeposit() {
        ClientDTO authenticatedUser = authService.getAuthenticatedClient();
        System.out.println("\n\n--------- DEPOSIT --------");
        List<AccountDTO> accountsDTO = authenticatedUser.getAccountsDTO();
        if (accountsDTO == null || accountsDTO.isEmpty()) {
            System.out.println("Please create account to procead !! ");
            return;
        }
        System.out.println("\nACCOUNTS LIST: \n\n");
        AccountServiceImpl.showAccounts(accountsDTO);
        System.out.println("*****************************\n\n");
        System.out.println("Amount: ");
        int ammount = Integer.parseInt(scanner.nextLine());
        System.out.println("Account ID: ");
        int accountId = Integer.parseInt(scanner.nextLine());

        try {
            accountService.deposit(accountId, ammount, Optional.empty(), TypeDeposit.INTERNAL);
        } catch (Exception e) {
//            throw new RuntimeException(e);
            //e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
            return;
        }

        System.out.println("\n");

    }


    private void handleSignUp() {
        System.out.println("\n\n-------- Sign Up --------");
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        try {
            authService.signUp(username, password);
            System.out.println("Sign up successful! You can now log in.");
        } catch (Exception e) {

            System.err.println("Error during sign up: " + e.getMessage());
        }
    }

    private void handleLogin() {
        System.out.println("\n\n-------- Login --------");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        try {

            ClientDTO clientDTO= authService.signIn(username, password);
            if (clientDTO != null) {
                System.out.println("Login successful!");
                System.out.println("Welcome "+clientDTO.getFullName());
            }
        } catch (Exception e) {

            System.err.println("Login failed: " + e.getMessage());
        }
    }

    private void handleCreateAccount() {
        System.out.println("\n\n-------- Create New Account --------");
        System.out.println("1. Create Current Account");
        System.out.println("2. Create Saving Account");
        System.out.println("0. Back to main menu");
        System.out.print("Your choice: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":

                System.out.println("\nCreating current account...");
                System.out.println("1. Initial balance: ");
                int initialBalance = Integer.parseInt( scanner.nextLine());
                System.out.println("2. Overdraft balance: ");
                int overdraftBalance = Integer.parseInt( scanner.nextLine());
                CurrentAccountDTO currentAccountDTO  = new CurrentAccountDTO();
                currentAccountDTO.setAccountType(AccountType.CA);
                currentAccountDTO.setBalance(initialBalance);
                currentAccountDTO.setOverDraft(overdraftBalance);
                currentAccountDTO.setClientId(authService.getAuthenticatedClient().getId());
                accountService.createCurrentAccount(currentAccountDTO);
                System.out.println("current account created");

                break;
            case "2":

                System.out.println("\nCreating saving account...");
                SavingAccountDTO savingAccountDTO  = new SavingAccountDTO();
                savingAccountDTO.setAccountType(AccountType.SA);
                System.out.println("1. Initial balance: ");
                int saInitialBalance = Integer.parseInt( scanner.nextLine());
                System.out.println("Interest rate is 2.5% ");
                savingAccountDTO.setBalance(saInitialBalance);
                savingAccountDTO.setInterestRate(2.5/100);
                savingAccountDTO.setClientId(authService.getAuthenticatedClient().getId());
                accountService.createSavingAccount(savingAccountDTO);
                System.out.println("saving account created");
                break;
            case "0":
                return; // Go back to the previous menu
            default:
                System.err.println("Invalid choice.");
        }
    }

    public void handlePrintStatments(){
        accountService.printStatement();
    }
}
