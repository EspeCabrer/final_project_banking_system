package com.ironhack.demo.Account.AccountTypes.Savings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SavingsController {

    @Autowired
    SavingsService savingsService;

    @PostMapping("/accounts/new/savings")
    @ResponseStatus(HttpStatus.CREATED)
    public Savings addSavingsAccount(@RequestBody Savings savings) {

        return savingsService.add(savings);
    }


}
