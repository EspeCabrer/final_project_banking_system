package com.ironhack.banksystem.user.UserTypes.ThirdParty;

import com.ironhack.banksystem.account.DTOs.TransferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ThirdPartyController {

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PatchMapping("account/thirdparty/deposit")
    @ResponseStatus(HttpStatus.OK)
    public void depositWithThidParty(
            @RequestHeader String hashedKey,
            @Valid @RequestBody ThirdPartyDTO thirdPartyDTO) {

        System.err.println(hashedKey);


        System.err.println(thirdPartyRepository.findByHashedKey(hashedKey).isPresent());
        System.err.println(passwordEncoder.matches("hashedKey", hashedKey));
        //passwordEncoder.matches(rawEnteredPassword, storedEncryptedPassword)
//$2a$10$d8pirQ/C/1UOlb2CeWbNWezQ.1WwcFtOHstgAtsIBYGWok6oaHjKm

        //ThirdParty thirdParty = thirdPartyRepository.findById(thirdPartyDTO.)


        //return accountService.doTransfer(user.getUsername(), senderAccountId, transferDTO);
    }


}
