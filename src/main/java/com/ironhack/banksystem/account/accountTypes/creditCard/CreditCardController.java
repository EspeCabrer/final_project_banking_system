package com.ironhack.banksystem.account.accountTypes.creditCard;

import com.ironhack.banksystem.security.CustomUserDetails;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolder;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CreditCardController {

    @Autowired
    AccountHolderService accountHolderService;
    @Autowired
    CreditCardService creditCardService;

    @PostMapping("/account/new/creditcard")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard addCreditCard(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody CreditCardCreateDTO creditCardCreateDTO) {
        AccountHolder primaryOwner = accountHolderService.getByUsername(creditCardCreateDTO.getPrimaryOwnerUserName());
        AccountHolder secondaryOwner = null;
        if(creditCardCreateDTO.getSecondaryOwnerUserName() != null) {
            secondaryOwner = accountHolderService.getByUsername(creditCardCreateDTO.getSecondaryOwnerUserName());
        }

        return creditCardService.add(creditCardCreateDTO, primaryOwner, secondaryOwner);
    }

    @PutMapping("/account/update/creditcard/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CreditCard updateCreditCard(@PathVariable Long id,
                                       @RequestBody CreditCardCreateDTO creditCardCreateDTO) {

        AccountHolder primaryOwner = accountHolderService.getByUsername(creditCardCreateDTO.getPrimaryOwnerUserName());
        AccountHolder secondaryOwner = null;
        if(creditCardCreateDTO.getSecondaryOwnerUserName() != null) {
            secondaryOwner = accountHolderService.getByUsername(creditCardCreateDTO.getSecondaryOwnerUserName());
        }

        return creditCardService.updateCreditCard(id, creditCardCreateDTO, primaryOwner, secondaryOwner);
    }

}



