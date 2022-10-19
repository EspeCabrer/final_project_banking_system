package com.ironhack.demo.user.UserTypes.AccountHolder;

import com.ironhack.demo.address.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AccountHolderRepositoryTests {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @BeforeEach
    void setUp() {
        accountHolderRepository.deleteAll();
    }

    @Test
    @DisplayName("Save new account holder user - works ok")
    void addAccountHolder() {
        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user = new AccountHolder("pepe87", "password", LocalDate.parse("1987-06-02"), address, null );

        AccountHolder savedUser = accountHolderRepository.save(user);
        Optional<AccountHolder> optionalAccountHolder = accountHolderRepository.findById(savedUser.getId());

        assertTrue(optionalAccountHolder.isPresent());
        assertEquals("pepe87", optionalAccountHolder.get().getUsername() );
    }

    @Test
    @DisplayName("Find by userName - works ok")
    void findByUsername_WorksOk() {
        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user = new AccountHolder("pepe87", "password", LocalDate.parse("1987-06-02"), address, null );

        accountHolderRepository.save(user);
        Optional<AccountHolder> optionalAccountHolder = accountHolderRepository.findByUsername("pepe87");

        assertTrue(optionalAccountHolder.isPresent());
        assertEquals("pepe87", optionalAccountHolder.get().getUsername() );
    }
}
