package com.ironhack.demo.Account.AccountTypes.Savings;

import com.ironhack.demo.Address.Address;
import com.ironhack.demo.Money.Money;
import com.ironhack.demo.User.UserTypes.AccountHolder.AccountHolder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class SavingsSettersTests {

    @Test
    @DisplayName("SetInterestRate -- interest Rate is greater than 0.5, throws error")
    public void setInterestRate_GreaterThanMaxPermitted_ThrowsError(){

        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user = new AccountHolder("pepe87", "password", LocalDate.parse("1987-06-02"), address, null );

        BigDecimal interestRate = new BigDecimal("0.6");

        assertThrows(IllegalArgumentException.class,
                ()-> new Savings(new Money(new BigDecimal(1000)), user, null, new BigDecimal("0.4"),  interestRate, "secretKey"));
    }

    @Test
    @DisplayName("SetMinimumBalance -- minimum balance is greater than 1000 or less than 100, throws error")
    public void setMinimumBalance_GreaterThanMaxPermitted_ThrowsError(){

        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user = new AccountHolder("pepe87", "password", LocalDate.parse("1987-06-02"), address, null );

        Money minimumBalanceGreaterThan1000 = new Money(new BigDecimal("1100"));
        Money minimumBalanceLessThan100 = new Money(new BigDecimal("90"));


      assertThrows(IllegalArgumentException.class,
                ()-> new Savings(new Money(new BigDecimal(1000)), user, null, new BigDecimal("0.4"), minimumBalanceGreaterThan1000, "secretKey"));

      assertThrows(IllegalArgumentException.class,
                ()-> new Savings(new Money(new BigDecimal(1000)), user, null, new BigDecimal("0.4"), minimumBalanceLessThan100, "secretKey"));
    }
}
