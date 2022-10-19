package com.ironhack.banksystem.account.accountTypes.checking;

import com.ironhack.banksystem.account.accountTypes.checking.DTO.AddCheckingAccountReturnedDTO;
import com.ironhack.banksystem.account.accountTypes.checking.DTO.CheckingDTO;
import com.ironhack.banksystem.address.Address;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolder;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolderRepository;
import org.junit.jupiter.api.AfterEach;
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
public class StudentCheckingServiceTests {

    @Autowired
    StudentCheckingService studentCheckingService;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    StudentCheckingRepository studentCheckingRepository;

    @BeforeEach
    public void setUp(){
        studentCheckingRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @AfterEach
    public void clean(){
        studentCheckingRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }


    @Test
    @DisplayName("Save new student checking account - works ok")
    void add_WorksOk() {
        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user = new AccountHolder("pepe2000", "password", LocalDate.parse("2000-06-02"), address, null );
        accountHolderRepository.save(user);
        CheckingDTO accountInfo = new CheckingDTO(new Money(new BigDecimal("2000")), "pepe87", "anton763", new BigDecimal(0.3), "secretKey");

        AddCheckingAccountReturnedDTO studentCheckingSaved = studentCheckingService.add(accountInfo, user, null);

        Optional<StudentChecking> optionalChecking = studentCheckingRepository.findById(studentCheckingSaved.getId());

        assertTrue(optionalChecking.isPresent());
        assertEquals("pepe2000", optionalChecking.get().getPrimaryOwner().getUsername());
    }
}
