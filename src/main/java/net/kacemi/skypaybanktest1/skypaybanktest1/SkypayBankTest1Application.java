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





    }
}
