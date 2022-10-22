package com.ironhack.banksystem.account.accountTypes.creditCard;

import com.ironhack.banksystem.address.Address;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.role.EnumRole;
import com.ironhack.banksystem.role.RoleEntity;
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
public class CreditCardRepositoryTests {

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    private RoleRepository roleRepository;

    RoleEntity role;

    @BeforeEach
    public void setUp(){
        creditCardRepository.deleteAll();
        accountHolderRepository.deleteAll();
        role = roleRepository.findByName(EnumRole.ACCOUNT_HOLDER).get();
    }
    @AfterEach
    public void clean(){
        creditCardRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void addCreditcardAccount_worksOk() {
        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user = new AccountHolder("antonia34", "password", LocalDate.parse("2000-06-02"), address, null, role );
        accountHolderRepository.save(user);
        CreditCardEntity creditCard = creditCardRepository.save(new CreditCardEntity(new Money(BigDecimal.valueOf(3000)), user, null,
                null, null));

        Optional<CreditCardEntity> creditCardOptional = creditCardRepository.findById(creditCard.getId());

        assertTrue(creditCardOptional.isPresent());
    }
}
