package com.ironhack.demo.Account.AccountTypes;

import com.ironhack.demo.Account.Account;
import com.ironhack.demo.Account.EnumStatusAccount;
import com.ironhack.demo.Money.Money;
import com.ironhack.demo.User.UserTypes.AccountHolder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Savings extends Account {


    @DecimalMax(value = "0.5", inclusive = true)
    private BigDecimal interestRate = new BigDecimal("0.0025");

    @Max(1000)
    @Min(100)
    private Money minimumBalance = new Money(new BigDecimal(1000));

    private String secretKey;

    private final Date CREATION_DATE = new Date(System.currentTimeMillis());

    private EnumStatusAccount status;

}
