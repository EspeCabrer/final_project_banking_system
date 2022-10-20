package com.ironhack.banksystem.account.accountTypes.savings;

import com.ironhack.banksystem.address.Address;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolder;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SavingsServiceTests {

    @Autowired
    SavingsRepository savingsRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    SavingsService savingsService;


    @BeforeEach
    void setUp() {
        savingsRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @AfterEach
    void clean(){
        savingsRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    public void add_WorksOk() {
        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user = new AccountHolder("pepe87", "password", LocalDate.parse("1987-06-02"), address, null );
        accountHolderRepository.save(user);
        SavingsDTO savingsDTO = new SavingsDTO(BigDecimal.valueOf(1000), "pepe87", null,null, null, "secretKey");

        Savings savingsAccountSaved = savingsService.add(savingsDTO, user, null);
        assertTrue(savingsRepository.findById(savingsAccountSaved.getId()).isPresent());
    }
}
