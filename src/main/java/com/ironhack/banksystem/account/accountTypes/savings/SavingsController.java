package com.ironhack.banksystem.account.accountTypes.savings;

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
public class SavingsController {

    @Autowired
    SavingsService savingsService;

    @Autowired
    AccountHolderService accountHolderService;

    @PostMapping("/accounts/new/savings")
    @ResponseStatus(HttpStatus.CREATED)
    public SavingsEntity addSavingsAccount(@Valid @RequestBody SavingsDTO savingsDTO) {
        AccountHolder primaryOwner = accountHolderService.getByUsername(savingsDTO.getPrimaryOwnerUserName());
        AccountHolder secondaryOwner = null;
        if(savingsDTO.getSecondaryOwnerUserName() != null) {
            secondaryOwner = accountHolderService.getByUsername(savingsDTO.getSecondaryOwnerUserName());
        }

        return savingsService.add(savingsDTO, primaryOwner, secondaryOwner);
    }

}
