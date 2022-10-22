package com.ironhack.banksystem.account.accountTypes.checking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class CheckingCreateDTO {

    @NotNull(message = "The balance is required.")
    private BigDecimal balance;
    @NotBlank(message = "The primaryOwnerUserName is required.")
    private String primaryOwnerUserName;
    //optional
    private String secondaryOwnerUserName;
    @NotBlank(message = "The secretKey is required.")
    private String secretKey;
}
