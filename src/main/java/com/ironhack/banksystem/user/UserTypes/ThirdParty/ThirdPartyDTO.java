package com.ironhack.banksystem.user.UserTypes.ThirdParty;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ThirdPartyDTO {

    private BigDecimal amount;
    @NotNull
    private long accountId;
    @NotBlank
    private String accountKey;

}
