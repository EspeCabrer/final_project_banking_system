package com.ironhack.banksystem.account.accountTypes.creditCard;

import com.ironhack.banksystem.account.accountTypes.savings.Savings;
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
public class CreditCardRepositoryTests {

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @BeforeEach
    public void setUp(){
        creditCardRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }
    @AfterEach
    public void clean(){
        creditCardRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void addCreditcardAccount_worksOk() {
        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user = new AccountHolder("antonia34", "password", LocalDate.parse("2000-06-02"), address, null );
        accountHolderRepository.save(user);
        CreditCard creditCard = creditCardRepository.save(new CreditCard(new Money(BigDecimal.valueOf(3000)), user, null, BigDecimal.valueOf(0.6),
                null, null));

        Optional<CreditCard> creditCardOptional = creditCardRepository.findById(creditCard.getId());

        assertTrue(creditCardOptional.isPresent());
    }
}
