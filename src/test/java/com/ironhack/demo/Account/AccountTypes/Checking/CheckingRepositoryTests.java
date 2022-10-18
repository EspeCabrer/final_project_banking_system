package com.ironhack.demo.Account.AccountTypes.Checking;

import com.ironhack.demo.Address.Address;
import com.ironhack.demo.Money.Money;
import com.ironhack.demo.User.UserTypes.AccountHolder.AccountHolder;
import com.ironhack.demo.User.UserTypes.AccountHolder.AccountHolderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CheckingRepositoryTests {

    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @BeforeEach
    void setUp() {
        checkingRepository.deleteAll();
    }

    @Test
    @DisplayName("Save new checking account - works ok")
    void addCheckingAccount() {
        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user = new AccountHolder("pepe87", "password", LocalDate.parse("1987-06-02"), address, null );
        accountHolderRepository.save(user);
        Checking account = new Checking(new Money(new BigDecimal(2000)), user, null, new BigDecimal("0.4"), "secretKey");

        Checking savedAccount = checkingRepository.save(account);
        Optional<Checking> optionalAccountHolder = checkingRepository.findById(savedAccount.getId());

        assertTrue(optionalAccountHolder.isPresent());
        assertEquals("pepe87", optionalAccountHolder.get().getPrimaryOwner().getUsername() );
    }


}
