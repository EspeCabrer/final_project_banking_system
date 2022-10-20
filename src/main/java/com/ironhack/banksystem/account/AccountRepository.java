package com.ironhack.banksystem.account;

import com.ironhack.banksystem.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}