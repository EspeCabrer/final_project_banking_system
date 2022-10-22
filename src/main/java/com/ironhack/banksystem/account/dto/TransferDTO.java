package com.ironhack.banksystem.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TransferDTO {
    @NotBlank
    private String receiverOwnerUserName;

    @NotNull
    private Long receiverAccountId;

    @NotNull
    private BigDecimal amountToTransfer;
}
