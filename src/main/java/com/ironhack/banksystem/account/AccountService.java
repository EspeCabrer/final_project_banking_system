package com.ironhack.banksystem.account;

import com.ironhack.banksystem.account.DTOs.AmountDTO;
import com.ironhack.banksystem.account.DTOs.TransferDTO;
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
            if(isUserAccount(account, user.getUsername())) return account.getBalance();
            else throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return account.getBalance();
    }

    public boolean isUserAccount(Account account, String userName) {
        String primaryOwnerUserName = account.getPrimaryOwner().getUsername();
        String secondaryOwnerUserName = "";
        if(account.getSecondaryOwner() != null) {
            secondaryOwnerUserName = account.getSecondaryOwner().getUsername();
        }

        List<String> ownersUsersNames = List.of(primaryOwnerUserName, secondaryOwnerUserName);

        return ownersUsersNames.contains(userName);
    }

    public Account updateBalanceByAccountId(Long id, AmountDTO amountDTO) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        account.getBalance().setAmount(amountDTO.getAmount());
        return accountRepository.save(account);
    }

    public Money doTransfer(String senderUserName, Long senderAccountId, TransferDTO transferDTO) {
        Account senderAccount = accountRepository.findById(senderAccountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account " + senderAccountId + " not found"));
        Account receiverAccount = accountRepository.findById(transferDTO.getReceiverAccountId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account " +transferDTO.getReceiverAccountId() + " not found"));
        if (isUserAccount(senderAccount, senderUserName)
            && hasEnoughMoney(senderAccount, transferDTO.getAmountToTransfer())
            && isUserAccount(receiverAccount, transferDTO.getReceiverOwnerUserName())) {

            BigDecimal moneyToTransfer = transferDTO.getAmountToTransfer();

            senderAccount.getBalance().setAmount(senderAccount.getBalance().getAmount().subtract(moneyToTransfer));
            receiverAccount.getBalance().setAmount(receiverAccount.getBalance().getAmount().add(moneyToTransfer));

            accountRepository.saveAll(List.of(senderAccount, receiverAccount));
        }
        else throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return senderAccount.getBalance();
    }


    public boolean hasEnoughMoney(Account account, BigDecimal amountToTransfer) {
        return account.getBalance().getAmount().compareTo(amountToTransfer) > 0;
    }

    //**TRANSFER MONEY FROM OWN ACCOUNTS TO OTHER ACCOUNT
    //Need
    //AccountId OWN ACCOUNT
    //User provide Primary or Secondary owner name and id of the account that should receive the transfer --Destinatary

    //Check if accountID belongs user.
    //Check enough founds

    // AccountId OWN ACCOUNT
    //User provide Primary or Secondary owner name and id of the account that should receive the transfer
}
