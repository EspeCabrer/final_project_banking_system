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
public class StudentCheckingRepositoryTests {

    @Autowired
    StudentCheckingRepository studentCheckingRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @BeforeEach
    void setUp() {
        studentCheckingRepository.deleteAll();
    }

    @Test
    @DisplayName("Save new student checking account - works ok")
    void addCheckingAccount() {
        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user = new AccountHolder("antonia34", "password", LocalDate.parse("2000-06-02"), address, null );
        accountHolderRepository.save(user);


        StudentChecking account = new StudentChecking(new Money(new BigDecimal(2000)), user, null, new BigDecimal("0.4"), "secretKey");
        StudentChecking savedAccount = studentCheckingRepository.save(account);
        Optional<StudentChecking> optionalStudentChecking = studentCheckingRepository.findById(savedAccount.getId());

        assertTrue(optionalStudentChecking.isPresent());
        assertEquals("antonia34", optionalStudentChecking.get().getPrimaryOwner().getUsername() );
    }

}
