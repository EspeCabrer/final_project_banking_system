package com.ironhack.banksystem.account.accountTypes.savings;

import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SavingsService {

    @Autowired
    SavingsRepository savingsRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public SavingsEntity add(SavingsDTO savingsDTO, AccountHolder primaryOwner, AccountHolder secondaryOwner){

        Money minimumBalance = null;
        if (savingsDTO.getMinimumBalance() != null){
            minimumBalance = new Money(savingsDTO.getMinimumBalance());
        }

        return savingsRepository.save(new SavingsEntity(
                new Money(savingsDTO.getBalance()),
                primaryOwner,
                secondaryOwner,
                savingsDTO.getInterestRate(),
                minimumBalance,
                passwordEncoder.encode(savingsDTO.getSecretKey()))
        );
    }
}
