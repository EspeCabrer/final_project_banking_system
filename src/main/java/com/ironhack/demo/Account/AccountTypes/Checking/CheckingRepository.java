package com.ironhack.demo.Account.AccountTypes.Checking;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckingRepository extends JpaRepository<Checking, Long> {
}
