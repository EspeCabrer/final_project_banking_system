package com.ironhack.banksystem.account.accountTypes.creditCard;

import com.ironhack.banksystem.money.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
public class CreditCardDTO {

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
    @NotNull(message = "The penaltyFee is required.")
    private BigDecimal penaltyFee;

}
