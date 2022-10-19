package com.ironhack.banksystem.account.accountTypes.savings;

import com.ironhack.banksystem.address.Address;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolder;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SavingsRepositoryTests {

    @Autowired
    SavingsRepository savingsRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @BeforeEach
    public void setUp(){
        savingsRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }
    @AfterEach
    public void clean(){
        savingsRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void addSavingAccount_worksOk() {
        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user = new AccountHolder("antonia34", "password", LocalDate.parse("2000-06-02"), address, null );
        accountHolderRepository.save(user);
        Savings savings = savingsRepository.save(new Savings(new Money(BigDecimal.valueOf(3000)), user, null, BigDecimal.valueOf(0.6),
                null, null, "secretKey"));

        Optional<Savings> savingsOptional = savingsRepository.findById(savings.getId());

        assertTrue(savingsOptional.isPresent());
    }
}
