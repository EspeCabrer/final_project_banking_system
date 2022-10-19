package com.ironhack.demo.account.accountTypes.savings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class SavingsDTO {

    @NotNull(message = "The balance is required.")
    private BigDecimal balance;
    @NotBlank(message = "The primary owner user name is required.")
    private String primaryOwnerUserName;
    //optional
    private String secondaryOwnerUserName;
    @NotNull(message = "The penalty fee is required.")
    private BigDecimal penaltyFee;
    //optional
    @DecimalMax(value = "0.5", inclusive = true)
    private BigDecimal interestRate;
    //optional
    @Max(value = 1000, message = "The minimum balance must be between 100 and 1000.")
    @Min(value= 100, message = "The minimum balance must be between 100 and 1000.")
    private BigDecimal minimumBalance;
    @NotBlank(message = "The secretKey is required.")
    private String secretKey;

    @Override
    public String toString() {
        return "SavingsDTO{" +
                "balance=" + balance +
                ", primaryOwnerUserName='" + primaryOwnerUserName + '\'' +
                ", secondaryOwnerUserName='" + secondaryOwnerUserName + '\'' +
                ", penaltyFee=" + penaltyFee +
                ", interestRate=" + interestRate +
                ", minimumBalance=" + minimumBalance +
                ", secretKey='" + secretKey + '\'' +
                '}';
    }
}
