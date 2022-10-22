package com.ironhack.banksystem.user.UserTypes.ThirdParty;

import com.ironhack.banksystem.account.Account;
import com.ironhack.banksystem.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

public class ThirdPartyService {

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void checkIfThirdPartyExistsByEncryptedHashedKey(String encryptedHashedKey) {
        if (thirdPartyRepository.findByHashedKey(encryptedHashedKey).isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Third party not found");
    }

    public Account getAccountIfValidIdAndPassword(Long accountId, String secretKey) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        if (!passwordEncoder.matches(secretKey, account.getSecretKey()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong account or password");
        return account;
    }

    public void doTransaction(String encryptedHashedKey, ThirdPartyDTO thirdPartyDTO) {
        checkIfThirdPartyExistsByEncryptedHashedKey(encryptedHashedKey);
        Account account = getAccountIfValidIdAndPassword(thirdPartyDTO.getAccountId(), thirdPartyDTO.getAccountKey());
        account.deposit(thirdPartyDTO.getAmount());
    }
}
