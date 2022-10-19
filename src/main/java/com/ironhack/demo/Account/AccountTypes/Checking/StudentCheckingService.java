package com.ironhack.demo.Account.AccountTypes.Checking;

import com.ironhack.demo.User.UserTypes.AccountHolder.AccountHolder;
import com.ironhack.demo.User.UserTypes.AccountHolder.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentCheckingService {

    @Autowired
    StudentCheckingRepository studentCheckingRepository;

    public StudentChecking add(CheckingDTO checkingDTO, AccountHolder primaryOwner, AccountHolder secondaryOwner ){
        return studentCheckingRepository.save(
                new StudentChecking(
                        checkingDTO.getBalance(),
                        primaryOwner,
                        secondaryOwner,
                        checkingDTO.getPenaltyFee(),
                        checkingDTO.getSecretKey()
                )
        );
    }
}
