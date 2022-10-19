package com.ironhack.banksystem.account.accountTypes.savings;

import com.ironhack.banksystem.account.Account;
import com.ironhack.banksystem.account.EnumStatusAccount;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Savings extends Account {

    @DecimalMax(value = "0.5", inclusive = true)
    private BigDecimal interestRate;

    private Money minimumBalance;

    @NotNull
    @Column(nullable = false)
    private String secretKey;

    private final Date CREATION_DATE = new Date(System.currentTimeMillis());

    private EnumStatusAccount status = EnumStatusAccount.ACTIVE;

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal penaltyFee, BigDecimal interestRate, Money minimumBalance, String secretKey) {
        super(balance, primaryOwner, secondaryOwner, penaltyFee);
        setInterestRate(interestRate);
        setMinimumBalance(minimumBalance);
        this.secretKey = secretKey;
    }

    public void setInterestRate(BigDecimal interestRate) {
        if (interestRate == null) this.interestRate = new BigDecimal("0.0025");
        else if(interestRate.compareTo(new BigDecimal("0.5")) > 0) {
            throw new IllegalArgumentException("Interest rate can't be greater than 0.5");
        } else {
            this.interestRate = interestRate;
        }
    }

    public void setMinimumBalance(Money minimumBalance) {
        if (minimumBalance == null) this.minimumBalance = new Money(BigDecimal.valueOf(1000));
        else if(minimumBalance.getAmount().compareTo(new BigDecimal(100)) < 0
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
