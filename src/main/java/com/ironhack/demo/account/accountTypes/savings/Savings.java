package com.ironhack.demo.account.accountTypes.savings;

import com.ironhack.demo.account.Account;
import com.ironhack.demo.account.EnumStatusAccount;
import com.ironhack.demo.money.Money;
import com.ironhack.demo.user.UserTypes.AccountHolder.AccountHolder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        setInterestRate(interestRate);
        setMinimumBalance(minimumBalance);
        this.secretKey = secretKey;
    }

    public Savings(Money balance,
                   AccountHolder primaryOwner,
                   AccountHolder secondaryOwner,
                   BigDecimal penaltyFee,
                   Money minimumBalance,
                   String secretKey) {
        super(balance, primaryOwner, secondaryOwner, penaltyFee);
        setMinimumBalance(minimumBalance);
        this.secretKey = secretKey;
    }

    public Savings(Money balance,
                   AccountHolder primaryOwner,
                   AccountHolder secondaryOwner,
                   BigDecimal penaltyFee,
                   BigDecimal interestRate,
                   String secretKey) {
        super(balance, primaryOwner, secondaryOwner, penaltyFee);
        setInterestRate(interestRate);
        this.secretKey = secretKey;
    }

    public void setInterestRate(BigDecimal interestRate) {
        if(interestRate.compareTo(new BigDecimal("0.5")) > 0) {
            throw new IllegalArgumentException("Interest rate can't be greater than 0.5");
        } else {
            this.interestRate = interestRate;
        }
    }

    public void setMinimumBalance(Money minimumBalance) {
        if(minimumBalance.getAmount().compareTo(new BigDecimal(100)) < 0
                || minimumBalance.getAmount().compareTo(new BigDecimal(1000)) > 0) {
            throw new IllegalArgumentException("Minimum balance must be between 100 and 1000");
         } else {
            this.minimumBalance = minimumBalance;
        }
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setStatus(EnumStatusAccount status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Savings{" +
                "interestRate=" + interestRate +
                ", minimumBalance=" + minimumBalance +
                ", secretKey='" + secretKey + '\'' +
                ", CREATION_DATE=" + CREATION_DATE +
                ", status=" + status +
                '}';
    }
}
