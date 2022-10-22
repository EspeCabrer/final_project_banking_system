package com.ironhack.banksystem.thirdParty;

import com.ironhack.banksystem.account.Account;
import com.ironhack.banksystem.account.accountTypes.savings.Savings;
import com.ironhack.banksystem.account.accountTypes.savings.SavingsRepository;
import com.ironhack.banksystem.account.accountTypes.savings.SavingsService;
import com.ironhack.banksystem.address.Address;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.role.EnumRole;
import com.ironhack.banksystem.role.Role;
import com.ironhack.banksystem.role.RoleRepository;
import com.ironhack.banksystem.thirdParty.DTOs.ThirdPartyCreateDTO;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolder;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class ThirdPartyServiceTests {

    @Autowired
    ThirdPartyService thirdPartyService;

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    SavingsRepository savingsRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    Role role;

    @BeforeEach
    void setUp() {
        thirdPartyRepository.deleteAll();
        savingsRepository.deleteAll();
        accountHolderRepository.deleteAll();
        role = roleRepository.findByName(EnumRole.ACCOUNT_HOLDER).get();
    }

    @Test
    void add_WorksOk(){
        ThirdPartyCreateDTO dto = new ThirdPartyCreateDTO("party5");
        thirdPartyService.add(dto);
        assertTrue(thirdPartyRepository.findByName(dto.getName()).isPresent());
    }
    @Test
    void add_existingName_ThrowsError(){
        ThirdPartyCreateDTO dto = new ThirdPartyCreateDTO("party5");
        thirdPartyService.add(dto);
        assertThrows(ResponseStatusException.class,
                ()-> thirdPartyService.add(dto));
    }

    @Test
    void getAccountIfValidIdAndPassword_WorksOk() {
        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user = new AccountHolder("antonia34", "password", LocalDate.parse("2000-06-02"), address, null, role );
        accountHolderRepository.save(user);
        Savings savings = savingsRepository.save(new Savings(new Money(BigDecimal.valueOf(3000)), user, null,
                null, null, passwordEncoder.encode("secretKey")));

        Account account = thirdPartyService.getAccountIfValidIdAndPassword(
                savings.getId(), "secretKey");

        assertEquals(savings.getId(), account.getId());
    }

    @Test
    void getAccountIfValidIdAndPassword_WrongPassword_ThrowError() {
        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user = new AccountHolder("antonia34", "password", LocalDate.parse("2000-06-02"), address, null, role );
        accountHolderRepository.save(user);
        Savings savings = savingsRepository.save(new Savings(new Money(BigDecimal.valueOf(3000)), user, null,
                null, null, "secretKey"));

        assertThrows(ResponseStatusException.class,
                ()-> thirdPartyService.getAccountIfValidIdAndPassword(savings.getId(), passwordEncoder.encode("wrongPassword")));
    }
}
