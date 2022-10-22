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
    ThirdPartyService thirdPartyService;


    @PatchMapping("account/thirdparty/deposit")
    @ResponseStatus(HttpStatus.OK)
    public void depositWithThidParty(
            @RequestHeader String encryptedHashedKey,
            @Valid @RequestBody ThirdPartyDTO thirdPartyDTO) {

        thirdPartyService.doTransaction(encryptedHashedKey, thirdPartyDTO);
    }
}
