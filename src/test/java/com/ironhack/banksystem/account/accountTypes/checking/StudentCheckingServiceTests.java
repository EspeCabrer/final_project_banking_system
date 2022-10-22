package com.ironhack.banksystem.account.accountTypes.checking;

import com.ironhack.banksystem.account.accountTypes.checking.dto.AddCheckingAccountReturnedDTO;
import com.ironhack.banksystem.account.accountTypes.checking.dto.CheckingCreateDTO;
import com.ironhack.banksystem.address.Address;
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
public class StudentCheckingServiceTests {

    @Autowired
    StudentCheckingService studentCheckingService;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    StudentCheckingRepository studentCheckingRepository;

    @Autowired
    private RoleRepository roleRepository;

    RoleEntity role;

    @BeforeEach
    public void setUp(){
        studentCheckingRepository.deleteAll();
        accountHolderRepository.deleteAll();
        role = roleRepository.findByName(EnumRole.ACCOUNT_HOLDER).get();
    }

    @AfterEach
    public void clean(){
        studentCheckingRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }


    @Test
    @DisplayName("Save new student checking account - works ok")
    void add_WorksOk() {
        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user = new AccountHolder("pepe2000", "password", LocalDate.parse("2000-06-02"), address, null, role );
        accountHolderRepository.save(user);
        CheckingCreateDTO accountInfo = new CheckingCreateDTO(new BigDecimal("2000"), "pepe87", "anton763", "secretKey");

        AddCheckingAccountReturnedDTO studentCheckingSaved = studentCheckingService.add(accountInfo, user, null);

        Optional<StudentCheckingEntity> optionalChecking = studentCheckingRepository.findById(studentCheckingSaved.getId());

        assertTrue(optionalChecking.isPresent());
        assertEquals("pepe2000", optionalChecking.get().getPrimaryOwner().getUsername());
    }
}
