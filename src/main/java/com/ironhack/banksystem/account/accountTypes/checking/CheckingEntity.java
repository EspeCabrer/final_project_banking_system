package com.ironhack.banksystem.account.accountTypes.checking;

import com.ironhack.banksystem.account.AccountEntity;
import com.ironhack.banksystem.account.EnumAccountStatus;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CheckingEntity extends AccountEntity {

    private String secretKey;
    @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance_amount"))
    @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency"))
    private final Money MINIMUM_BALANCE = new Money(BigDecimal.valueOf(250));
    private final BigDecimal MONTHLY_MAINTENANCE_FEE = BigDecimal.valueOf(12);
    @Enumerated(EnumType.STRING)
    private EnumAccountStatus status = EnumAccountStatus.ACTIVE;

    public CheckingEntity(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey) {
        super(balance, primaryOwner, secondaryOwner);
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
    public Money checkBalance(){
        checkPenaltyFee();
        return this.getBalance();
    }

    private void checkPenaltyFee() {
        if(super.getBalance().getAmount().compareTo(MINIMUM_BALANCE.getAmount()) < 0) {
            super.withdraw(super.getPENALTY_FEE());
        }
    }
}
