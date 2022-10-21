package com.ironhack.banksystem.account;

import com.ironhack.banksystem.account.DTOs.AmountDTO;
import com.ironhack.banksystem.account.DTOs.TransferDTO;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("account/balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Money getBalanceByAccountId(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long id){
        return accountService.getBalanceByAccountId(id, user);
    }

    @PatchMapping("admin/account/balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account updateBalanceByAccountId(
            @PathVariable Long id,
            @RequestBody AmountDTO amountDTO) {
        return accountService.updateBalanceByAccountId(id, amountDTO);
    }

    @PatchMapping("account/transfer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Money doTransfer(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable("id") Long senderAccountId,
            @RequestBody TransferDTO transferDTO) {

        return accountService.doTransfer(user.getUsername(), senderAccountId, transferDTO);
    }

}
