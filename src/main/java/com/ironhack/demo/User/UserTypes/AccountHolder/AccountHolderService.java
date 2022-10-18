package com.ironhack.demo.User.UserTypes.AccountHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.time.LocalDate;

@Service
public class AccountHolderService {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    public boolean isValidDateFormat(String date) {
        try {
            LocalDate.parse(date);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public AccountHolder add(AccountHolderDTO accountHolderDTO){

        AccountHolder user = new AccountHolder(accountHolderDTO.getUsername(), accountHolderDTO.getPassword(),LocalDate.parse(accountHolderDTO.getDateBirth()), accountHolderDTO.getPrimaryAddress(), accountHolderDTO.getMailingAddress());
        return accountHolderRepository.save(user);
    }

    public AccountHolder getByUsername(String username) {
        return accountHolderRepository.findByUsername(username).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Primary owner not found"));
    }

}
