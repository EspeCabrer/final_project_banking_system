package com.ironhack.banksystem.account.accountTypes.checking.DTO;

import com.ironhack.banksystem.money.Money;
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

    @NotNull(message = "The balance is required.")
    private BigDecimal balance;
    @NotBlank(message = "The primaryOwnerUserName is required.")
    private String primaryOwnerUserName;
    private String secondaryOwnerUserName;
    @NotBlank(message = "The secretKey is required.")
    private String secretKey;
}
