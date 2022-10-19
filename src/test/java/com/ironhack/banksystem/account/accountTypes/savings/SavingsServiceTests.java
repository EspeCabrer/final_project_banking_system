package com.ironhack.banksystem.account.accountTypes.savings;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SavingsServiceTests {

    @Autowired
    SavingsRepository savingsRepository;


    @BeforeEach
    void setUp() {
        savingsRepository.deleteAll();

    }
}
