package com.ironhack.banksystem.account.accountTypes.creditCard;

import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCardService {

    @Autowired
    CreditCardRepository creditCardRepository;

    public CreditCard add(CreditCardDTO creditCardDTO, AccountHolder primaryOwner, AccountHolder secondaryOwner) {


        return creditCardRepository.save(new CreditCard(
                        new Money(creditCardDTO.getBalance()),
                        primaryOwner,
                        secondaryOwner,
                        creditCardDTO.getPenaltyFee(),
                        creditCardDTO.getInterestRate(),
                        new Money(creditCardDTO.getCreditLimit()))
        );
    }
}
