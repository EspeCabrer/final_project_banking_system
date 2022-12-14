package com.ironhack.banksystem.account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Money balance;

    @ManyToOne
    @JoinColumn(name="primary_owner_id")
    @JsonIgnore
    private AccountHolder primaryOwner;

    @ManyToOne
    @JoinColumn(name="secundary_owner_id")
    @JsonIgnore
    private AccountHolder secondaryOwner;

    private final BigDecimal PENALTY_FEE = BigDecimal.valueOf(40);

    private final Date CREATION_DATE = new Date(System.currentTimeMillis());

    public Account(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
    }


    public void withdraw(BigDecimal amount) {
        if(this.getBalance().getAmount().compareTo(amount) > 0) {
            this.balance.decreaseAmount(amount);
        } else throw new IllegalArgumentException("Insufficient funds");
    }

    public void deposit(BigDecimal amount) {
        this.balance.increaseAmount(amount);

    }

    public Money checkBalance(){
        return this.balance;
    }

    public String getSecretKey() {
        return "";
    }

}
