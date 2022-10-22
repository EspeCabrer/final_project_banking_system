package com.ironhack.banksystem.account.accountTypes.creditCard;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.banksystem.address.Address;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.role.EnumRole;
import com.ironhack.banksystem.role.Role;
import com.ironhack.banksystem.role.RoleRepository;
import com.ironhack.banksystem.user.UserRepository;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolder;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolderRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CreditCardControllerTests {
    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;


    private final ObjectMapper objectMapper = new ObjectMapper();
    Role role;

    AccountHolder user1;


    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        accountHolderRepository.deleteAll();
        creditCardRepository.deleteAll();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Address address = new Address("Roma n25", "Madrid", 06754);
        user1 = new AccountHolder("maria", "password", LocalDate.parse("1987-06-02"), address, null, role );
        AccountHolder user2 = new AccountHolder("pepe", "password", LocalDate.parse("1987-06-02"), address, null, role );

        accountHolderRepository.saveAll(List.of(user1, user2));
        role = roleRepository.findByName(EnumRole.ACCOUNT_HOLDER).get();
    }

    @AfterEach
    public void clean(){
        accountHolderRepository.deleteAll();
        creditCardRepository.deleteAll();
    }

    @Test
    void post_CreditCardAccount_WorksOk() throws Exception {
        CreditCardCreateDTO creditCardDTO = new CreditCardCreateDTO(BigDecimal.valueOf(2000), "maria", "pepe", BigDecimal.valueOf(300), BigDecimal.valueOf(0.2));
        String body = objectMapper.writeValueAsString(creditCardDTO);

        mockMvc.perform(post("/accounts/new/creditcard").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertEquals(1, creditCardRepository.findAll().size());
        assertEquals("maria", creditCardRepository.findAll().get(0).getPrimaryOwner().getUsername());
    }

    @Test
    void post_CreditCardAccount_NullBalance_ThrowsError() throws Exception {
        BigDecimal balance = null;
        CreditCardCreateDTO creditCardDTO = new CreditCardCreateDTO(balance, "maria", "maria", BigDecimal.valueOf(300), BigDecimal.valueOf(0.2));
        String body = objectMapper.writeValueAsString(creditCardDTO);

        mockMvc.perform(post("/accounts/new/creditcard").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void post_CreditCardAccount_NullPrimaryOwner_ThrowsError() throws Exception {

        String primaryOwnerUserName = null;
        CreditCardCreateDTO creditCardDTO = new CreditCardCreateDTO(BigDecimal.valueOf(2000), primaryOwnerUserName, "maria", BigDecimal.valueOf(300), BigDecimal.valueOf(0.2));
        String body = objectMapper.writeValueAsString(creditCardDTO);

        mockMvc.perform(post("/accounts/new/creditcard").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void post_CreditCardAccount_nullSecondaryOwner_WorksOk() throws Exception {

        String secondaryOwnerUserName = null;
        CreditCardCreateDTO creditCardDTO = new CreditCardCreateDTO(BigDecimal.valueOf(2000), "maria", secondaryOwnerUserName, BigDecimal.valueOf(300), BigDecimal.valueOf(0.2));
        String body = objectMapper.writeValueAsString(creditCardDTO);

        mockMvc.perform(post("/accounts/new/creditcard").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
    }


    @Test
    void post_CreditCardAccount_nullCreditLimit_WorksOk() throws Exception {

        BigDecimal creditLimit = null;
        CreditCardCreateDTO creditCardDTO = new CreditCardCreateDTO(BigDecimal.valueOf(2000), "maria", null, creditLimit, BigDecimal.valueOf(0.2));
        String body = objectMapper.writeValueAsString(creditCardDTO);

        mockMvc.perform(post("/accounts/new/creditcard").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    void post_CreditCardAccount_nullInterestRate_WorksOk() throws Exception {

        BigDecimal interestRate = null;
        CreditCardCreateDTO creditCardDTO = new CreditCardCreateDTO(BigDecimal.valueOf(2000), "maria", null, BigDecimal.valueOf(300), interestRate);
        String body = objectMapper.writeValueAsString(creditCardDTO);

        mockMvc.perform(post("/accounts/new/creditcard").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    void patch_updateCreditCard_WorksOk() throws Exception {
        CreditCard creditCardSaved = creditCardRepository.save(new CreditCard(new Money(BigDecimal.valueOf(3000)), user1, null, null, null));

        BigDecimal interestRate = null;
        CreditCardCreateDTO creditCardDTO = new CreditCardCreateDTO(BigDecimal.valueOf(2000), "pepe", null, BigDecimal.valueOf(300), interestRate);
        String body = objectMapper.writeValueAsString(creditCardDTO);

        mockMvc.perform(put("/accounts/update/creditcard/" + creditCardSaved.getId()).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        CreditCard creditCardUpdated = creditCardRepository.findById(creditCardSaved.getId()).get();

        assertEquals("pepe", creditCardUpdated.getPrimaryOwner().getUsername());
        assertEquals(creditCardSaved.getId(), creditCardUpdated.getId());
    }
}
