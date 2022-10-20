package com.ironhack.banksystem.account.accountTypes.creditCard;

import com.ironhack.banksystem.account.accountTypes.savings.Savings;
import com.ironhack.banksystem.account.accountTypes.savings.SavingsDTO;
import com.ironhack.banksystem.account.accountTypes.savings.SavingsService;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolder;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolderService;
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

    @PostMapping("/accounts/new/creditcard")
    @ResponseStatus(HttpStatus.CREATED)
    public Savings addCreditCard(@Valid @RequestBody CreditCardDTO creditCardDTO) {
        AccountHolder primaryOwner = accountHolderService.getByUsername(creditCardDTO.getPrimaryOwnerUserName());
        AccountHolder secondaryOwner = null;
        if(creditCardDTO.getSecondaryOwnerUserName() != null) {
            secondaryOwner = accountHolderService.getByUsername(creditCardDTO.getSecondaryOwnerUserName());
        }
        return null;

        //return savingsService.add(savingsDTO, primaryOwner, secondaryOwner);
    }

}



