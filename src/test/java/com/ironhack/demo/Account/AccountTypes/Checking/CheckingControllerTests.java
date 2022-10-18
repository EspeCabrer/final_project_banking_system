package com.ironhack.demo.Account.AccountTypes.Checking;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.demo.Address.Address;
import com.ironhack.demo.Money.Money;
import com.ironhack.demo.User.UserTypes.AccountHolder.AccountHolder;
import com.ironhack.demo.User.UserTypes.AccountHolder.AccountHolderDTO;
import com.ironhack.demo.User.UserTypes.AccountHolder.AccountHolderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CheckingControllerTests {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    Address address;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        address = new Address("Roma n25", "Madrid", 06754);
    }

    @Test
    void post_CheckingAccount_invalidPrimaryOwnerUserName_ThrowsError() throws Exception {
        CheckingDTO checkingDTO = new CheckingDTO(new Money(new BigDecimal("2000")), "anto76", null, new BigDecimal(0.3), "secretKey");
        String body = objectMapper.writeValueAsString(checkingDTO);

        MvcResult mvcResult = mockMvc.perform(post("/accounts/new/checking").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void post_CheckingAccount_invalidSecondOwnerUserName_ThrowsError() throws Exception {
        AccountHolder user = new AccountHolder("pepe87", "password", LocalDate.parse("1987-06-02"), address, null );
        accountHolderRepository.save(user);
        CheckingDTO checkingDTO = new CheckingDTO(new Money(new BigDecimal("2000")), "pepe87", "anton763", new BigDecimal(0.3), "secretKey");
        String body = objectMapper.writeValueAsString(checkingDTO);

        MvcResult mvcResult = mockMvc.perform(post("/accounts/new/checking").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void post_CheckingAccount_nullSecondOwnerUserName_WorksOk() throws Exception {
        AccountHolder user = new AccountHolder("pepe87", "password", LocalDate.parse("1987-06-02"), address, null );
        accountHolderRepository.save(user);
        CheckingDTO checkingDTO = new CheckingDTO(new Money(new BigDecimal("2000")), "pepe87", null, new BigDecimal(0.3), "secretKey");
        String body = objectMapper.writeValueAsString(checkingDTO);

        MvcResult mvcResult = mockMvc.perform(post("/accounts/new/checking").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
    }
}
