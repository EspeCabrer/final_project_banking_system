package com.ironhack.banksystem.account;

import com.ironhack.banksystem.account.dto.AmountDTO;
import com.ironhack.banksystem.account.dto.TransferDTO;
import com.ironhack.banksystem.account.accountTypes.checking.Checking;
import com.ironhack.banksystem.account.accountTypes.checking.CheckingRepository;
import com.ironhack.banksystem.account.accountTypes.creditCard.CreditCard;
import com.ironhack.banksystem.account.accountTypes.creditCard.CreditCardRepository;
import com.ironhack.banksystem.address.Address;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.role.EnumRole;
import com.ironhack.banksystem.role.Role;
import com.ironhack.banksystem.role.RoleRepository;
import com.ironhack.banksystem.user.UserRepository;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolder;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolderRepository;
import com.ironhack.banksystem.user.userTypes.admin.Admin;
import com.ironhack.banksystem.user.userTypes.admin.AdminRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountServiceTests {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    Admin userJose;
    AccountHolder userAlex;
    AccountHolder userAntonia;

    @BeforeEach
    public void setUp(){
        accountHolderRepository.deleteAll();
        creditCardRepository.deleteAll();
        Role accountHolderRole = roleRepository.findByName(EnumRole.ACCOUNT_HOLDER).get();
        Role adminRole = roleRepository.findByName(EnumRole.ADMIN).get();
        Address address = new Address("Roma n25", "Madrid", 06754);
        userJose = new Admin("jose", "password", adminRole );
        userAlex = new AccountHolder("alex", "password", LocalDate.parse("1987-06-02"), address, null, accountHolderRole );
        userAntonia = new AccountHolder("antonia", "password", LocalDate.parse("1987-06-02"), address, null, accountHolderRole );
        adminRepository.save(userJose);
        accountHolderRepository.saveAll(List.of(userAlex, userAntonia));
    }


    @AfterEach
    public void clean(){
        userRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void getBalanceByAccountId_AdminPermission_CanCheckAllBalances(){

        String userAccessName = userJose.getUsername();
        Money balance = new Money(BigDecimal.valueOf(10500));
        CreditCard accountUserPepe = creditCardRepository.save(new CreditCard(balance, userAlex, null, null, null));

        Money moneyOtherAccount = accountService.getBalanceByAccountId(accountUserPepe.getId(), userAccessName);

        assertEquals(balance.getAmount(), moneyOtherAccount.getAmount());
    }

    @Test
    void getBalanceByAccountId_AccountHolderPermission_CheckOtherBalance_ThrowError(){

        String userAccessName = userAntonia.getUsername();
        Money balance = new Money(BigDecimal.valueOf(10500));
        CreditCard accountUserAlex = creditCardRepository.save(new CreditCard(balance, userAlex, null, null, null));

        assertThrows(ResponseStatusException.class,
                ()-> accountService.getBalanceByAccountId(accountUserAlex.getId(), userAccessName));
    }

    @Test
    void getBalanceByAccountId_AccountHolderPermission_CheckOwnAccountBalance_WorksOk(){

        String userAccessName = userAlex.getUsername();
        Money balance = new Money(BigDecimal.valueOf(10500));
        CreditCard accountUserAlex = creditCardRepository.save(new CreditCard(balance, userAlex, null, null, null));

        Money moneyOwnAccount = accountService.getBalanceByAccountId(accountUserAlex.getId(), userAccessName);

        assertEquals(balance.getAmount(), moneyOwnAccount.getAmount());
    }

    @Test
    void getBalanceByAccountId_AccountHolderPermission_BalanceLowerThanMinimumBalance_ApplyPenaltyFee(){
        String userAccessName = userAlex.getUsername();
        //Checking MinimumBalance is 250
        Money balance = new Money(BigDecimal.valueOf(200));
        Checking accountUserAlex = checkingRepository.save(new Checking(balance, userAlex, null, "secretKey"));
        Money moneyOwnAccount = accountService.getBalanceByAccountId(accountUserAlex.getId(), userAccessName);
        //Expect 250 (initialBalance) - 40 (penalty fee) = 210
        assertEquals(new BigDecimal("160.00"), moneyOwnAccount.getAmount());
    }

    @Test
    void isUserAccount_AccountNotBelongsUser_ReturnFalse(){
        CreditCard accountUserAlex = creditCardRepository.save(new CreditCard(new Money(BigDecimal.valueOf(10500)), userAlex, null, null, null));

        assertFalse(accountService.isUserAccount(accountUserAlex, "jose"));;
    }

    @Test
    void isUserAccount_AccountBelongsUser_ReturnTrue(){
        CreditCard accountUserAlex = creditCardRepository.save(new CreditCard(new Money(BigDecimal.valueOf(10500)), userAlex, null, null, null));

        assertTrue(accountService.isUserAccount(accountUserAlex, "alex"));;
    }

    @Test
    void updateBalanceByAccountId_ValidAccountId_WorksOk(){
        Money balance = new Money(BigDecimal.valueOf(500));
        AmountDTO amountDTO = new AmountDTO(BigDecimal.valueOf(1000));
        CreditCard accountUserAlex = creditCardRepository.save(new CreditCard(balance, userAlex, null, null, null));
        Account updatedAccount = accountService.updateBalanceByAccountId(accountUserAlex.getId(), amountDTO);

        assertEquals(amountDTO.getAmount(), updatedAccount.getBalance().getAmount());
    }

    @Test
    void updateBalanceByAccountId_NonExistentAccountId_ThrowsError(){
        AmountDTO amountDTO = new AmountDTO(BigDecimal.valueOf(1000));
        List<Account> accounts = accountRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        long nonExistentId = 1;
        if (accounts.size() > 0){
            nonExistentId = accounts.get(0).getId() + 1;
        }
        long finalNonExistentId1 = nonExistentId;
        assertThrows(ResponseStatusException.class, ()-> accountService.updateBalanceByAccountId(finalNonExistentId1, amountDTO));
    }

    @Test
    void doTransfer_ValidSenderInfo_And_ValidReceiverInfo_WorksOk(){
      Money balanceAlex = new Money(BigDecimal.valueOf(2500));
      Money balanceAntonia = new Money(BigDecimal.valueOf(5000));
      CreditCard accountUserAlex = creditCardRepository.save(new CreditCard(balanceAlex, userAlex, null, null, null));
      CreditCard accountUserAntonia = creditCardRepository.save(new CreditCard(balanceAntonia, userAntonia, null, null, null));
      String senderUserName = "alex";
      String receiverUserName = "antonia";
      BigDecimal amountToTransfer = BigDecimal.valueOf(500);
      TransferDTO transferDTO = new TransferDTO(receiverUserName, accountUserAntonia.getId(), amountToTransfer);

      Money newSenderAccountBalance = accountService.doTransfer(senderUserName, accountUserAlex.getId(),transferDTO);

      assertEquals(new BigDecimal("2000.00"), accountRepository.findById(accountUserAlex.getId()).get().getBalance().getAmount());
      assertEquals(new BigDecimal("5500.00"), accountRepository.findById(accountUserAntonia.getId()).get().getBalance().getAmount());
      assertEquals(new BigDecimal("2000.00"), newSenderAccountBalance.getAmount());
    }

    @Test
    void doTransfer_SenderUserName_NotBelongs_SenderAccountId_ThrowsError(){
        Money balanceAntonia = new Money(BigDecimal.valueOf(5000));
        CreditCard accountUserAntonia = creditCardRepository.save(new CreditCard(balanceAntonia, userAntonia, null, null, null));
        String senderUserName = "alex";
        String receiverUserName = "antonia";
        BigDecimal amountToTransfer = BigDecimal.valueOf(500);
        TransferDTO transferDTO = new TransferDTO(receiverUserName, accountUserAntonia.getId(), amountToTransfer);

        assertThrows(ResponseStatusException.class,
                ()-> accountService.doTransfer(senderUserName, accountUserAntonia.getId(), transferDTO));
    }

    @Test
    void doTransfer_ReceiverUserName_NotBelongs_ReceiverAccountId_ThrowsError(){
        Money balanceAlex = new Money(BigDecimal.valueOf(2500));
        CreditCard accountUserAlex = creditCardRepository.save(new CreditCard(balanceAlex, userAlex, null, null, null));
        BigDecimal amountToTransfer = BigDecimal.valueOf(500);
        TransferDTO transferDTO = new TransferDTO("antonia", accountUserAlex.getId(), amountToTransfer);

        assertThrows(ResponseStatusException.class,
                ()-> accountService.doTransfer("alex", accountUserAlex.getId(),transferDTO));
    }

    @Test
    void doTransfer_InsufficientFunds_ThrowsError(){
        Money balanceAlex = new Money(BigDecimal.valueOf(2500));
        Money balanceAntonia = new Money(BigDecimal.valueOf(5000));
        CreditCard accountUserAlex = creditCardRepository.save(new CreditCard(balanceAlex, userAlex, null, null, null));
        CreditCard accountUserAntonia = creditCardRepository.save(new CreditCard(balanceAntonia, userAntonia, null, null, null));
        String senderUserName = "alex";
        String receiverUserName = "antonia";
        BigDecimal amountToTransfer = BigDecimal.valueOf(5000);
        TransferDTO transferDTO = new TransferDTO(receiverUserName, accountUserAntonia.getId(), amountToTransfer);

        assertThrows(ResponseStatusException.class,
                ()-> accountService.doTransfer(senderUserName, accountUserAlex.getId(),transferDTO));
    }

    @Test
    void doTransfer_BalanceLowerThanMinimumBalance_ApplyPenaltyFee(){
        Money balanceAlex = new Money(BigDecimal.valueOf(200));
        Money balanceAntonia = new Money(BigDecimal.valueOf(10));
        //Checking MinimumBalance is 250
        Checking accountUserAlex = checkingRepository.save(new Checking(balanceAlex, userAlex, null, "secretKey"));
        CreditCard accountUserAntonia = creditCardRepository.save(new CreditCard(balanceAntonia, userAntonia, null, null, null));
        String senderUserName = "alex";
        String receiverUserName = "antonia";
        BigDecimal amountToTransfer = BigDecimal.valueOf(10);
        TransferDTO transferDTO = new TransferDTO(receiverUserName, accountUserAntonia.getId(), amountToTransfer);

        Money newSenderAccountBalance = accountService.doTransfer(senderUserName, accountUserAlex.getId(),transferDTO);
        //Expected --> 200 (initialBalance) - 10 (amountToTransfer) - 40 (PenaltyFee) = 150
        assertEquals(new BigDecimal("150.00"), accountRepository.findById(accountUserAlex.getId()).get().getBalance().getAmount());
        assertEquals(new BigDecimal("150.00"), newSenderAccountBalance.getAmount());
    }
}
