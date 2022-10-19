package com.ironhack.banksystem.account.accountTypes;

import com.ironhack.banksystem.account.Account;
import com.ironhack.banksystem.money.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class CreditCard extends Account {

    @DecimalMax("0.2")
    @DecimalMin("0.1")
    private BigDecimal interestRate = new BigDecimal("0.2");

    @Max(100000)
    @Min(100)
    private Money creditLimit = new Money(new BigDecimal(100));
}
