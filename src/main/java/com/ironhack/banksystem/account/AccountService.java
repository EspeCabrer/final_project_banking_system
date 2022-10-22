package com.ironhack.banksystem.account;

import com.ironhack.banksystem.account.dto.AmountDTO;
import com.ironhack.banksystem.account.dto.TransferDTO;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.role.EnumRole;
import com.ironhack.banksystem.user.UserEntity;
import com.ironhack.banksystem.user.UserRepository;
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

    @Autowired
    UserRepository userRepository;

    public boolean isUserAccount(AccountEntity account, String userName) {

        String primaryOwnerUserName = account.getPrimaryOwner().getUsername();
        String secondaryOwnerUserName = "";
        if(account.getSecondaryOwner() != null) {
            secondaryOwnerUserName = account.getSecondaryOwner().getUsername();
        }

        List<String> ownersUsersNames = List.of(primaryOwnerUserName, secondaryOwnerUserName);

        return ownersUsersNames.contains(userName);
    }


    /*public boolean hasEnoughMoney(Account account, BigDecimal amountToTransfer) {
        if(account.getBalance().getAmount().compareTo(amountToTransfer) > 0) return true;
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds");
    }*/


    public Money getBalanceByAccountId(Long id, String userName){
        AccountEntity account = accountRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        UserEntity user = userRepository.findByUsername(userName).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        EnumRole roleName = user.getRole().getName();

        if (roleName == EnumRole.ACCOUNT_HOLDER) {
            if(isUserAccount(account, userName)) return account.checkBalance();
            else throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return account.getBalance();

    }

    public AccountEntity updateBalanceByAccountId(Long id, AmountDTO amountDTO) {
        AccountEntity account = accountRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        account.getBalance().setAmount(amountDTO.getAmount());
        return accountRepository.save(account);
    }

    public Money doTransfer(String senderUserName, Long senderAccountId, TransferDTO transferDTO) {
        AccountEntity senderAccount = accountRepository.findById(senderAccountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account " + senderAccountId + " not found"));
        AccountEntity receiverAccount = accountRepository.findById(transferDTO.getReceiverAccountId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account " +transferDTO.getReceiverAccountId() + " not found"));
        if (isUserAccount(senderAccount, senderUserName) && isUserAccount(receiverAccount, transferDTO.getReceiverOwnerUserName())) {
            BigDecimal moneyToTransfer = transferDTO.getAmountToTransfer();
           try {
               senderAccount.withdraw(moneyToTransfer);
               receiverAccount.deposit(moneyToTransfer);
               accountRepository.saveAll(List.of(senderAccount, receiverAccount));
           } catch (IllegalArgumentException e) {
               throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds");
           }
        }
        else throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return senderAccount.getBalance();
    }

}
