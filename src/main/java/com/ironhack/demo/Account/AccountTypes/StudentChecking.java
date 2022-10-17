package com.ironhack.demo.Account.AccountTypes;

import com.ironhack.demo.Account.Account;
import com.ironhack.demo.Account.EnumStatusAccount;
import com.ironhack.demo.Money.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StudentChecking extends Account {

    private String secretKey;
    private final Date CREATION_DATE = new Date(System.currentTimeMillis());
    private EnumStatusAccount status;


}
