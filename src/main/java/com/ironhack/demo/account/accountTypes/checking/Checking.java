package com.ironhack.demo.account.accountTypes.checking;

import com.ironhack.demo.account.Account;
import com.ironhack.demo.account.EnumStatusAccount;
import com.ironhack.demo.money.Money;
import com.ironhack.demo.user.UserTypes.AccountHolder.AccountHolder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Checking extends Account {

    private String secretKey;
    private final Money MINIMUM_BALANCE = new Money(new BigDecimal(250));
    private final BigDecimal MONTHLY_MAINTENANCE_FEE = new BigDecimal(12);
    private final Date CREATION_DATE = new Date(System.currentTimeMillis());
    private EnumStatusAccount status = EnumStatusAccount.ACTIVE;

    public Checking(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal penaltyFee, String secretKey) {
        super(balance, primaryOwner, secondaryOwner, penaltyFee);
        this.secretKey = secretKey;
    }
}
