package com.ironhack.demo.Account.AccountTypes.Savings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.demo.Address.Address;
import com.ironhack.demo.Money.Money;
import com.ironhack.demo.User.UserTypes.AccountHolder.AccountHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
public class SavingsControllerTests {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void post_SavingsAccount_isCreated_WorksOk() throws Exception {

        AccountHolder holder = new AccountHolder("Antoni", "password", LocalDate.of(1889,03,23), new Address("roma", "Madrid", 0332),null);

        Savings savingsAccount1 = new Savings(new Money(new BigDecimal(30000)), holder, null, new BigDecimal("0.3"), "secretkey");
        String body = objectMapper.writeValueAsString(savingsAccount1);

      /*  MvcResult mvcResult = mockMvc.perform(post("/accounts/new-account/savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();*/





     /*   DepartmentDTO product = new DepartmentDTO("department1");
        String body = objectMapper.writeValueAsString(product);

        MvcResult mvcResult = mockMvc.perform(post("/department/add").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();*/


    }




}
