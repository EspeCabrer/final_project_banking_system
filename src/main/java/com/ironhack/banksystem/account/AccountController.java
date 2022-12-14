package com.ironhack.banksystem.account;

import com.ironhack.banksystem.account.dto.AmountDTO;
import com.ironhack.banksystem.account.dto.TransferDTO;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("account/balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Money getBalanceByAccountId(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long id){
        return accountService.getBalanceByAccountId(id, user.getUsername());
    }

    @PatchMapping("account/balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account updateBalanceByAccountId(
            @PathVariable Long id,
            @Valid @RequestBody AmountDTO amountDTO) {
        return accountService.updateBalanceByAccountId(id, amountDTO);
    }

    @PatchMapping("account/transfer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Money doTransfer(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable("id") Long senderAccountId,
            @Valid @RequestBody TransferDTO transferDTO) {

        return accountService.doTransfer(user.getUsername(), senderAccountId, transferDTO);
    }

    @DeleteMapping("account/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long accountId) {
        accountService.delete(accountId);
    }
}
