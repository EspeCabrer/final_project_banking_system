package com.ironhack.banksystem.account.accountTypes.savings;

import com.ironhack.banksystem.address.Address;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.role.EnumRole;
import com.ironhack.banksystem.role.Role;
import com.ironhack.banksystem.role.RoleRepository;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolder;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SavingsRepositoryTests {

    @Autowired
    SavingsRepository savingsRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    private RoleRepository roleRepository;

    Role role;

    @BeforeEach
    public void setUp(){
        savingsRepository.deleteAll();
        accountHolderRepository.deleteAll();
        role = roleRepository.findByName(EnumRole.ACCOUNT_HOLDER).get();
    }
    @AfterEach
    public void clean(){
        savingsRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void addSavingAccount_worksOk() {
        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user = new AccountHolder("antonia34", "password", LocalDate.parse("2000-06-02"), address, null, role );
        accountHolderRepository.save(user);
        Savings savings = savingsRepository.save(new Savings(new Money(BigDecimal.valueOf(3000)), user, null,
                null, null, "secretKey"));

        Optional<Savings> savingsOptional = savingsRepository.findById(savings.getId());

        assertTrue(savingsOptional.isPresent());
    }
}
