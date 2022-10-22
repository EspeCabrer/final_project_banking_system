package com.ironhack.banksystem.account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolder;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
            this.balance.setAmount(this.balance.getAmount().subtract(amount));
        } else throw new IllegalArgumentException("Insufficient funds");
    }

    public void deposit(BigDecimal amount) {
        this.balance.setAmount(this.balance.getAmount().add(amount));
    }

    public Money checkBalance(){
        return this.balance;
    }

    public String getSecretKey() {
        return "";
    }


}
