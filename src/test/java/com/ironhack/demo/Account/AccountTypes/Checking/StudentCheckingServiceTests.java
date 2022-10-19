package com.ironhack.demo.Account.AccountTypes.Checking;

import com.ironhack.demo.Address.Address;
import com.ironhack.demo.Money.Money;
import com.ironhack.demo.User.UserTypes.AccountHolder.AccountHolder;
import com.ironhack.demo.User.UserTypes.AccountHolder.AccountHolderRepository;
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
public class StudentCheckingServiceTests {

    @Autowired
    StudentCheckingService studentCheckingService;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    StudentCheckingRepository studentCheckingRepository;


    @Test
    @DisplayName("Save new checking account - works ok")
    void add_WorksOk() {
        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user = new AccountHolder("pepe2000", "password", LocalDate.parse("2000-06-02"), address, null );
        accountHolderRepository.save(user);
        CheckingDTO accountInfo = new CheckingDTO(new Money(new BigDecimal("2000")), "pepe87", "anton763", new BigDecimal(0.3), "secretKey");

        StudentChecking studentCheckingSaved = studentCheckingService.add(accountInfo, user, null);

        Optional<StudentChecking> optionalChecking = studentCheckingRepository.findById(studentCheckingSaved.getId());

        assertTrue(optionalChecking.isPresent());
        assertEquals("pepe2000", optionalChecking.get().getPrimaryOwner().getUsername());
    }
}
