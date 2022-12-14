package com.ironhack.banksystem.account.accountTypes.creditCard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
public class CreditCardCreateDTO {

    @NotNull(message = "The balance is required.")
    private BigDecimal balance;
    @NotBlank(message = "The primaryOwnerUserName is required.")
    private String primaryOwnerUserName;
    //optional
    private String secondaryOwnerUserName;
    private BigDecimal creditLimit;
    //optional
    @DecimalMax(value = "0.5", inclusive = true)
    private BigDecimal interestRate;
}
