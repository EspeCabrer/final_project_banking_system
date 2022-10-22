package com.ironhack.banksystem.account.accountTypes.checking;

import com.ironhack.banksystem.address.Address;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.role.EnumRole;
import com.ironhack.banksystem.role.Role;
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
public class StudentCheckingRepositoryTests {

    @Autowired
    StudentCheckingRepository studentCheckingRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    private RoleRepository roleRepository;

    Role role;

    @BeforeEach
    void setUp() {
        studentCheckingRepository.deleteAll();
        role = roleRepository.findByName(EnumRole.ACCOUNT_HOLDER).get();
    }

    @AfterEach
    void clean() {
        studentCheckingRepository.deleteAll();
    }

    @Test
    @DisplayName("Save new student checking account - works ok")
    void addCheckingAccount() {
        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user = new AccountHolder("antonia34", "password", LocalDate.parse("2000-06-02"), address, null, role );
        accountHolderRepository.save(user);


        StudentChecking account = new StudentChecking(new Money(new BigDecimal(2000)), user, null, "secretKey");
        StudentChecking savedAccount = studentCheckingRepository.save(account);
        Optional<StudentChecking> optionalStudentChecking = studentCheckingRepository.findById(savedAccount.getId());

        assertTrue(optionalStudentChecking.isPresent());
        assertEquals("antonia34", optionalStudentChecking.get().getPrimaryOwner().getUsername() );
    }

}
