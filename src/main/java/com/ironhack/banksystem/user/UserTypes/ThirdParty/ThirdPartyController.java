package com.ironhack.banksystem.user.UserTypes.ThirdParty;

import com.ironhack.banksystem.user.UserTypes.ThirdParty.DTOs.ThirdPartyCreateDTO;
import com.ironhack.banksystem.user.UserTypes.ThirdParty.DTOs.ThirdPartyTransferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ThirdPartyController {

    @Autowired
    ThirdPartyService thirdPartyService;

    @PostMapping("thirdparty/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty addThirdParty(@Valid @RequestBody ThirdPartyCreateDTO thirdPartyCreateDTO) {
        return thirdPartyService.add(thirdPartyCreateDTO);
    }


    @PatchMapping("thirdparty/deposit")
    @ResponseStatus(HttpStatus.OK)
    public void depositWithThidParty(
            @RequestHeader String hashedKey,
            @Valid @RequestBody ThirdPartyTransferDTO thirdPartyTransferDTO) {

        thirdPartyService.doTransaction(hashedKey, thirdPartyTransferDTO);
    }
}
