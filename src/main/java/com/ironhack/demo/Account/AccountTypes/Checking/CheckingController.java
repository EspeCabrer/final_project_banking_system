package com.ironhack.demo.Account.AccountTypes.Checking;

import com.ironhack.demo.User.UserTypes.AccountHolder.AccountHolder;
import com.ironhack.demo.User.UserTypes.AccountHolder.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CheckingController {

    @Autowired
    CheckingService checkingService;

    @Autowired
    AccountHolderService accountHolderService;

    @PostMapping("accounts/new/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public Checking addCheckingAccount(@Valid @RequestBody CheckingDTO checkingDTO) {
       AccountHolder primaryOwner = accountHolderService.getByUsername(checkingDTO.getUserNamePrimaryOwner());
       AccountHolder secondOwner = null;
       if(checkingDTO.getUserNameSecondaryOwner() != null) {
           secondOwner = accountHolderService.getByUsername(checkingDTO.getUserNameSecondaryOwner());
       }

       return null;
    }
}
