package com.ironhack.demo.account.accountTypes.savings;

import com.ironhack.demo.money.Money;
import com.ironhack.demo.user.UserTypes.AccountHolder.AccountHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SavingsService {

    @Autowired
    SavingsRepository savingsRepository;

    public Savings add(SavingsDTO savingsDTO, AccountHolder primaryOwner, AccountHolder secondaryOwner){

        Money minimumBalance;
        if (savingsDTO.getMinimumBalance() == null){
            minimumBalance = null;
        } else minimumBalance = new Money(savingsDTO.getMinimumBalance());

        Savings savings = new Savings(
                new Money(savingsDTO.getBalance()),
                primaryOwner,
                secondaryOwner,
                savingsDTO.getPenaltyFee(),
                savingsDTO.getInterestRate(),
                minimumBalance,
                savingsDTO.getSecretKey());

        System.out.println(savings.toString());





        //savingsRepository.save(savings)
        return null;
    }
}
