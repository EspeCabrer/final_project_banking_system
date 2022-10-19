package com.ironhack.demo.user.UserTypes.AccountHolder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.demo.address.Address;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AccountHolderControllerTests {

    @Autowired
    AccountHolderRepository accountHolderRepository;

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
    void post_AccountHolder_isCreated_WorksOk() throws Exception {

        AccountHolderDTO userInfo = new AccountHolderDTO("Miqui657", "password", "1999-01-06", address, null);

        String body = objectMapper.writeValueAsString(userInfo);

        MvcResult mvcResult = mockMvc.perform(post("/users/new/accountholder").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
    }
    @Test
    void post_AccountHolder_userNameAlreadyExistsInDB_ThrowError() throws Exception {

        accountHolderRepository.save(new AccountHolder("Miqui657", "password", LocalDate.parse("1999-01-06"), address, null));
        AccountHolderDTO userInfo = new AccountHolderDTO("Miqui657", "password", "1999-01-06", address, null);

        String body = objectMapper.writeValueAsString(userInfo);

        MvcResult mvcResult = mockMvc.perform(post("/users/new/accountholder").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void post_AccountHolder_InvalidBirthDate_ThrowsError() throws Exception {

        AccountHolderDTO userInfo = new AccountHolderDTO("Miqui657", "password", "01-nov-1111", address, null);
        String body = objectMapper.writeValueAsString(userInfo);

        MvcResult mvcResult = mockMvc.perform(post("/users/new/accountholder").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void post_AccountHolder_NullPassword_ThrowsError() throws Exception {

        AccountHolderDTO userInfo = new AccountHolderDTO("Miqui657", null, "1999-01-06", address, null);
        String body = objectMapper.writeValueAsString(userInfo);

        MvcResult mvcResult = mockMvc.perform(post("/users/new/accountholder").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void post_AccountHolder_NullUserName_ThrowsError() throws Exception {

        AccountHolderDTO userInfo = new AccountHolderDTO(null, "password", "1999-01-06", address, null);
        String body = objectMapper.writeValueAsString(userInfo);

        MvcResult mvcResult = mockMvc.perform(post("/users/new/accountholder").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void post_AccountHolder_NullAddressPrimary_ThrowsError() throws Exception {

        AccountHolderDTO userInfo = new AccountHolderDTO("userName", "password", "1999-01-06", null, null);
        String body = objectMapper.writeValueAsString(userInfo);

        MvcResult mvcResult = mockMvc.perform(post("/users/new/accountholder").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }


}
