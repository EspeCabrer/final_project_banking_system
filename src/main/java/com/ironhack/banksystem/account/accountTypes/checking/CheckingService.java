package com.ironhack.banksystem.account.accountTypes.checking;

import com.ironhack.banksystem.account.accountTypes.checking.DTO.AddCheckingAccountReturnedDTO;
import com.ironhack.banksystem.account.accountTypes.checking.DTO.CheckingDTO;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolder;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolderRepository;
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

    public AddCheckingAccountReturnedDTO add(CheckingDTO checkingDTO, AccountHolder primaryOwner, AccountHolder secondaryOwner ){
        Checking checking = checkingRepository.save(
                new Checking(
                        checkingDTO.getBalance(),
                        primaryOwner,
                        secondaryOwner,
                        checkingDTO.getPenaltyFee(),
                        passwordEncoder.encode(checkingDTO.getSecretKey())
                )
        );

        String secondaryOwnerUserName = null;
        if (secondaryOwner != null) {
            secondaryOwnerUserName = secondaryOwner.getUsername();
        }

        return new AddCheckingAccountReturnedDTO(
                checking.getId(),
                checking.getBalance(),
                checking.getPrimaryOwner().getUsername(),
                secondaryOwnerUserName,
                checking.getPenaltyFee(),
                checking.getCREATION_DATE());
    }
}
