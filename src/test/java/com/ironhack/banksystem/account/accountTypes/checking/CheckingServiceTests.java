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
public class CheckingServiceTests {

    @Autowired
    CheckingService checkingService;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    CheckingRepository checkingRepository;

    @BeforeEach
    public void setUp(){
        checkingRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @AfterEach
    public void clean(){
        checkingRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    @DisplayName("Save new checking account - works ok")
    void add_WorksOk() {
        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user = new AccountHolder("pepe87", "password", LocalDate.parse("1987-06-02"), address, null );
        accountHolderRepository.save(user);
        CheckingDTO accountInfo = new CheckingDTO(new BigDecimal("2000"), "pepe87", "anton763", new BigDecimal(0.3), "secretKey");

        AddCheckingAccountReturnedDTO checkingAccountSaved = checkingService.add(accountInfo, user, null);

        Optional<Checking> optionalChecking = checkingRepository.findById(checkingAccountSaved.getId());

        assertTrue(optionalChecking.isPresent());
        assertEquals("pepe87", optionalChecking.get().getPrimaryOwner().getUsername());
    }

}
