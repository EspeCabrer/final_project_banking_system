package com.ironhack.demo.User.UserTypes.AccountHolder;

import com.ironhack.demo.Address.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AccountHolderServiceTests {

    @Autowired
    AccountHolderService accountHolderService;

    @Autowired
    AccountHolderRepository accountHolderRepository;


    @Test
    @DisplayName("Save new account holder user - works ok")
    void addAccountHolder() {
        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolderDTO userInfo = new AccountHolderDTO("Miqui657", "password", "1999-01-06", address, null);

        AccountHolder savedUser = accountHolderService.add(userInfo);
        Optional<AccountHolder> optionalAccountHolder = accountHolderRepository.findById(savedUser.getId());

        assertTrue(optionalAccountHolder.isPresent());
        assertEquals("Miqui657", optionalAccountHolder.get().getUsername());
    }
}
