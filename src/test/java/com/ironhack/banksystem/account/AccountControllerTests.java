package com.ironhack.banksystem.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.banksystem.account.accountTypes.creditCard.CreditCard;
import com.ironhack.banksystem.account.accountTypes.creditCard.CreditCardDTO;
import com.ironhack.banksystem.account.accountTypes.creditCard.CreditCardRepository;
import com.ironhack.banksystem.address.Address;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.role.EnumRole;
import com.ironhack.banksystem.role.Role;
import com.ironhack.banksystem.role.RoleRepository;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolder;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AccountControllerTests {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        accountHolderRepository.deleteAll();
        creditCardRepository.deleteAll();
        Role role = roleRepository.findByName(EnumRole.ACCOUNT_HOLDER).get();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user = new AccountHolder("maria", "password", LocalDate.parse("1987-06-02"), address, null, role );
        accountHolderRepository.save(user);
        creditCardRepository.save(new CreditCard(new Money(BigDecimal.valueOf(3000)), user, null, null, null));
    }

    /*@Test
    public void getBalanceByAccountId_WorksOK() throws Exception {

        mockMvc.perform(get("/account/1"))
                .andExpect(status().isOk()).andReturn();

        mockMvc.perform(get("/account/2"))
                .andExpect(status().isNotFound()).andReturn();
    }*/
}
