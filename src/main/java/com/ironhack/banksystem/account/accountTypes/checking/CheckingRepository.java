package com.ironhack.banksystem.account.accountTypes.checking;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckingRepository extends JpaRepository<Checking, Long> {
}
