package com.ironhack.demo.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.demo.Money.Money;
import com.ironhack.demo.User.UserTypes.AccountHolder;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

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

    @NotNull
    @ManyToOne
    @JoinColumn(name="primary_owner_id")
    @JsonIgnore
    private AccountHolder primaryOwner;

    @ManyToOne
    @JoinColumn(name="secundary_owner_id")
    @JsonIgnore
    private AccountHolder secondaryOwner;

    private BigDecimal penaltyFee;

    public Account(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal penaltyFee) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.penaltyFee = penaltyFee;
    }
}
