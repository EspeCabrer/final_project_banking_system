package com.ironhack.demo.Account.AccountTypes.Checking;

import com.ironhack.demo.Money.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class CheckingDTO {

    @NotNull
    private Money balance;
    @NotBlank
    private String userNamePrimaryOwner;
    private String userNameSecondaryOwner;
    @NotNull
    private BigDecimal penaltyFee;
    @NotBlank
    private String secretKey;
}
