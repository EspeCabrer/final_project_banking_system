package com.ironhack.banksystem.account.accountTypes.checking;

import com.ironhack.banksystem.address.Address;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.role.EnumRole;
import com.ironhack.banksystem.role.RoleEntity;
import com.ironhack.banksystem.role.RoleRepository;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolder;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CheckingRepositoryTests {

    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    private RoleRepository roleRepository;

    RoleEntity role;

    @BeforeEach
    void setUp() {
        checkingRepository.deleteAll();
        role = roleRepository.findByName(EnumRole.ACCOUNT_HOLDER).get();

    }

    @AfterEach
    void clean() {
        checkingRepository.deleteAll();
    }

    @Test
    @DisplayName("Save new checking account - works ok")
    void addCheckingAccount() {
        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user = new AccountHolder("pepe87", "password", LocalDate.parse("1987-06-02"), address, null, role );
        accountHolderRepository.save(user);
        CheckingEntity account = new CheckingEntity(new Money(new BigDecimal(2000)), user, null, "secretKey");

        CheckingEntity savedAccount = checkingRepository.save(account);
        Optional<CheckingEntity> optionalChecking = checkingRepository.findById(savedAccount.getId());

        assertTrue(optionalChecking.isPresent());
        assertEquals("pepe87", optionalChecking.get().getPrimaryOwner().getUsername() );
    }


}
