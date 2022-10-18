package com.ironhack.demo.Account.AccountTypes.Savings;

import com.ironhack.demo.Money.Money;
import com.ironhack.demo.User.UserTypes.AccountHolder.AccountHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class SavingsDTO {

    private Money balance;
    private AccountHolder primaryOwner;
    private AccountHolder secondaryOwner;
    private BigDecimal penaltyFee;
    private BigDecimal interestRate;
    private Money minimumBalance;
    private String secretKey;
}
