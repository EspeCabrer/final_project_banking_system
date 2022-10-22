package com.ironhack.banksystem.user.userTypes.accountHolder;

import com.ironhack.banksystem.role.EnumRole;
import com.ironhack.banksystem.role.RoleEntity;
import com.ironhack.banksystem.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;

@Service
public class AccountHolderService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean isValidDateFormat(String date) {
        try {
            LocalDate.parse(date);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public AccountHolder add(AccountHolderDTO accountHolderDTO){

        if(accountHolderRepository.findByUsername(accountHolderDTO.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This user already exists");
        }

        RoleEntity role = roleRepository.findByName(EnumRole.ACCOUNT_HOLDER).get();

        AccountHolder user = new AccountHolder(accountHolderDTO.getUsername(), passwordEncoder.encode(accountHolderDTO.getPassword()),LocalDate.parse(accountHolderDTO.getDateBirth()), accountHolderDTO.getPrimaryAddress(), accountHolderDTO.getMailingAddress(), role);
        return accountHolderRepository.save(user);
    }

    public AccountHolder getByUsername(String username) {
        return accountHolderRepository.findByUsername(username).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Primary owner not found"));
    }

    public int ageCalculator(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

}
