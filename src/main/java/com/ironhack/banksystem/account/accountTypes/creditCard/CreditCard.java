package com.ironhack.banksystem.account.accountTypes.creditCard;

import com.ironhack.banksystem.account.Account;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolder;
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
@Getter
public class CreditCard extends Account {

    @DecimalMax("0.2")
    @DecimalMin("0.1")
    private BigDecimal interestRate;

    private Money creditLimit;

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal penaltyFee, BigDecimal interestRate, Money creditLimit) {
        super(balance, primaryOwner, secondaryOwner, penaltyFee);
        setInterestRate(interestRate);
        setCreditLimit(creditLimit);
    }

    public void setInterestRate(BigDecimal interestRate) {
        if (interestRate == null) this.interestRate = BigDecimal.valueOf(0.2);
        else if(interestRate.compareTo(BigDecimal.valueOf(0.2)) > 0 ||
                interestRate.compareTo(BigDecimal.valueOf(0.1)) < 0 ) {
            throw new IllegalArgumentException("Interest rate must be between 0.2 and 0.1");
        } else {
            this.interestRate = interestRate;
        }
    }

    public void setCreditLimit(Money creditLimit) {
        if (creditLimit == null) this.creditLimit = new Money(BigDecimal.valueOf(100));
        else if(creditLimit.getAmount().compareTo(BigDecimal.valueOf(100)) < 0
                || creditLimit.getAmount().compareTo(BigDecimal.valueOf(100000)) > 0) {
            throw new IllegalArgumentException("Credit limit must be between 100 and 100000");
        } else {
            this.creditLimit = creditLimit;
        }
    }
}
