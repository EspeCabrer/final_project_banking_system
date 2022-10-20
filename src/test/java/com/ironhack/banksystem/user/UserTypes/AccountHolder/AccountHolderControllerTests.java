package com.ironhack.banksystem.user.UserTypes.AccountHolder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.banksystem.address.Address;
import com.ironhack.banksystem.role.EnumRole;
import com.ironhack.banksystem.role.Role;
import com.ironhack.banksystem.role.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.AfterTestClass;
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
    private RoleRepository roleRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    Address address;
    Role role;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        address = new Address("Roma n25", "Madrid", 06754);
        accountHolderRepository.deleteAll();
        role = roleRepository.findByName(EnumRole.ACCOUNT_HOLDER).get();
    }

    @AfterTestClass
    public void clean(){
        accountHolderRepository.deleteAll();
    }

    @Test
    void post_AccountHolder_IsCreated_WorksOk() throws Exception {

        AccountHolderDTO userInfo = new AccountHolderDTO("Miqui657", "password", "1999-01-06", address, null);

        String body = objectMapper.writeValueAsString(userInfo);

        MvcResult mvcResult = mockMvc.perform(post("/users/new/accountholder").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
    }


    @Test
    void post_AccountHolder_NullOrBlankUsername_ThrowError() throws Exception {

        AccountHolderDTO userInfo1 = new AccountHolderDTO("", "password", "1999-01-06", address, null);
        AccountHolderDTO userInfo2 = new AccountHolderDTO(null, "password", "1999-01-06", address, null);

        String body1 = objectMapper.writeValueAsString(userInfo1);
        String body2 = objectMapper.writeValueAsString(userInfo2);

        mockMvc.perform(post("/users/new/accountholder").content(body1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        mockMvc.perform(post("/users/new/accountholder").content(body2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void post_AccountHolder_NullOrBlankPassword_ThrowError() throws Exception {

        AccountHolderDTO userInfo1 = new AccountHolderDTO("Miqui657", "", "1999-01-06", address, null);
        AccountHolderDTO userInfo2 = new AccountHolderDTO("Miqui657", null, "1999-01-06", address, null);

        String body1 = objectMapper.writeValueAsString(userInfo1);
        String body2 = objectMapper.writeValueAsString(userInfo2);

        mockMvc.perform(post("/users/new/accountholder").content(body1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        mockMvc.perform(post("/users/new/accountholder").content(body2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }


    @Test
    void post_AccountHolder_NullDateBirth_ThrowError() throws Exception {

        AccountHolderDTO userInfo1 = new AccountHolderDTO("Miqui657", "password", null , address, null);

        String body1 = objectMapper.writeValueAsString(userInfo1);

        mockMvc.perform(post("/users/new/accountholder").content(body1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void post_AccountHolder_NullPrimaryAddress_ThrowError() throws Exception {

        AccountHolderDTO userInfo1 = new AccountHolderDTO("Miqui657", "password", "1999-01-06", null, null);

        String body1 = objectMapper.writeValueAsString(userInfo1);

        mockMvc.perform(post("/users/new/accountholder").content(body1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void post_AccountHolder_UserNameAlreadyExistsInDB_ThrowError() throws Exception {

        accountHolderRepository.save(new AccountHolder("Miqui657", "password", LocalDate.parse("1999-01-06"), address, null, role));
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
}
