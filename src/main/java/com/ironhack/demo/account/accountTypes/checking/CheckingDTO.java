package com.ironhack.demo.account.accountTypes.checking;

import com.ironhack.demo.money.Money;
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
    private String primaryOwnerUserName;
    private String secondaryOwnerUserName;
    @NotNull
    private BigDecimal penaltyFee;
    @NotBlank
    private String secretKey;
}
