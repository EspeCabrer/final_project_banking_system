package com.ironhack.demo.Account.AccountTypes.Savings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SavingsService {

    @Autowired
    SavingsRepository savingsRepository;

    public Savings add(Savings savings){
        return savingsRepository.save(savings);
    }
}
