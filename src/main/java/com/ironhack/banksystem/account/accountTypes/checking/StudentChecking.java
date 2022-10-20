package com.ironhack.banksystem.account.accountTypes.checking;

import com.ironhack.banksystem.account.Account;
import com.ironhack.banksystem.account.EnumStatusAccount;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolder;
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
public class StudentChecking extends Account {

    private String secretKey;
    private final Date CREATION_DATE = new Date(System.currentTimeMillis());
    private EnumStatusAccount status;

    public StudentChecking(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
    }
}
