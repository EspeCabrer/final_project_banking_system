package com.ironhack.banksystem.account.accountTypes.checking;

import com.ironhack.banksystem.account.accountTypes.checking.DTO.AddCheckingAccountReturnedDTO;
import com.ironhack.banksystem.account.accountTypes.checking.DTO.CheckingDTO;
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
public class CheckingController {

    @Autowired
    CheckingService checkingService;

    @Autowired
    AccountHolderService accountHolderService;

    @Autowired
    StudentCheckingService studentCheckingService;

    @PostMapping("accounts/new/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public AddCheckingAccountReturnedDTO addCheckingAccount(@Valid @RequestBody CheckingDTO checkingDTO) {
       AccountHolder primaryOwner = accountHolderService.getByUsername(checkingDTO.getPrimaryOwnerUserName());
       AccountHolder secondaryOwner = null;
       if(checkingDTO.getSecondaryOwnerUserName() != null) {
           secondaryOwner = accountHolderService.getByUsername(checkingDTO.getSecondaryOwnerUserName());
       }
       int primaryOwnerAge = accountHolderService.ageCalculator(primaryOwner.getDateBirth());
       if (primaryOwnerAge < 24)  return studentCheckingService.add(checkingDTO, primaryOwner, secondaryOwner);
       else return checkingService.add(checkingDTO, primaryOwner, secondaryOwner);
    }
}
