package com.ironhack.banksystem.account.accountTypes.savings;

import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SavingsService {

    @Autowired
    SavingsRepository savingsRepository;

    public Savings add(SavingsDTO savingsDTO, AccountHolder primaryOwner, AccountHolder secondaryOwner){

        Money minimumBalance = null;
        if (savingsDTO.getMinimumBalance() != null){
            minimumBalance = new Money(savingsDTO.getMinimumBalance());
        }

        return savingsRepository.save(new Savings(
                new Money(savingsDTO.getBalance()),
                primaryOwner,
                secondaryOwner,
                savingsDTO.getPenaltyFee(),
                savingsDTO.getInterestRate(),
                minimumBalance,
                savingsDTO.getSecretKey())
        );
    }
}
