package com.ironhack.demo.Account.AccountTypes.Savings;

import com.ironhack.demo.Account.Account;
import com.ironhack.demo.Account.EnumStatusAccount;
import com.ironhack.demo.Money.Money;
import com.ironhack.demo.User.UserTypes.AccountHolder.AccountHolder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
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

    @NotNull
    @Column(nullable = false)
    private String secretKey;

    private final Date CREATION_DATE = new Date(System.currentTimeMillis());

    private EnumStatusAccount status = EnumStatusAccount.ACTIVE;

    public Savings(Money balance,
                   AccountHolder primaryOwner,
                   AccountHolder secondaryOwner,
                   BigDecimal penaltyFee,
                   String secretKey) {
        super(balance, primaryOwner, secondaryOwner, penaltyFee);
        this.secretKey = secretKey;
    }

    public Savings(Money balance,
                   AccountHolder primaryOwner,
                   AccountHolder secondaryOwner,
                   BigDecimal penaltyFee,
                   BigDecimal interestRate,
                   Money minimumBalance,
                   String secretKey) {
        super(balance, primaryOwner, secondaryOwner, penaltyFee);
        this.interestRate = interestRate;
        this.minimumBalance = minimumBalance;
        this.secretKey = secretKey;
    }

    public Savings(Money balance,
                   AccountHolder primaryOwner,
                   AccountHolder secondaryOwner,
                   BigDecimal penaltyFee,
                   Money minimumBalance,
                   String secretKey) {
        super(balance, primaryOwner, secondaryOwner, penaltyFee);
        this.minimumBalance = minimumBalance;
        this.secretKey = secretKey;
    }

    public Savings(Money balance,
                   AccountHolder primaryOwner,
                   AccountHolder secondaryOwner,
                   BigDecimal penaltyFee,
                   BigDecimal interestRate,
                   String secretKey) {
        super(balance, primaryOwner, secondaryOwner, penaltyFee);
        this.interestRate = interestRate;
        this.secretKey = secretKey;
    }
}
