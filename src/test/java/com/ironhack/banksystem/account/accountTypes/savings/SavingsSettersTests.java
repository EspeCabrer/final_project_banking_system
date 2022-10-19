package com.ironhack.banksystem.account.accountTypes.savings;

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
public class SavingsSettersTests {

    AccountHolder user;

    @BeforeEach
    public void setUp(){
        Address address = new Address("Roma n25", "Madrid", 06754);
        user = new AccountHolder("pepe87", "password", LocalDate.parse("1987-06-02"), address, null );
    }

    @Test
    @DisplayName("SetInterestRate -- interest Rate is greater than 0.5, throws error")
    public void setInterestRate_GreaterThanMaxPermitted_ThrowsError(){

        BigDecimal interestRate = new BigDecimal("0.6");

        assertThrows(IllegalArgumentException.class,
                ()-> new Savings(new Money(new BigDecimal(1000)), user, null, new BigDecimal("0.4"),  interestRate, new Money(), "secretKey"));
    }

    @Test
    @DisplayName("SetMinimumBalance -- minimum balance is greater than 1000 or less than 100, throws error")
    public void setMinimumBalance_GreaterThanMaxPermitted_ThrowsError(){

        Money minimumBalanceGreaterThan1000 = new Money(new BigDecimal("1100"));
        Money minimumBalanceLessThan100 = new Money(new BigDecimal("90"));


        assertThrows(IllegalArgumentException.class,
                ()-> new Savings(new Money(new BigDecimal(1000)), user, null, new BigDecimal("0.4"), new BigDecimal("0.3"), minimumBalanceGreaterThan1000, "secretKey"));

        assertThrows(IllegalArgumentException.class,
                ()-> new Savings(new Money(new BigDecimal(1000)), user, null, new BigDecimal("0.4"),new BigDecimal("0.3"), minimumBalanceLessThan100, "secretKey"));
    }

    @Test
    @DisplayName("SetMinimumBalance -- null value, assign default value")
    public void setMinimumBalance_NullValue_AssignDefaultValue(){

        Money minimumBalance = null;
        Savings savings = new Savings(new Money(new BigDecimal(1000)), user, null, new BigDecimal("0.4"), new BigDecimal("0.3"), minimumBalance, "secretKey");

        assertEquals(new Money(BigDecimal.valueOf(1000)), savings.getMinimumBalance());
    }

    @Test
    @DisplayName("SetInterestRate -- null value, assign default value")
    public void setInterestRate_NullValue_AssignDefaultValue(){

        BigDecimal interestRate = null;
        Savings savings = new Savings(new Money(new BigDecimal(1000)), user, null, new BigDecimal("0.4"), interestRate, new Money(new BigDecimal("150")), "secretKey");

        assertEquals(BigDecimal.valueOf(0.0025), savings.getInterestRate());
    }
}
