package com.ironhack.banksystem.account.accountTypes.checking;

import com.ironhack.banksystem.account.AccountEntity;
import com.ironhack.banksystem.account.EnumAccountStatus;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StudentCheckingEntity extends AccountEntity {

    private String secretKey;
    @Enumerated(EnumType.STRING)
    private EnumAccountStatus status;

    public StudentCheckingEntity(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
    }
}
