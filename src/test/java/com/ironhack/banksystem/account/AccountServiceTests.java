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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
public class AccountServiceTests {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    RoleRepository roleRepository;

    Admin userMaria;
    AccountHolder userPepe;

    @BeforeEach
    public void setUp(){
        accountHolderRepository.deleteAll();
        creditCardRepository.deleteAll();
        Role role = roleRepository.findByName(EnumRole.ACCOUNT_HOLDER).get();
        Address address = new Address("Roma n25", "Madrid", 06754);
        userMaria = new AccountHolder("maria", "password", LocalDate.parse("1987-06-02"), address, null, role );
        accountHolderRepository.save(userMaria);
    }


    @AfterEach
    public void clean(){

    }

    @Test
    void getBalanceByAccountId_HasAdminPermission_CanCheckAll(){

        String userName = userMaria.getUsername();
        CreditCard accountUserMaria = creditCardRepository.save(new CreditCard(new Money(BigDecimal.valueOf(1000)), userMaria, null, null, null));

        Money moneyOwnAccount = accountService.getBalanceByAccountId(accountUserMaria.getId(), String.valueOf(EnumRole.ADMIN), userName );
        Money moneyOtherAccount = accountService.getBalanceByAccountId()
    }
}
