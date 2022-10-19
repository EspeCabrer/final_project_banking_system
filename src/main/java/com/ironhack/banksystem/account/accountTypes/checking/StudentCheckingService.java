package com.ironhack.banksystem.account.accountTypes.checking;

import com.ironhack.banksystem.account.accountTypes.checking.DTO.AddCheckingAccountReturnedDTO;
import com.ironhack.banksystem.account.accountTypes.checking.DTO.CheckingDTO;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentCheckingService {

    @Autowired
    StudentCheckingRepository studentCheckingRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public AddCheckingAccountReturnedDTO add(CheckingDTO checkingDTO, AccountHolder primaryOwner, AccountHolder secondaryOwner ){
        StudentChecking studentChecking = studentCheckingRepository.save(
                    new StudentChecking(
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
                studentChecking.getId(),
                studentChecking.getBalance(),
                studentChecking.getPrimaryOwner().getUsername(),
                secondaryOwnerUserName,
                studentChecking.getPenaltyFee(),
                studentChecking.getCREATION_DATE());
            }
}
