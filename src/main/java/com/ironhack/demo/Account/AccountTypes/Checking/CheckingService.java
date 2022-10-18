package com.ironhack.demo.Account.AccountTypes.Checking;

import com.ironhack.demo.User.UserTypes.AccountHolder.AccountHolder;
import com.ironhack.demo.User.UserTypes.AccountHolder.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CheckingService {

    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    public Checking add(CheckingDTO checkingDTO ){


        return null;
    }


}
