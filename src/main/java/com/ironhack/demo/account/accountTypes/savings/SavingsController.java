package com.ironhack.demo.account.accountTypes.savings;

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

    @PostMapping("/accounts/new/savings")
    @ResponseStatus(HttpStatus.CREATED)
    public Savings addSavingsAccount(@Valid @RequestBody SavingsDTO savingsDTO) {

        System.out.println(savingsDTO.toString());


        return null;
        //return savingsService.add(savings);
    }


}
