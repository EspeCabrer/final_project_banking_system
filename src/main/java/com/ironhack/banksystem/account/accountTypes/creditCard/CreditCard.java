package com.ironhack.banksystem.account.accountTypes.creditCard;

import com.ironhack.banksystem.Utils;
import com.ironhack.banksystem.account.Account;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class CreditCard extends Account {

    @DecimalMax("0.2")
    @DecimalMin("0.1")
    private BigDecimal interestRate;

    private Money creditLimit;

    private int monthsOfInterestRateApplied = 0;

    public CreditCard(Money balance, AccountHolder primaryOwner,
                      AccountHolder secondaryOwner,
                      BigDecimal interestRate, Money creditLimit) {
        super(balance, primaryOwner, secondaryOwner);
        setInterestRate(interestRate);
        setCreditLimit(creditLimit);
    }

    @Override
    public Money checkBalance() {
        applyInterestRate();
        return this.getBalance();
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

    private int checkMonthsFromCreatedAccount() {
        return Utils.getMonthsBetweenToDates(getCREATION_DATE(), new Date(System.currentTimeMillis()));
    }

    private void applyInterestRate() {
        BigDecimal monthlyInterest = getInterestRate().divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP);
        int monthsToPay = checkMonthsFromCreatedAccount() - monthsOfInterestRateApplied;
        for (int i = monthsToPay; i > 0; i--) {
            BigDecimal addToAccount = getBalance().getAmount().multiply(monthlyInterest);
            deposit(addToAccount);
        }
        monthsOfInterestRateApplied += monthsToPay;
    }

    //OverloadingToTest
     int checkMonthsFromCreatedAccount(Date dateCreationAccount, Date currentDateToTest) {
        return Utils.getMonthsBetweenToDates(dateCreationAccount, currentDateToTest);
    }

    public Money applyInterestRate(Date dateCreationAccountToTest, Date currentDateToTest, int monthsOfInterestRatePaid) {
        BigDecimal monthlyInterest = getInterestRate().divide(BigDecimal.valueOf(12), RoundingMode.HALF_EVEN);
        int monthsToPay = checkMonthsFromCreatedAccount(dateCreationAccountToTest, currentDateToTest) - monthsOfInterestRatePaid;
        for (int i = monthsToPay; i > 0; i--) {
            BigDecimal addToAccount = getBalance().getAmount().multiply(monthlyInterest);
            deposit(addToAccount);
        }
        monthsOfInterestRateApplied += monthsToPay;
        return getBalance();
    }
}
