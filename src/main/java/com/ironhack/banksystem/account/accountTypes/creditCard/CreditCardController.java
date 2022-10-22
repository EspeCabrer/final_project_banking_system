package com.ironhack.banksystem.account.accountTypes.creditCard;

import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolder;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CreditCardController {


    @Autowired
    AccountHolderService accountHolderService;
    @Autowired
    CreditCardService creditCardService;

    @PostMapping("/accounts/new/creditcard")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCardEntity addCreditCard(@Valid @RequestBody CreditCardCreateDTO creditCardDTO) {
        AccountHolder primaryOwner = accountHolderService.getByUsername(creditCardDTO.getPrimaryOwnerUserName());
        AccountHolder secondaryOwner = null;
        if(creditCardDTO.getSecondaryOwnerUserName() != null) {
            secondaryOwner = accountHolderService.getByUsername(creditCardDTO.getSecondaryOwnerUserName());
        }

        return creditCardService.add(creditCardDTO, primaryOwner, secondaryOwner);
    }
}



