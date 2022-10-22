package com.ironhack.banksystem.account.accountTypes.savings;

import com.ironhack.banksystem.address.Address;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.role.EnumRole;
import com.ironhack.banksystem.role.Role;
import com.ironhack.banksystem.role.RoleRepository;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class SavingsSettersTests {

    @Autowired
    private RoleRepository roleRepository;

    AccountHolder user;

    @BeforeEach
    public void setUp(){
        Role role = roleRepository.findByName(EnumRole.ACCOUNT_HOLDER).get();
        Address address = new Address("Roma n25", "Madrid", 06754);
        user = new AccountHolder("pepe87", "password", LocalDate.parse("1987-06-02"), address, null, role );
    }

    @Test
    @DisplayName("SetInterestRate -- interest Rate is greater than 0.5, throws error")
    public void setInterestRate_GreaterThanMaxPermitted_ThrowsError(){

        BigDecimal interestRate = new BigDecimal("0.6");

        assertThrows(IllegalArgumentException.class,
                ()-> new Savings(new Money(new BigDecimal(1000)), user, null,  interestRate, new Money(), "secretKey"));
    }

    @Test
    @DisplayName("SetMinimumBalance -- minimum balance is greater than 1000 or less than 100, throws error")
    public void setMinimumBalance_ValueOutOfRang_ThrowsError(){

        Money minimumBalanceGreaterThan1000 = new Money(new BigDecimal("1100"));
        Money minimumBalanceLessThan100 = new Money(new BigDecimal("90"));


        assertThrows(IllegalArgumentException.class,
                ()-> new Savings(new Money(new BigDecimal(1000)), user, null, new BigDecimal("0.3"), minimumBalanceGreaterThan1000, "secretKey"));

        assertThrows(IllegalArgumentException.class,
                ()-> new Savings(new Money(new BigDecimal(1000)), user, null,new BigDecimal("0.3"), minimumBalanceLessThan100, "secretKey"));
    }

    @Test
    @DisplayName("SetMinimumBalance -- null value, assign default value")
    public void setMinimumBalance_NullValue_AssignDefaultValue() throws ParseException {

        Money minimumBalance = null;
        Savings savings = new Savings(new Money(new BigDecimal(1000)), user, null, new BigDecimal("0.3"), minimumBalance, "secretKey");
        assertEquals(new Money(BigDecimal.valueOf(1000)), savings.getMinimumBalance());
    }

    @Test
    @DisplayName("SetInterestRate -- null value, assign default value")
    public void setInterestRate_NullValue_AssignDefaultValue(){

        BigDecimal interestRate = null;
        Savings savings = new Savings(new Money(new BigDecimal(1000)), user, null, interestRate, new Money(new BigDecimal("150")), "secretKey");

        assertEquals(BigDecimal.valueOf(0.0025), savings.getInterestRate());
    }

    @Test
    @DisplayName("applyInterestRate -- works ok")
    public void applyInterestRate_WorksOk() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date creationAccountFakeDate = formatter.parse("26-11-2020");
        Date currentFakeDate = formatter.parse("26-12-2022");

        BigDecimal interestRate = BigDecimal.valueOf(0.5);
        Savings savings1 = new Savings(new Money(new BigDecimal(1000)), user, null, interestRate, new Money(new BigDecimal("150")), "secretKey");
        Savings savings2 = new Savings(new Money(new BigDecimal(1000)), user, null, interestRate, new Money(new BigDecimal("150")), "secretKey");


        assertEquals(new BigDecimal("2250.0000"), savings1.applyInterestRate(creationAccountFakeDate, currentFakeDate, 0).getAmount());
        assertEquals(new BigDecimal("2250.0000"), savings1.getBalance().getAmount());
        assertEquals(2, savings1.getYearsOfInterestRateApplied());
        assertEquals(new BigDecimal("1000.00"), savings2.applyInterestRate(creationAccountFakeDate, currentFakeDate, 2).getAmount());
        assertEquals(new BigDecimal("1500.000"), savings2.applyInterestRate(creationAccountFakeDate, currentFakeDate, 1).getAmount());
    }
}
