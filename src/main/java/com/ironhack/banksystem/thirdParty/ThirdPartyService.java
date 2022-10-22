package com.ironhack.banksystem.thirdParty;

import com.ironhack.banksystem.account.AccountEntity;
import com.ironhack.banksystem.account.AccountRepository;
import com.ironhack.banksystem.thirdParty.DTOs.ThirdPartyCreateDTO;
import com.ironhack.banksystem.thirdParty.DTOs.ThirdPartyTransferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ThirdPartyService {

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ThirdParty add(ThirdPartyCreateDTO dto) {
        if (thirdPartyRepository.findByName(dto.getName()).isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This third party already exists");
        return thirdPartyRepository.save(new ThirdParty(passwordEncoder.encode(dto.getName()), dto.getName()));
    }

    public void checkIfThirdPartyExistsByEncryptedHashedKey(String encryptedHashedKey) {
        if (thirdPartyRepository.findByHashedKey(encryptedHashedKey).isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Third party not found");
    }

    public AccountEntity getAccountIfValidIdAndPassword(Long accountId, String secretKey) {
        AccountEntity account = accountRepository.findById(accountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        if (!passwordEncoder.matches(secretKey, account.getSecretKey()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong account or password");
        return account;
    }

    public void doTransaction(String encryptedHashedKey, ThirdPartyTransferDTO dto) {
        checkIfThirdPartyExistsByEncryptedHashedKey(encryptedHashedKey);
        AccountEntity account = getAccountIfValidIdAndPassword(dto.getAccountId(), dto.getAccountKey());
        account.deposit(dto.getAmount());
    }
}
