package com.ironhack.banksystem.user.userTypes.accountHolder;

import com.ironhack.banksystem.address.Address;
import com.ironhack.banksystem.role.EnumRole;
import com.ironhack.banksystem.role.Role;
import com.ironhack.banksystem.role.RoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AccountHolderRepositoryTests {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    private RoleRepository roleRepository;
    Role role;

    @BeforeEach
    void setUp() {
        accountHolderRepository.deleteAll();
        role = roleRepository.findByName(EnumRole.ACCOUNT_HOLDER).get();
    }

    @AfterEach
    void clean() {
        accountHolderRepository.deleteAll();
    }

    @Test
    @DisplayName("Save new account holder user - works ok")
    void addAccountHolder() {
        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user = new AccountHolder("pepe87", "password", LocalDate.parse("1987-06-02"), address, null, role );

        AccountHolder savedUser = accountHolderRepository.save(user);
        Optional<AccountHolder> optionalAccountHolder = accountHolderRepository.findById(savedUser.getId());

        assertTrue(optionalAccountHolder.isPresent());
        assertEquals("pepe87", optionalAccountHolder.get().getUsername() );
    }

    @Test
    @DisplayName("Find by userName - works ok")
    void findByUsername_WorksOk() {
        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user = new AccountHolder("pepe87", "password", LocalDate.parse("1987-06-02"), address, null, role );

        accountHolderRepository.save(user);
        Optional<AccountHolder> optionalAccountHolder = accountHolderRepository.findByUsername("pepe87");

        assertTrue(optionalAccountHolder.isPresent());
        assertEquals("pepe87", optionalAccountHolder.get().getUsername() );
    }
}
