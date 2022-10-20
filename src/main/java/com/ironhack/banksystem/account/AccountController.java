package com.ironhack.banksystem.account;

import com.ironhack.banksystem.account.accountTypes.AccountService;
import com.ironhack.banksystem.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/account/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Money getBalanceByAccountId(@PathVariable Long id){
        return accountService.getBalanceByAccountId(id);
    }




}
