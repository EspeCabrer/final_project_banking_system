package com.ironhack.banksystem.account.accountTypes.savings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.banksystem.address.Address;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SavingsControllerTests {

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
        AccountHolder user = new AccountHolder("maria", "password", LocalDate.parse("1987-06-02"), address, null );
        accountHolderRepository.save(user);
    }

    @AfterEach
    public void clean(){
        accountHolderRepository.deleteAll();
    }


    @Test
    void post_SavingsAccount_nullBalance_ThrowsError() throws Exception {

        SavingsDTO savingsDto = new SavingsDTO(null, "pepe", "maria", BigDecimal.valueOf(0.3), BigDecimal.valueOf(0.2), BigDecimal.valueOf(400), "secretKey");
        String body = objectMapper.writeValueAsString(savingsDto);

        mockMvc.perform(post("/accounts/new/savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void post_SavingsAccount_nullPrimaryOwner_ThrowsError() throws Exception {

        SavingsDTO savingsDto = new SavingsDTO(BigDecimal.valueOf(2000), null, "maria", BigDecimal.valueOf(0.3), BigDecimal.valueOf(0.2), BigDecimal.valueOf(400), "secretKey");
        String body = objectMapper.writeValueAsString(savingsDto);

        mockMvc.perform(post("/accounts/new/savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void post_SavingsAccount_nullSecondaryOwner_WorksOk() throws Exception {

        SavingsDTO savingsDto = new SavingsDTO(BigDecimal.valueOf(2000), "maria", null, BigDecimal.valueOf(0.3), BigDecimal.valueOf(0.2), BigDecimal.valueOf(400), "secretKey");
        String body = objectMapper.writeValueAsString(savingsDto);

        mockMvc.perform(post("/accounts/new/savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    void post_SavingsAccount_nullPenaltyFee_ThrowsError() throws Exception {

        SavingsDTO savingsDto = new SavingsDTO(BigDecimal.valueOf(2000),  "maria", null, null, BigDecimal.valueOf(0.2), BigDecimal.valueOf(400), "secretKey");
        String body = objectMapper.writeValueAsString(savingsDto);

        mockMvc.perform(post("/accounts/new/savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void post_SavingsAccount_nullInterestRate_WorksOk() throws Exception {

        SavingsDTO savingsDto = new SavingsDTO(BigDecimal.valueOf(2000),  "maria", null, BigDecimal.valueOf(0.3), null, BigDecimal.valueOf(400), "secretKey");
        String body = objectMapper.writeValueAsString(savingsDto);

        mockMvc.perform(post("/accounts/new/savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    void post_SavingsAccount_nullMinimumBalance_WorksOk() throws Exception {

        SavingsDTO savingsDto = new SavingsDTO(BigDecimal.valueOf(2000),  "maria", null, BigDecimal.valueOf(0.3), BigDecimal.valueOf(0.2), null, "secretKey");
        String body = objectMapper.writeValueAsString(savingsDto);

        mockMvc.perform(post("/accounts/new/savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    void post_SavingsAccount_MinimumBalanceGreaterThanMaxValue_ThrowError() throws Exception {

        SavingsDTO savingsDto = new SavingsDTO(BigDecimal.valueOf(2000),  "maria", null, BigDecimal.valueOf(0.3), BigDecimal.valueOf(0.2), BigDecimal.valueOf(1000.5),"secretKey");
        String body = objectMapper.writeValueAsString(savingsDto);

        mockMvc.perform(post("/accounts/new/savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void post_SavingsAccount_MinimumBalanceLessThanMinValue_ThrowError() throws Exception {

        SavingsDTO savingsDto = new SavingsDTO(BigDecimal.valueOf(2000),  "maria", null, BigDecimal.valueOf(0.3), BigDecimal.valueOf(0.2), BigDecimal.valueOf(50), "secretKey");
        String body = objectMapper.writeValueAsString(savingsDto);

        mockMvc.perform(post("/accounts/new/savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void post_SavingsAccount_NullOrBlankSecretKey_ThrowError() throws Exception {

        SavingsDTO savingsDto1 = new SavingsDTO(BigDecimal.valueOf(2000),  "maria", null, BigDecimal.valueOf(0.3), BigDecimal.valueOf(0.2), BigDecimal.valueOf(400), "");
        String body1 = objectMapper.writeValueAsString(savingsDto1);
        SavingsDTO savingsDto2 = new SavingsDTO(BigDecimal.valueOf(2000), "maria", null, BigDecimal.valueOf(0.3), BigDecimal.valueOf(0.2), BigDecimal.valueOf(400), null);
        String body2 = objectMapper.writeValueAsString(savingsDto2);

        mockMvc.perform(post("/accounts/new/savings").content(body1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        mockMvc.perform(post("/accounts/new/savings").content(body2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }




}
