package com.ironhack.banksystem.thirdParty.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ThirdPartyTransferDTO {

    private BigDecimal amount;
    @NotNull
    private long accountId;
    @NotBlank
    private String accountKey;

}
