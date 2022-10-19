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

    Address address;

    @BeforeEach
    void setUp() {
        address = new Address("Roma n25", "Madrid", 06754);

    }

    @Test
    @DisplayName("Save new account holder user - works ok")
    void add_WorksOk() {
        AccountHolderDTO userInfo = new AccountHolderDTO("Miqui657", "password", "1999-01-06", address, null);

        AccountHolder savedUser = accountHolderService.add(userInfo);
        Optional<AccountHolder> optionalAccountHolder = accountHolderRepository.findById(savedUser.getId());

        assertTrue(optionalAccountHolder.isPresent());
        assertEquals("Miqui657", optionalAccountHolder.get().getUsername());
    }

    @Test
    @DisplayName("Find accountHolder by username - works ok")
    void findByUsername_WorksOk()  {

        AccountHolder user = new AccountHolder("pepe87", "password", LocalDate.parse("1987-06-02"), address, null );
        accountHolderRepository.save(user);

        Optional<AccountHolder> optionalAccountHolder = accountHolderRepository.findByUsername("pepe87");

        assertTrue(optionalAccountHolder.isPresent());
        assertEquals("pepe87", optionalAccountHolder.get().getUsername());
    }

    @Test
    @DisplayName("Get user age - works ok")
    void ageCalculator_WorksOk()  {
        LocalDate birthDate = LocalDate.parse("2000-05-07");
        assertEquals(22, accountHolderService.ageCalculator(birthDate));
    }
}
