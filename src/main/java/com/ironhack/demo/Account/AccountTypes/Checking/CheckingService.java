package com.ironhack.demo.Account.AccountTypes.Checking;

import com.ironhack.demo.User.UserTypes.AccountHolder.AccountHolder;
import com.ironhack.demo.User.UserTypes.AccountHolder.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CheckingService {

    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Checking add(CheckingDTO checkingDTO, AccountHolder primaryOwner, AccountHolder secondaryOwner ){
        return checkingRepository.save(new Checking(checkingDTO.getBalance(), primaryOwner, secondaryOwner, checkingDTO.getPenaltyFee(), passwordEncoder.encode(checkingDTO.getSecretKey())));
    }

}
