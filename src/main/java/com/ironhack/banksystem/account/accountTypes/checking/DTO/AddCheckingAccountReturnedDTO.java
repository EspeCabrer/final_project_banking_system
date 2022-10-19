package com.ironhack.banksystem.account.accountTypes.checking.DTO;

import com.ironhack.banksystem.money.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Setter
@Getter
public class AddCheckingAccountReturnedDTO {
    private Long id;
    private Money balance;
    private String primaryOwnerUserName;
    private String secondaryOwnerUserName;
    private BigDecimal penaltyFee;
    private Date creationDate;
}


