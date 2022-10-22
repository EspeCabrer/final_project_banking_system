package com.ironhack.banksystem.user.UserTypes.AccountHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class AccountHolderController {

    @Autowired
    AccountHolderService accountHolderService;

    @PostMapping("users/new/accountholder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder addAccountHolderUser(@Valid @RequestBody AccountHolderDTO accountHolderDTO) {
        if(accountHolderService.isValidDateFormat(accountHolderDTO.getDateBirth())) return accountHolderService.add(accountHolderDTO);
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid format for birth date. It must be 'yyyy-MM-dd'");
    }
}
