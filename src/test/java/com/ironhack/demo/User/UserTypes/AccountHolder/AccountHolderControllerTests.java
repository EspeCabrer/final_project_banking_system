package com.ironhack.demo.User.UserTypes.AccountHolder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.demo.Address.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AccountHolderControllerTests {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void post_AccountHolder_isCreated_ValidBirthDate_WorksOk() throws Exception {

        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolderDTO userInfo = new AccountHolderDTO("Miqui657", "password", "1999-01-06", address, null);

        String body = objectMapper.writeValueAsString(userInfo);

        MvcResult mvcResult = mockMvc.perform(post("/users/new/accountholder").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    void post_AccountHolder_InvalidBirthDate_ThrowsError() throws Exception {

        Address address = new Address("Roma n25", "Madrid", 06754);
        AccountHolderDTO userInfo = new AccountHolderDTO("Miqui657", "password", "01-nov-1111", address, null);

        String body = objectMapper.writeValueAsString(userInfo);

        MvcResult mvcResult = mockMvc.perform(post("/users/new/accountholder").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }
}
