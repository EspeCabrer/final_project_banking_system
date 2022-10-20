package com.ironhack.banksystem.account.accountTypes.creditCard;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.banksystem.account.accountTypes.savings.SavingsDTO;
import com.ironhack.banksystem.address.Address;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolder;
import com.ironhack.banksystem.user.UserTypes.AccountHolder.AccountHolderRepository;
import org.junit.jupiter.api.AfterEach;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CreditCardControllerTests {
    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;


    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        accountHolderRepository.deleteAll();
        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolder user1 = new AccountHolder("maria", "password", LocalDate.parse("1987-06-02"), address, null );
        AccountHolder user2 = new AccountHolder("pepe", "password", LocalDate.parse("1987-06-02"), address, null );

        accountHolderRepository.saveAll(List.of(user1, user2));
    }

    @AfterEach
    public void clean(){
        accountHolderRepository.deleteAll();
    }

    //REQUIRED
    //Balance --
    //Primary Owner --
    //penaltyFee --

    //OPTIONAL
    //secundaryOwner --
    //creditLimit --
    //interestRate --

    @Test
    void post_CreditCardAccount_NullBalance_ThrowsError() throws Exception {
        BigDecimal balance = null;
        CreditCardDTO creditCardDTO = new CreditCardDTO(balance, "maria", "maria", new Money(BigDecimal.valueOf(300)), BigDecimal.valueOf(0.2), BigDecimal.valueOf(20));
        String body = objectMapper.writeValueAsString(creditCardDTO);

        mockMvc.perform(post("/accounts/new/creditcard").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void post_CreditCardAccount_NullPrimaryOwner_ThrowsError() throws Exception {

        String primaryOwnerUserName = null;
        CreditCardDTO creditCardDTO = new CreditCardDTO(BigDecimal.valueOf(2000), primaryOwnerUserName, "maria", new Money(BigDecimal.valueOf(300)), BigDecimal.valueOf(0.2), BigDecimal.valueOf(20));
        String body = objectMapper.writeValueAsString(creditCardDTO);

        mockMvc.perform(post("/accounts/new/creditcard").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void post_CreditCardAccount_nullSecondaryOwner_WorksOk() throws Exception {

        String secondaryOwnerUserName = null;
        CreditCardDTO creditCardDTO = new CreditCardDTO(BigDecimal.valueOf(2000), "maria", secondaryOwnerUserName, new Money(BigDecimal.valueOf(300)), BigDecimal.valueOf(0.2), BigDecimal.valueOf(20));
        String body = objectMapper.writeValueAsString(creditCardDTO);

        mockMvc.perform(post("/accounts/new/creditcard").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
    }


    @Test
    void post_CreditCardAccount_nullPenaltyFee_ThrowsError() throws Exception {

        BigDecimal penaltyFee = null;
        CreditCardDTO creditCardDTO = new CreditCardDTO(BigDecimal.valueOf(2000), "maria", null, new Money(BigDecimal.valueOf(300)), BigDecimal.valueOf(0.2), penaltyFee);
        String body = objectMapper.writeValueAsString(creditCardDTO);

        mockMvc.perform(post("/accounts/new/creditcard").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void post_CreditCardAccount_nullCreditLimit_WorksOk() throws Exception {

        Money creditLimit = null;
        CreditCardDTO creditCardDTO = new CreditCardDTO(BigDecimal.valueOf(2000), "maria", null, creditLimit, BigDecimal.valueOf(0.2), BigDecimal.valueOf(20));
        String body = objectMapper.writeValueAsString(creditCardDTO);

        mockMvc.perform(post("/accounts/new/creditcard").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    void post_CreditCardAccount_nullInterestRate_WorksOk() throws Exception {

        BigDecimal interestRate = null;
        CreditCardDTO creditCardDTO = new CreditCardDTO(BigDecimal.valueOf(2000), "maria", null, new Money(BigDecimal.valueOf(300)), interestRate, BigDecimal.valueOf(20));
        String body = objectMapper.writeValueAsString(creditCardDTO);

        mockMvc.perform(post("/accounts/new/creditcard").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
    }
}
