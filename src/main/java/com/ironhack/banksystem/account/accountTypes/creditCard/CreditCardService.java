package com.ironhack.banksystem.account.accountTypes.creditCard;

import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CreditCardService {

    @Autowired
    CreditCardRepository creditCardRepository;

    public CreditCard crateCreditCard(CreditCardCreateDTO creditCardDTO, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        Money creditLimit = null;
        if (creditCardDTO.getCreditLimit() != null){
            creditLimit = new Money(creditCardDTO.getCreditLimit());
        }

        return new CreditCard(
                new Money(creditCardDTO.getBalance()),
                primaryOwner,
                secondaryOwner,
                creditCardDTO.getInterestRate(),
                creditLimit);
    }

    public CreditCard add(CreditCardCreateDTO creditCardDTO, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        return creditCardRepository.save(crateCreditCard(creditCardDTO, primaryOwner, secondaryOwner));
    }

    public CreditCard updateCreditCard(Long id, CreditCardCreateDTO creditCardDTO, AccountHolder primaryOwner, AccountHolder secondaryOwner) {

        CreditCard existingAccount = creditCardRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        CreditCard newAccount = crateCreditCard(creditCardDTO, primaryOwner, secondaryOwner);
        newAccount.setId(existingAccount.getId());
        return creditCardRepository.save(newAccount);
    }
}
