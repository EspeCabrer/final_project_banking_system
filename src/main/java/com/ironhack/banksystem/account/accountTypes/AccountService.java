package com.ironhack.banksystem.account.accountTypes;

import com.ironhack.banksystem.account.Account;
import com.ironhack.banksystem.account.AccountRepository;
import com.ironhack.banksystem.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public Money getBalanceByAccountId(Long id){
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        return account.getBalance();
        //return new BalanceDTO(account.getBalance());
    }
}
