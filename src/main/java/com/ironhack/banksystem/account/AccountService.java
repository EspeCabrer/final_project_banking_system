package com.ironhack.banksystem.account;

import com.ironhack.banksystem.account.Account;
import com.ironhack.banksystem.account.AccountRepository;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.role.EnumRole;
import com.ironhack.banksystem.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;


    public Money getBalanceByAccountId(Long id, CustomUserDetails user){
        String role = String.valueOf(user.getAuthorities().toArray()[0]);
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        if (role.equals("ROLE_" + EnumRole.ACCOUNT_HOLDER)) {
            if(isOwnAccount(account, user)) return account.getBalance();
            else throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return account.getBalance();
    }

    public boolean isOwnAccount(Account account, CustomUserDetails user) {
        String primaryOwnerUserName = account.getPrimaryOwner().getUsername();
        String secondaryOwnerUserName = "";
        if(account.getSecondaryOwner() != null) {
            secondaryOwnerUserName = account.getSecondaryOwner().getUsername();
        }

        List<String> ownersUsersNames = List.of(primaryOwnerUserName, secondaryOwnerUserName);

        return ownersUsersNames.contains(user.getUsername());
    }

    public Account updateBalanceByAccountId(Long id, AmountDTO amountDTO) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        account.getBalance().setAmount(amountDTO.getAmount());
        return accountRepository.save(account);
    }
}
