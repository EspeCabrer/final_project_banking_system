package com.ironhack.banksystem.account;

import com.ironhack.banksystem.account.accountTypes.creditCard.CreditCard;
import com.ironhack.banksystem.account.accountTypes.creditCard.CreditCardRepository;
import com.ironhack.banksystem.address.Address;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.role.EnumRole;
import com.ironhack.banksystem.role.Role;
import com.ironhack.banksystem.role.RoleRepository;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolder;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolderRepository;
import com.ironhack.banksystem.user.UserTypes.Admin.Admin;
import com.ironhack.banksystem.user.UserTypes.Admin.AdminRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AccountServiceTests {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    RoleRepository roleRepository;

    Admin userMaria;
    AccountHolder userPepe;
    AccountHolder userAntonia;

    @BeforeEach
    public void setUp(){
        accountHolderRepository.deleteAll();
        creditCardRepository.deleteAll();
        Role accountHolderRole = roleRepository.findByName(EnumRole.ACCOUNT_HOLDER).get();
        Role adminRole = roleRepository.findByName(EnumRole.ADMIN).get();
        Address address = new Address("Roma n25", "Madrid", 06754);
        userMaria = new Admin("maria", "password", adminRole );
        userPepe = new AccountHolder("pepe", "password", LocalDate.parse("1987-06-02"), address, null, accountHolderRole );
        userAntonia = new AccountHolder("antonia", "password", LocalDate.parse("1987-06-02"), address, null, accountHolderRole );
        adminRepository.save(userMaria);
        accountHolderRepository.saveAll(List.of(userPepe, userAntonia));
    }


    @AfterEach
    public void clean(){

    }

    @Test
    void getBalanceByAccountId_HasAdminPermission_CanCheckAllBalances(){

        String userAccessName = userMaria.getUsername();
        Money balance = new Money(BigDecimal.valueOf(10500));
        CreditCard accountUserPepe = creditCardRepository.save(new CreditCard(balance, userPepe, null, null, null));

        Money moneyOtherAccount = accountService.getBalanceByAccountId(accountUserPepe.getId(), userAccessName);

        assertEquals(balance.getAmount(), moneyOtherAccount.getAmount());
    }

    @Test
    void getBalanceByAccountId_HasAccountHolderPermission_CheckOtherBalance_ThrowError(){

        String userAccessName = userAntonia.getUsername();
        Money balance = new Money(BigDecimal.valueOf(10500));
        CreditCard accountUserPepe = creditCardRepository.save(new CreditCard(balance, userPepe, null, null, null));

        assertThrows(ResponseStatusException.class,
                ()-> accountService.getBalanceByAccountId(accountUserPepe.getId(), userAccessName));
    }
}
