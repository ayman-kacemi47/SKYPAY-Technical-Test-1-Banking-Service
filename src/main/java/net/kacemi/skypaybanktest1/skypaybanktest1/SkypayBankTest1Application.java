package net.kacemi.skypaybanktest1.skypaybanktest1;

import net.kacemi.skypaybanktest1.skypaybanktest1.controllers.BankCliController;
import net.kacemi.skypaybanktest1.skypaybanktest1.services.AccountServiceImpl;
import net.kacemi.skypaybanktest1.skypaybanktest1.services.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SkypayBankTest1Application implements CommandLineRunner {
        private final BankCliController bankCliController;
    public SkypayBankTest1Application(BankCliController bankCliController) {
        this.bankCliController = bankCliController;
    }
    public static void main(String[] args) {
		SpringApplication.run(SkypayBankTest1Application.class, args);
	}

    @Override
    public void run(String... args) throws Exception {

        bankCliController.runMainLoop();



//
//        System.out.println("Welcome to Skypay Bank");
//        System.out.println("-------- MENU 0 --------");
//        System.out.println("1. Login");
//        System.out.println("2. Sign up");
//        System.out.println("0. Exit");
//
//        System.out.println("-------- MENU 1 (login) --------");
//        System.out.println("Enter your username:");
//        System.out.println("Enter your password:");
//
//        System.out.println("-------- MENU 2 (sign up) --------");
//        System.out.println("Enter a username:");
//        System.out.println("Enter your password:");
//
//        System.out.println("--------" + authenticatedUser.getFullName()+ " space --------");
//        System.out.println("1. Deposit");
//        System.out.println("2. Withdraw");
//        System.out.println("3. Make transfert");
//        System.out.println("4. Create Account");
//        System.out.println("0. Sign Out");
//
//
//        System.out.println("-------- MENU TRANSACTION --------");
//        System.out.println("acounts table");
//        System.out.println("Enter account id:");
//        System.out.println("Enter amount to XXXX:");
//
//        System.out.println("-------- MENU TRANSFERT --------");
//        System.out.println("acounts table");
//        System.out.println("Enter your account id:");
//        System.out.println("Enter reciever account id:");
//        System.out.println("Enter amount to XXXX:");
//        System.out.println("Enter description:");
//
//        System.out.println("-------- MENU CREATE ACCOUNT --------");
//        System.out.println("1. Create current account");
//        System.out.println("2. Create saving account");
//        System.out.println("0. Iintial menu");
//
//        System.out.println("-------- MENU CREATE CA --------");
//        System.out.println("1. Initial balance: ");
//        System.out.println("2. Overdraft balance: ");
//        System.out.println("0. Iintial menu");
//
//        System.out.println("-------- MENU CREATE SA --------");
//        System.out.println("1. Initial balance: ");
//        System.out.println("2. Interest rate: ");
//        System.out.println("0. Iintial menu");
//


    }
}
