package com.ironhack.banksystem.account.accountTypes.savings;

import com.ironhack.banksystem.Utils;
import com.ironhack.banksystem.account.Account;
import com.ironhack.banksystem.account.EnumAccountStatus;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    private int yearsOfInterestRateApplied = 0;

    @Enumerated(EnumType.STRING)
    private EnumAccountStatus status = EnumAccountStatus.ACTIVE;

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal interestRate, Money minimumBalance, String secretKey) {
        super(balance, primaryOwner, secondaryOwner);
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
        else if(minimumBalance.getAmount().compareTo(BigDecimal.valueOf(100)) < 0
                || minimumBalance.getAmount().compareTo(BigDecimal.valueOf(1000)) > 0) {
            throw new IllegalArgumentException("Minimum balance must be between 100 and 1000");
         } else {
            this.minimumBalance = minimumBalance;
        }
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }


    @Override
    public void withdraw(BigDecimal amount) {
        checkPenaltyFee();
        if(this.getBalance().getAmount().compareTo(amount) > 0) {
            super.getBalance().setAmount(super.getBalance().getAmount().subtract(amount));
        } else throw new IllegalArgumentException("Insufficient funds");
    }

    @Override
    public Money checkBalance() {
        applyInterestRate();
        checkPenaltyFee();
        return this.getBalance();
    }

    private void checkPenaltyFee() {
        if(super.getBalance().getAmount().compareTo(minimumBalance.getAmount()) < 0) {
            super.withdraw(super.getPENALTY_FEE());
        }
    }

    private int checkYearsFromCreatedAccount() {
        return Utils.getYearsBetweenToDates(getCREATION_DATE(), new Date(System.currentTimeMillis()));
    }

    private void applyInterestRate() {
        int yearsToPay = checkYearsFromCreatedAccount() - yearsOfInterestRateApplied;
        if(yearsToPay > 0) {
            deposit(getBalance().getAmount().multiply(new BigDecimal(yearsToPay).add(interestRate)));
            yearsOfInterestRateApplied += yearsToPay;
        }
    }

    //OverloadingToTest
    public int checkYearsFromCreatedAccount(Date dateCreationAccount, Date currentDateToTest) {
        return Utils.getYearsBetweenToDates(dateCreationAccount, currentDateToTest);
    }

    public Money applyInterestRate(Date dateCreationAccountToTest, Date currentDateToTest, int yearsOfInterestRatePaid) {
        int yearsToPay = checkYearsFromCreatedAccount(dateCreationAccountToTest, currentDateToTest) - yearsOfInterestRatePaid;
        for (int i = yearsToPay; i > 0; i--) {
            BigDecimal addToAccount = getBalance().getAmount().multiply(interestRate);
            deposit(addToAccount);
        }
        yearsOfInterestRateApplied += yearsToPay;
        return getBalance();
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
