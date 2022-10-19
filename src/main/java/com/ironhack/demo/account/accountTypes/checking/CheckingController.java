package com.ironhack.demo.account.accountTypes.checking;

import com.ironhack.demo.user.UserTypes.AccountHolder.AccountHolder;
import com.ironhack.demo.user.UserTypes.AccountHolder.AccountHolderService;
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

    @Autowired
    StudentCheckingService studentCheckingService;

    @PostMapping("accounts/new/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCheckingAccount(@Valid @RequestBody CheckingDTO checkingDTO) {
       AccountHolder primaryOwner = accountHolderService.getByUsername(checkingDTO.getUserNamePrimaryOwner());
       AccountHolder secondaryOwner = null;
       if(checkingDTO.getUserNameSecondaryOwner() != null) {
           secondaryOwner = accountHolderService.getByUsername(checkingDTO.getUserNameSecondaryOwner());
       }
       int primaryOwnerAge = accountHolderService.ageCalculator(primaryOwner.getDateBirth());
       if (primaryOwnerAge < 24) studentCheckingService.add(checkingDTO, primaryOwner, secondaryOwner);
       else checkingService.add(checkingDTO, primaryOwner, secondaryOwner);
    }
}
