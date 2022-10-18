package com.ironhack.demo.Account.AccountTypes.Checking;

import com.ironhack.demo.Account.Account;
import com.ironhack.demo.Account.EnumStatusAccount;
import com.ironhack.demo.Money.Money;
import com.ironhack.demo.User.UserTypes.AccountHolder.AccountHolder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StudentChecking extends Account {

    private String secretKey;
    private final Date CREATION_DATE = new Date(System.currentTimeMillis());
    private EnumStatusAccount status;

    public StudentChecking(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal penaltyFee, String secretKey) {
        super(balance, primaryOwner, secondaryOwner, penaltyFee);
        this.secretKey = secretKey;
    }
}
