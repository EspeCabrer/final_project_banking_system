package com.ironhack.banksystem.account.accountTypes.creditCard;

import com.ironhack.banksystem.account.accountTypes.savings.Savings;
import com.ironhack.banksystem.address.Address;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CreditCardSettersTests {

    AccountHolder user;

    @BeforeEach
    public void setUp(){
        Address address = new Address("Roma n25", "Madrid", 06754);
        user = new AccountHolder("pepe87", "password", LocalDate.parse("1987-06-02"), address, null );
    }

    @Test
    @DisplayName("SetInterestRate -- interest Rate is greater than 0.2 or less than 0.1, throws error")
    public void setInterestRate_GreaterThanMaxPermitted_ThrowsError(){

        BigDecimal interestRateGreaterThanMax = BigDecimal.valueOf(0.21);
        BigDecimal interestRateLessThanMin = BigDecimal.valueOf(0.09);

        assertThrows(IllegalArgumentException.class,
                ()-> new CreditCard(new Money(BigDecimal.valueOf(1000)), user, null, BigDecimal.valueOf(0.4), interestRateGreaterThanMax, new Money(BigDecimal.valueOf(1000))));

        assertThrows(IllegalArgumentException.class,
                ()-> new CreditCard(new Money(BigDecimal.valueOf(1000)), user, null, BigDecimal.valueOf(0.4), interestRateLessThanMin, new Money(BigDecimal.valueOf(1000))));

    }

    @Test
    @DisplayName("SetInterestRate -- null value, assign default value")
    public void setInterestRate_NullValue_AssignDefaultValue(){

        BigDecimal interestRate = null;
        CreditCard creditCard = new CreditCard(new Money(BigDecimal.valueOf(1000)), user, null, BigDecimal.valueOf(0.4), interestRate, new Money(BigDecimal.valueOf(1000)));

        assertEquals(BigDecimal.valueOf(0.2), creditCard.getInterestRate());
    }

    @Test
    @DisplayName("SetCreditLimit -- credit limit is greater than 100000 or less than 100, throws error")
    public void setCreditLimit_ValueOutOfRang_ThrowsError(){

        Money creditLimitGreaterThanMax = new Money(BigDecimal.valueOf(100000.1));
        Money creditLimitGreaterLessThanMin = new Money(BigDecimal.valueOf(99.9));

        assertThrows(IllegalArgumentException.class,
                ()-> new CreditCard(new Money(BigDecimal.valueOf(1000)), user, null, BigDecimal.valueOf(0.4), null, creditLimitGreaterLessThanMin));

        assertThrows(IllegalArgumentException.class,
                ()-> new CreditCard(new Money(BigDecimal.valueOf(1000)), user, null, BigDecimal.valueOf(0.4), null, creditLimitGreaterThanMax));
    }

    @Test
    @DisplayName("SetCreditLimit -- null value, assign default value")
    public void setCreditLimit_NullValue_AssignDefaultValue(){

        Money creditLimit = null;
        CreditCard creditCard = new CreditCard(new Money(BigDecimal.valueOf(1000)), user, null, BigDecimal.valueOf(0.4), null, creditLimit);

        assertEquals(new Money(BigDecimal.valueOf(100)), creditCard.getCreditLimit());
    }



}
