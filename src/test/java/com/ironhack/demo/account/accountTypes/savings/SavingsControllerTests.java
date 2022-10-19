package com.ironhack.demo.account.accountTypes.savings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.demo.address.Address;
import com.ironhack.demo.money.Money;
import com.ironhack.demo.user.UserTypes.AccountHolder.AccountHolder;
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
    void post_SavingsAccount_nullBalance_ThrowsError() throws Exception {

        SavingsDTO savingsDto = new SavingsDTO(null, "pepe", "maria", 0.3, 0.2, 400.00, "secretKey");
        String body = objectMapper.writeValueAsString(savingsDto);

        mockMvc.perform(post("/accounts/new/savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void post_SavingsAccount_nullPrimaryOwner_ThrowsError() throws Exception {

        SavingsDTO savingsDto = new SavingsDTO(2000.00, null, "maria", 0.3, 0.2, 400.00, "secretKey");
        String body = objectMapper.writeValueAsString(savingsDto);

        mockMvc.perform(post("/accounts/new/savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void post_SavingsAccount_nullSecondaryOwner_WorksOk() throws Exception {

        SavingsDTO savingsDto = new SavingsDTO(2000.00, "maria", null, 0.3, 0.2, 400.00, "secretKey");
        String body = objectMapper.writeValueAsString(savingsDto);

        mockMvc.perform(post("/accounts/new/savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    void post_SavingsAccount_nullPenaltyFee_ThrowsError() throws Exception {

        SavingsDTO savingsDto = new SavingsDTO(2000.00, "maria", null, null, 0.2, 400.00, "secretKey");
        String body = objectMapper.writeValueAsString(savingsDto);

        mockMvc.perform(post("/accounts/new/savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void post_SavingsAccount_nullInterestRate_WorksOk() throws Exception {

        SavingsDTO savingsDto = new SavingsDTO(2000.00, "maria", null, 0.3, null, 400.00, "secretKey");
        String body = objectMapper.writeValueAsString(savingsDto);

        mockMvc.perform(post("/accounts/new/savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    void post_SavingsAccount_nullMinimumBalance_WorksOk() throws Exception {

        SavingsDTO savingsDto = new SavingsDTO(2000.00, "maria", null, 0.3, 0.2, null, "secretKey");
        String body = objectMapper.writeValueAsString(savingsDto);

        mockMvc.perform(post("/accounts/new/savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    void post_SavingsAccount_MinimumBalanceGreaterThanMaxValue_ThrowError() throws Exception {

        SavingsDTO savingsDto = new SavingsDTO(2000.00, "maria", null, 0.3, 0.2, 1000.50, "secretKey");
        String body = objectMapper.writeValueAsString(savingsDto);

        mockMvc.perform(post("/accounts/new/savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void post_SavingsAccount_MinimumBalanceLessThanMinValue_ThrowError() throws Exception {

        SavingsDTO savingsDto = new SavingsDTO(2000.00, "maria", null, 0.3, 0.2, 50.00, "secretKey");
        String body = objectMapper.writeValueAsString(savingsDto);

        mockMvc.perform(post("/accounts/new/savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void post_SavingsAccount_NullOrBlankSecretKey_ThrowError() throws Exception {

        SavingsDTO savingsDto1 = new SavingsDTO(2000.00, "maria", null, 0.3, 0.2, 800.00, "");
        String body1 = objectMapper.writeValueAsString(savingsDto1);
        SavingsDTO savingsDto2 = new SavingsDTO(2000.00, "maria", null, 0.3, 0.2, 800.00, null);
        String body2 = objectMapper.writeValueAsString(savingsDto2);

        mockMvc.perform(post("/accounts/new/savings").content(body1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        mockMvc.perform(post("/accounts/new/savings").content(body2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }




}
