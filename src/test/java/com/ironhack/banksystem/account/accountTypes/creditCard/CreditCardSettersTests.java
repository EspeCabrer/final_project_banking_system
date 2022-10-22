package com.ironhack.banksystem.account.accountTypes.creditCard;

import com.ironhack.banksystem.address.Address;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.role.EnumRole;
import com.ironhack.banksystem.role.Role;
import com.ironhack.banksystem.role.RoleRepository;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolder;
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
public class CreditCardSettersTests {

    @Autowired
    private RoleRepository roleRepository;


    AccountHolder user;

    @BeforeEach
    public void setUp() {
        Role role = roleRepository.findByName(EnumRole.ACCOUNT_HOLDER).get();
        Address address = new Address("Roma n25", "Madrid", 06754);
        user = new AccountHolder("pepe87", "password", LocalDate.parse("1987-06-02"), address, null, role);
    }

    @Test
    @DisplayName("SetInterestRate -- interest Rate is greater than 0.2 or less than 0.1, throws error")
    public void setInterestRate_GreaterThanMaxPermitted_ThrowsError() {

        BigDecimal interestRateGreaterThanMax = BigDecimal.valueOf(0.21);
        BigDecimal interestRateLessThanMin = BigDecimal.valueOf(0.09);

        assertThrows(IllegalArgumentException.class,
                () -> new CreditCard(new Money(BigDecimal.valueOf(1000)), user, null, interestRateGreaterThanMax, new Money(BigDecimal.valueOf(1000))));

        assertThrows(IllegalArgumentException.class,
                () -> new CreditCard(new Money(BigDecimal.valueOf(1000)), user, null, interestRateLessThanMin, new Money(BigDecimal.valueOf(1000))));

    }

    @Test
    @DisplayName("SetInterestRate -- null value, assign default value")
    public void setInterestRate_NullValue_AssignDefaultValue() {

        BigDecimal interestRate = null;
        CreditCard creditCard = new CreditCard(new Money(BigDecimal.valueOf(1000)), user, null, interestRate, new Money(BigDecimal.valueOf(1000)));

        assertEquals(BigDecimal.valueOf(0.2), creditCard.getInterestRate());
    }

    @Test
    @DisplayName("SetCreditLimit -- credit limit is greater than 100000 or less than 100, throws error")
    public void setCreditLimit_ValueOutOfRang_ThrowsError() {

        Money creditLimitGreaterThanMax = new Money(BigDecimal.valueOf(100000.1));
        Money creditLimitGreaterLessThanMin = new Money(BigDecimal.valueOf(99.9));

        assertThrows(IllegalArgumentException.class,
                () -> new CreditCard(new Money(BigDecimal.valueOf(1000)), user, null, null, creditLimitGreaterLessThanMin));

        assertThrows(IllegalArgumentException.class,
                () -> new CreditCard(new Money(BigDecimal.valueOf(1000)), user, null, null, creditLimitGreaterThanMax));
    }

    @Test
    @DisplayName("SetCreditLimit -- null value, assign default value")
    public void setCreditLimit_NullValue_AssignDefaultValue() {

        Money creditLimit = null;
        CreditCard creditCard = new CreditCard(new Money(BigDecimal.valueOf(1000)), user, null, null, creditLimit);

        assertEquals(new Money(BigDecimal.valueOf(100)), creditCard.getCreditLimit());
    }

    @Test
    @DisplayName("applyInterestRateMethod -- works ok")
    public void applyInterestRate_WorksOk() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date creationAccountFakeDate = formatter.parse("01-01-2020");
        Date currentFakeDate = formatter.parse("05-03-2020");

        BigDecimal interestRate = BigDecimal.valueOf(0.12);
        CreditCard creditCard1 = new CreditCard(new Money(BigDecimal.valueOf(1000)), user, null, interestRate, null);
        CreditCard creditCard2 = new CreditCard(new Money(BigDecimal.valueOf(1000)), user, null, interestRate, null);


        assertEquals(1020.1, creditCard1.applyInterestRate(creationAccountFakeDate, currentFakeDate, 0).getAmount().doubleValue());
        assertEquals(1020.1, creditCard1.getBalance().getAmount().doubleValue());
        assertEquals(2, creditCard1.getMonthsOfInterestRateApplied());
        assertEquals(new BigDecimal("1000.00"), creditCard2.applyInterestRate(creationAccountFakeDate, currentFakeDate, 2).getAmount());
    }
}
