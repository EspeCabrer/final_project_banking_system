package com.ironhack.banksystem.account.accountTypes.checking;

import com.ironhack.banksystem.account.accountTypes.checking.dto.AddCheckingAccountReturnedDTO;
import com.ironhack.banksystem.account.accountTypes.checking.dto.CheckingCreateDTO;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolder;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolderRepository;
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

    public AddCheckingAccountReturnedDTO add(CheckingCreateDTO checkingDTO, AccountHolder primaryOwner, AccountHolder secondaryOwner ){
        CheckingEntity checking = checkingRepository.save(
                new CheckingEntity(
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
                checking.getId(),
                checking.getBalance(),
                checking.getPrimaryOwner().getUsername(),
                secondaryOwnerUserName,
                checking.getPENALTY_FEE(),
                checking.getCREATION_DATE());
    }
}
