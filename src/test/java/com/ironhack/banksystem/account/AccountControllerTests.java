package com.ironhack.banksystem.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.banksystem.account.dto.AmountDTO;
import com.ironhack.banksystem.account.accountTypes.creditCard.CreditCardEntity;
import com.ironhack.banksystem.account.accountTypes.creditCard.CreditCardRepository;
import com.ironhack.banksystem.address.Address;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.role.EnumRole;
import com.ironhack.banksystem.role.RoleEntity;
import com.ironhack.banksystem.role.RoleRepository;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolder;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AccountControllerTests {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    AccountRepository accountRepository;

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
        RoleEntity role = roleRepository.findByName(EnumRole.ACCOUNT_HOLDER).get();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user = new AccountHolder("cristina", "password", LocalDate.parse("1987-06-02"), address, null, role );
        accountHolderRepository.save(user);
        creditCardRepository.save(new CreditCardEntity(new Money(BigDecimal.valueOf(1000)), user, null, null, null));
        creditCardRepository.save(new CreditCardEntity(new Money(BigDecimal.valueOf(2000)), user, null, null, null));
    }

    @Test
    public void patch_updateBalanceByAccountId_WorksOK() throws Exception {
        AmountDTO amountDTO = new AmountDTO(BigDecimal.valueOf(500));
        String body = objectMapper.writeValueAsString(amountDTO);

        List<AccountEntity> accounts = accountRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        long existentAccountId = accounts.get(0).getId();
        long nonExistentAccountId = existentAccountId + 1;


       mockMvc.perform(patch("/admin/account/balance/" + existentAccountId).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

       mockMvc.perform(patch("/admin/account/balance/"+ nonExistentAccountId).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();

       assertEquals(new BigDecimal("500.00"), accountRepository.findById(existentAccountId).get().getBalance().getAmount());
    }
}
