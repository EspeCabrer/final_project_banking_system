package com.ironhack.banksystem.account.accountTypes.checking;

import com.ironhack.banksystem.account.Account;
import com.ironhack.banksystem.account.EnumAccountStatus;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Checking extends Account {

    private String secretKey;
    @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance_amount"))
    @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency"))
    private final Money MINIMUM_BALANCE = new Money(BigDecimal.valueOf(250));
    private final BigDecimal MONTHLY_MAINTENANCE_FEE = BigDecimal.valueOf(12);
    private final Date CREATION_DATE = new Date(System.currentTimeMillis());
    @Enumerated(EnumType.STRING)
    private EnumAccountStatus status = EnumAccountStatus.ACTIVE;

    public Checking(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
    }

    @Override
    public Money checkBalance() {
        if(super.getBalance().getAmount().compareTo(MINIMUM_BALANCE.getAmount()) < 0) {
            super.withdraw(super.getPENALTY_FEE());
        }
        return super.checkBalance();
    }
}
