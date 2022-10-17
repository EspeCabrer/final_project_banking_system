package com.ironhack.demo.Account;

import com.ironhack.demo.Money.Money;
import com.ironhack.demo.User.AccountHolder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Optional;

@Entity
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Money balance;

    private AccountHolder primaryOwner;

    private Optional<AccountHolder> secondaryOwner;

    private BigDecimal penaltyFee;






}
