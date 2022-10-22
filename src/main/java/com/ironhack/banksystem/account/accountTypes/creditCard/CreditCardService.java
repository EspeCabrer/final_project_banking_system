package com.ironhack.banksystem.account.accountTypes.creditCard;

import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCardService {

    @Autowired
    CreditCardRepository creditCardRepository;

    public CreditCardEntity add(CreditCardCreateDTO creditCardDTO, AccountHolder primaryOwner, AccountHolder secondaryOwner) {

        Money creditLimit = null;
        if (creditCardDTO.getCreditLimit() != null){
            creditLimit = new Money(creditCardDTO.getCreditLimit());
        }

        return creditCardRepository.save(new CreditCardEntity(
                        new Money(creditCardDTO.getBalance()),
                        primaryOwner,
                        secondaryOwner,
                        creditCardDTO.getInterestRate(),
                        creditLimit)
        );
    }
}
