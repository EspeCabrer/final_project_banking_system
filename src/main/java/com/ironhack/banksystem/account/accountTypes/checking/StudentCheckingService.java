package com.ironhack.banksystem.account.accountTypes.checking;

import com.ironhack.banksystem.account.accountTypes.checking.dto.AddCheckingAccountReturnedDTO;
import com.ironhack.banksystem.account.accountTypes.checking.dto.CheckingCreateDTO;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentCheckingService {

    @Autowired
    StudentCheckingRepository studentCheckingRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public AddCheckingAccountReturnedDTO add(CheckingCreateDTO checkingDTO, AccountHolder primaryOwner, AccountHolder secondaryOwner ){
        StudentCheckingEntity studentChecking = studentCheckingRepository.save(
                    new StudentCheckingEntity(
                            new Money(checkingDTO.getBalance()),
                            primaryOwner,
                            secondaryOwner,
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
                studentChecking.getPENALTY_FEE(),
                studentChecking.getCREATION_DATE());
            }
}
