package com.ironhack.banksystem.account.accountTypes.creditCard;

import com.ironhack.banksystem.account.accountTypes.savings.Savings;
import com.ironhack.banksystem.account.accountTypes.savings.SavingsDTO;
import com.ironhack.banksystem.account.accountTypes.savings.SavingsRepository;
import com.ironhack.banksystem.account.accountTypes.savings.SavingsService;
import com.ironhack.banksystem.address.Address;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolder;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CreditCardServiceTests {

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    CreditCardService creditCardService;


    @BeforeEach
    void setUp() {
        creditCardRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @AfterEach
    void clean(){
        creditCardRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    public void add_WorksOk() {
        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user = new AccountHolder("pepe87", "password", LocalDate.parse("1987-06-02"), address, null );
        accountHolderRepository.save(user);
        CreditCardDTO creditCardDTO = new CreditCardDTO(BigDecimal.valueOf(2000), "maria", null, BigDecimal.valueOf(300), BigDecimal.valueOf(0.2), BigDecimal.valueOf(20));

        CreditCard creditCardSaved = creditCardService.add(creditCardDTO, user, null);
        assertTrue(creditCardRepository.findById(creditCardSaved.getId()).isPresent());
    }


}
